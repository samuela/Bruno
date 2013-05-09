package org.bruno.plugins;


import java.io.File;
import java.util.Set;

import javax.script.ScriptException;

import org.bruno.foobar.ScriptFooable;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:13 PM To
 * change this template use File | Settings | File Templates.
 */
public interface PluginManager {


    //@Deprecated
    //public Plugin loadPlugin(File directoryPath) throws IllegalArgumentException;
    // @Deprecated
  //  Set<Plugin> loadPlugins(File file) throws IllegalArgumentException;



    /**
     *
     * @param topLevelPluginDir the system bruno plugin directory
     * @return a set containing a fooable for each script successfully loaded, or null
     * if no scripts are successfully loaded
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

    void executeScript(Script userScript);

    void executeScript(String userScript);

    /**
     * Clear old plugins before loading new ones
     */
    void clear();

}
