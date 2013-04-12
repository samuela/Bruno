package plugins;

import javax.script.ScriptException;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: jonathan
 * Date: 4/10/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PluginManager {

    void executeScript(Script userScript) throws ScriptException;

    Plugin loadPlugin(File directoryPath);

    LanguageBundle loadLanguageBundle(File bundle);
}
