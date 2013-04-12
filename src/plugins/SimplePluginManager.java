package plugins;

import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.script.*;
import java.io.*;
import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: jonathan
 * Date: 4/10/13
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimplePluginManager implements PluginManager{

    private ScriptEngineManager factory_;
    private Map<String, ScriptEngine> enginesByExtension_;
    private Map<Script, FileReader> scriptFileReaders_;

    public SimplePluginManager(){
        factory_ = new ScriptEngineManager();
        enginesByExtension_ = new HashMap<String, ScriptEngine>();
        scriptFileReaders_ = new HashMap<Script, FileReader>();

    }

    public boolean hasEngine(String extension){
        return enginesByExtension_.containsKey(extension);
    }

    public boolean supportsScript(Script s){
        return scriptFileReaders_.containsKey(s) && hasEngine(s.getExtension());
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
