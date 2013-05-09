package plugins;

import java.io.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.swing.*;

import errorhandling.ErrorLogger;
import foobar.ScriptFooable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:29 PM To
 * change this template use File | Settings | File Templates.
 */

public class SimplePluginManager implements PluginManager {

	private final ScriptEngineManager factory_;
	private Map<String, ScriptEngine> enginesByExtension_;
//	private Map<Script, BufferedReader> scriptBufferedReaders_;
	private Map<String, Plugin> pluginsByScriptName_;
	private Map<String, Plugin> pluginsByName_;

	private Bindings globals;

	public SimplePluginManager() {
        factory_ = new ScriptEngineManager();
		enginesByExtension_ = new HashMap<>();
	//	scriptBufferedReaders_ = new HashMap<>();
		pluginsByScriptName_ = new HashMap<>();
		pluginsByName_ = new HashMap<>();
		globals = new SimpleBindings();
		

	}

    public void clear(){
        pluginsByScriptName_ = new HashMap<>();
        pluginsByName_ = new HashMap<>();
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
        //manager contains plugin with a script with matching name
        boolean hasPlugin = pluginsByScriptName_.containsKey(s.getName());

        Plugin p = pluginsByScriptName_.get(s.getName());
        //just to be safe
        //plugin contains script with matching name
        Script found = p.getScriptByName(s.getName());

        return hasPlugin && found!=null && hasEngine(s.getExtension());
	}

	public void executeScript(String name) throws IllegalArgumentException {
        Plugin p = pluginsByScriptName_.get(name);
        if(p==null){
            throw new IllegalArgumentException("no plugin contains script " + name);
        }
        Script s = p.getScriptByName(name);
        if(s==null){
            throw new IllegalArgumentException("plugin " + p + " does not contain script " + s);
        }
		executeScript(pluginsByScriptName_.get(name).getScriptByName(name));
	}

	// a script cannot be executed unless its plugin has been loaded, so it is
	// safe to assume:
	// 1) engine for extension exists
	// 2) file for script exists
	@Override
	public void executeScript(final Script userScript) {
      //  System.out.println("executing " + userScript);
        // System.out.println("getting engine for " +
        // userScript.getExtension());
        final ScriptEngine engine = enginesByExtension_
                .get(userScript.getExtension());
        // System.out.println("ext:" + userScript.getExtension());
        // System.out.println("contains py:" +
        // enginesByExtension_.containsKey("py"));

            // System.out.println("engine null: " + (engine == null));
            putAll(engine);
            // System.out.println(engine);
            // System.out.println(scriptFileReaders_.get(userScript));
     //       BufferedReader scriptReader = scriptBufferedReaders_.get(userScript);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        engine.eval(userScript.getFileReader());
                    } catch (ScriptException e) {
                        System.err.println("script " + userScript + " messed up");
                        ErrorLogger.log("Error in script: " + userScript);
                    }
                }
            });
            userScript.resetReader();

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
	public Plugin loadPlugin(File directoryPath) throws IllegalArgumentException {
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
				HiddenFileFilter.VISIBLE, TrueFileFilter.INSTANCE);
		for (File f : files) {
			String path = f.getAbsolutePath();
			Script s = null;

        //        BufferedReader scriptReader = new BufferedReader(new FileReader(f));
              //  scriptBufferedReaders_.put(s, scriptReader);


			try {
				s = new Script(path, plugin, new BufferedReader(new FileReader(f)), f);
			} catch (IllegalArgumentException | IOException e) {
				// invalid path - ignore file
				// either
                ErrorLogger.log("Couldn't load script " + path + " : "
                        + e.getMessage());
				continue;
			}
            if (pluginsByScriptName_.containsKey(s.getName())) {
                ErrorLogger.log("A script with name '" + s.getName()
						+ "' already exists in plugin"
						+ pluginsByScriptName_.get(s.getName()));
				continue;
			}
			String extension = s.getExtension();
			if (!enginesByExtension_.containsKey(extension)) {
				// System.out.println("storing new engine for " + extension);
				// System.out.println("getting engine:" +
				// factory_.getEngineByExtension(extension));
				ScriptEngine newScriptEngine = factory_
						.getEngineByExtension(extension);
				if (newScriptEngine == null) {
                    ErrorLogger.log("Invalid extension: " + s.getPath());
					continue;
				} else {
					enginesByExtension_.put(extension,
							factory_.getEngineByExtension(extension));
				}
			}
            pluginsByScriptName_.put(s.getName(), plugin);
            plugin.addScript(s.getName(), s);

        }
  //      System.out.println("simplepluginmanager 198 " + plugin + " " + plugin.getScriptsByName());
        if(plugin.getScriptsByName().isEmpty()) {
       //     System.out.println("mreh: " + plugin.getPath());
            return null;
        }
        return plugin;
	}

	@Override
	public void revokeVariable(String key) {
		globals.remove(key);
	}

    //not supported in this version
    /**
	@Override
	public LanguageBundle loadLanguageBundle(File bundle) {
		return null;
	}
       **/
	Set<Plugin> loadPlugins(File pluginsDir) {
		Set<Plugin> plugins = new HashSet<>();
		if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
            ErrorLogger.log("Invalid plugins directory.");
			return null;
		}
		for (File f : pluginsDir.listFiles()) {	
			try {
				if (f.isDirectory()) {
					try {
                        Plugin p = loadPlugin(f.getAbsoluteFile());
                        if(p!=null){
						    plugins.add(p);
                        }
					} catch (IllegalArgumentException e) {
                        ErrorLogger.log("Failed to load plugin " + f + ": "
                                + e.getMessage());
					}
				}
			} catch (SecurityException e) {
                ErrorLogger.log("Couldn't load plugin at "
                        + f.getAbsolutePath());
			}
		}
		return plugins;
	}

    @Override
    /**
     * @returns null if topLevelPluginDir does not exist, is unreadable,
     * or invalid in some way
     */
    public Set<ScriptFooable> getAllScriptFooables(File topLevelPluginDir){
        Set<ScriptFooable> allScripts = new HashSet<>();
      //  Plugin misc = new Plugin(this, topLevelPluginDir.getAbsolutePath());
        if(!topLevelPluginDir.exists()){
            return null;
        }
        if(!topLevelPluginDir.isDirectory()) {
            return null;
        }
        for(Plugin p: loadPlugins(topLevelPluginDir)){
            for(Script s: p.getScriptsByName().values()){
                allScripts.add(new ScriptFooable(this, s));
            }
        }
        /*
        for(File file: topLevelPluginDir.listFiles()){
            if(file.isDirectory()){
                Plugin p = loadPlugin(file);
                if(p!=null){
                    for(Script s: p.getScriptsByName().values()){
                        allScripts.add(new ScriptFooable(this, s));
                    }
                } else {
                    //p is null meaning it has no valid scripts
                    //ignore silently
                }
            }
        }   */
        if(allScripts.isEmpty()){
            allScripts = null;
        }
        return allScripts;
    }
}
