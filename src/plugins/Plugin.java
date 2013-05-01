package plugins;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import com.google.common.base.Objects;

import foobar.Fooable;
import foobar.ScriptFooable;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:27 PM To
 * change this template use File | Settings | File Templates.
 */
public class Plugin {
	private final PluginManager manager_;
	private Map<String, Script> scriptsByName_;
	private final String name_;
	private final String path_;

	public Plugin(PluginManager manager, String path) {
		manager_ = manager;
		scriptsByName_ = new HashMap<String, Script>();
		path_ = path;
		name_ = findNameFromPath(path_);
	}

	private static String findNameFromPath(String path) {
		return (new File(path)).getName();
	}

	public void addScript(String name, Script s) {
		scriptsByName_.put(name, s);
	}

	public Script getScriptByName(String name) {
		if (scriptsByName_.containsKey(name)) {
			return scriptsByName_.get(name);
		} else {
			throw new IllegalArgumentException("Script " + name + " not found.");
		}
	}

    @Override
    public int hashCode() {
        return Objects.hashCode(name_, path_, scriptsByName_);
    }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Plugin)) {
			return false;
		} else {
			Plugin p = (Plugin) o;
			return p.getName().equals(getName())
					&& p.getPath().equals(getPath())
					&& p.getScriptsByName().equals(getScriptsByName());
		}
	}

    @Override
    public String toString(){
        return "Plugin '" + name_ + "': " + scriptsByName_.keySet();
    }
}
