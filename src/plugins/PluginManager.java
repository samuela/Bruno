package plugins;

import foobar.ScriptFooable;

import java.io.File;
import java.util.Set;

import javax.script.ScriptException;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:13 PM To
 * change this template use File | Settings | File Templates.
 */
public interface PluginManager {


    @Deprecated
    Set<Plugin> loadPlugins(File file) throws IllegalArgumentException;

    /**
     *
     * @param topLevelPluginDir the system bruno plugin directory
     * @return a set containing a fooable for each script successfully loaded
     */
    Set<ScriptFooable> getAllScriptFooables(File topLevelPluginDir);

    /**
     *
     * @param key name by which scripts will know variabe
     * @param val the object to be manipulated by the script
     *
     * allows all scripts access to val
     */
    void exposeVariable(String key, Object val);

    /**
     *
     * @param key variable to remove from scripts' access
     *
     *any scripts executed after a call to revokevariable() will no longer have access
     *to the variable revoked
     */
    void revokeVariable(String key);

    void executeScript(Script userScript) throws ScriptException;

    void executeScript(String userScript) throws ScriptException;

    /**
     *
     * @param bundle
     * @return
     *
     * not supported in this version
     */
  //  LanguageBundle loadLanguageBundle(File bundle);

}
