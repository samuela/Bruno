package plugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:29 PM To
 * change this template use File | Settings | File Templates.
 */

public class SimplePluginManager implements PluginManager{

    private ScriptEngineManager factory_;
    private Map<String, ScriptEngine> enginesByExtension_;
    private Map<Script, FileReader> scriptFileReaders_;
    private Map<String, Plugin> pluginsByScriptName_;

    public SimplePluginManager(){
        factory_ = new ScriptEngineManager();
        enginesByExtension_ = new HashMap<String, ScriptEngine>();
        scriptFileReaders_ = new HashMap<Script, FileReader>();
        pluginsByScriptName_ = new HashMap<String, Plugin>();
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

    //a script cannot be executed unless its plugin has been loaded, so it is safe to assume:
    //1) engine for extension exists
    //2) file for script exists
    @Override
    public void executeScript(Script userScript) throws ScriptException {
    //    System.out.println("getting engine for " + userScript.getExtension());
        ScriptEngine engine = enginesByExtension_.get(userScript.getExtension());
    //    System.out.println("ext:" + userScript.getExtension());
    //    System.out.println("contains py:"  + enginesByExtension_.containsKey("py"));
        try {
     //       System.out.println("engine null: " + (engine == null));
            engine.eval(scriptFileReaders_.get(userScript));
        } catch (ScriptException e) {
            throw new ScriptException("Error in Script: " + userScript);
        }
    }

    //if plugin contains any new scripting languages, create new engines for them
    //open new FileReader for each script in plugin recursively
    @Override
    public Plugin loadPlugin(File directoryPath) {
        Plugin plugin = new Plugin(directoryPath.getAbsolutePath());
        Collection<File> files = FileUtils.listFiles(directoryPath, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for(File f: files) {
            String path = f.getAbsolutePath();
            Script s = null;
            try{
               s = new Script(path, plugin);
            } catch (IllegalArgumentException e){
                //invalid path - ignore file
                continue;
            }
            if(pluginsByScriptName_.containsKey(s.getName())){
                System.err.println("A script with name: " + s.getName() + " already exists in plugin" + pluginsByScriptName_.get(s.getName()));
                continue;
            }
            plugin.addScript(s.getName(), s);
            String extension = s.getExtension();
            if(!enginesByExtension_.containsKey(extension)) {
                //      System.out.println("storing new engine for " + extension);
         //       System.out.println("getting engine:" + factory_.getEngineByExtension(extension));
                enginesByExtension_.put(extension, factory_.getEngineByExtension(extension));
            }
            try {
                FileReader scriptReader = new FileReader(f);
                scriptFileReaders_.put(s, scriptReader);
                pluginsByScriptName_.put(s.getName(), plugin);
            } catch (FileNotFoundException e) {
                System.err.println("file " + path + " has disappeared.");
            }


		}
		return plugin;
	}

	@Override
	public LanguageBundle loadLanguageBundle(File bundle) {
		return null;
	}

}
