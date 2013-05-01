package plugins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:29 PM To
 * change this template use File | Settings | File Templates.
 */

public class SimplePluginManager implements PluginManager {

	private final ScriptEngineManager factory_;
	private Map<String, ScriptEngine> enginesByExtension_;
	private Map<Script, FileReader> scriptFileReaders_;
	private Map<String, Plugin> pluginsByScriptName_;
	private Map<String, Plugin> pluginsByName_;
	private PrintWriter errorLog;

	private Bindings globals;
	private ScriptEngine last_;

	public SimplePluginManager() {
		factory_ = new ScriptEngineManager();
		enginesByExtension_ = new HashMap<String, ScriptEngine>();
		scriptFileReaders_ = new HashMap<Script, FileReader>();
		pluginsByScriptName_ = new HashMap<String, Plugin>();
		pluginsByName_ = new HashMap<>();
		globals = new SimpleBindings();
		
		try {
			errorLog = new PrintWriter(new BufferedWriter(new FileWriter(
					System.getProperty("user.home") + "/bruno.log", true)),
					true);
			errorLog.println(new Timestamp((new Date()).getTime()));
		} catch (IOException e) {
			errorLog = new PrintWriter(System.err, true);
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}

	public ScriptEngine getEngineByExtension(String ext) {
		return enginesByExtension_.get(ext);
	}

	public boolean contains(Plugin p, String scriptName, String ext) {
		Plugin p2 = pluginsByScriptName_.get(scriptName);
		if (p == null || p2 == null || !p.equals(p2)) {
			return false;
		}
		try {
			Script s = p.getScriptByName(scriptName);
			return s.getExtension().equals(ext);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public boolean hasEngine(String extension) {
		return enginesByExtension_.containsKey(extension);
	}

	public boolean supportsScript(Script s) {
		return scriptFileReaders_.containsKey(s) && hasEngine(s.getExtension());
	}

	public void executeScript(String name) throws ScriptException {
		executeScript(pluginsByScriptName_.get(name).getScriptByName(name));
	}

	// a script cannot be executed unless its plugin has been loaded, so it is
	// safe to assume:
	// 1) engine for extension exists
	// 2) file for script exists
	@Override
	public void executeScript(Script userScript) throws ScriptException {
		// System.out.println("getting engine for " +
		// userScript.getExtension());
		ScriptEngine engine = enginesByExtension_
				.get(userScript.getExtension());
		// System.out.println("ext:" + userScript.getExtension());
		// System.out.println("contains py:" +
		// enginesByExtension_.containsKey("py"));
		try {
			// System.out.println("engine null: " + (engine == null));
			putAll(engine);
			// System.out.println(engine);
			// System.out.println(scriptFileReaders_.get(userScript));
			engine.eval(scriptFileReaders_.get(userScript));
			// System.out.println("executescript trying to get...");
			// System.out.println("i="+engine.get("i"));
			// System.out.println(engine + "evaluating" + userScript);
			last_ = engine;
		} catch (ScriptException e) {
			throw new ScriptException("Error in Script: " + userScript);
		}
	}

	private void putAll(ScriptEngine engine) {
		for (String s : globals.keySet()) {
			engine.put(s, globals.get(s));
		}
	}

	@Override
	public void exposeVariable(String key, Object val) {
		globals.put(key, val);
	}

	// if plugin contains any new scripting languages, create new engines for
	// them
	// open new FileReader for each script in plugin recursively
	@Override
	public Plugin loadPlugin(File directoryPath)
			throws IllegalArgumentException {
		assert directoryPath.isDirectory();
		String dirPath = directoryPath.getAbsolutePath();
		assert !dirPath.isEmpty();
		int end = dirPath.length();
		if (dirPath.charAt(end - 1) == '/') {
			end--;
		}
		String pluginName = dirPath
				.substring(1 + dirPath.lastIndexOf('/'), end);
		if (pluginsByName_.containsKey(pluginName)) {
			throw new IllegalArgumentException(
					"Already loaded plugin with name " + pluginName);
		}
		Plugin plugin = new Plugin(this, dirPath);
		pluginsByName_.put(pluginName, plugin);

		Collection<File> files = FileUtils.listFiles(directoryPath,
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File f : files) {
			String path = f.getAbsolutePath();
			Script s = null;
			try {
				s = new Script(path, plugin);
			} catch (IllegalArgumentException e) {
				// invalid path - ignore file
				// either
				errorLog.println("Couldn't load script " + path + " : "
						+ e.getMessage());
				continue;
			}
			if (pluginsByScriptName_.containsKey(s.getName())) {
				errorLog.println("A script with name '" + s.getName()
						+ "' already exists in plugin"
						+ pluginsByScriptName_.get(s.getName()));
				continue;
			}
			plugin.addScript(s.getName(), s);
			String extension = s.getExtension();
			if (!enginesByExtension_.containsKey(extension)) {
				// System.out.println("storing new engine for " + extension);
				// System.out.println("getting engine:" +
				// factory_.getEngineByExtension(extension));
				ScriptEngine newScriptEngine = factory_
						.getEngineByExtension(extension);
				if (newScriptEngine == null) {
					errorLog.println("Invalid extension: " + s.getPath());
					continue;
				} else {
					enginesByExtension_.put(extension,
							factory_.getEngineByExtension(extension));
				}
			}
			try {
				FileReader scriptReader = new FileReader(f);
				scriptFileReaders_.put(s, scriptReader);
				pluginsByScriptName_.put(s.getName(), plugin);
			} catch (FileNotFoundException e) {
				errorLog.println("file " + path + " cannot be loaded.");
			}

		}
		return plugin;
	}

	@Override
	public void revokeVariable(String key) {
		globals.remove(key);
	}

	@Override
	public LanguageBundle loadLanguageBundle(File bundle) {
		return null;
	}

	@Override
	public Set<Plugin> loadPlugins(File pluginsDir)
			throws IllegalArgumentException {
		Set<Plugin> plugins = new HashSet<>();
		if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
			errorLog.println("Invalid plugins directory.");
			return null;
		}
		for (File f : pluginsDir.listFiles()) {
			try {
				if (f.isDirectory()) {
					try {
						plugins.add(loadPlugin(f.getAbsoluteFile()));
					} catch (IllegalArgumentException e) {
						errorLog.println("Failed to load plugin " + f + ": "
								+ e.getMessage());
					}
				}
			} catch (SecurityException e) {
				errorLog.println("Couldn't load plugin at "
						+ f.getAbsolutePath());
			}
		}
		return plugins;
	}

}
