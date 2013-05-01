package plugins;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plugin other = (Plugin) obj;
		if (manager_ == null) {
			if (other.manager_ != null)
				return false;
		} else if (!manager_.equals(other.manager_))
			return false;
		if (name_ == null) {
			if (other.name_ != null)
				return false;
		} else if (!name_.equals(other.name_))
			return false;
		if (path_ == null) {
			if (other.path_ != null)
				return false;
		} else if (!path_.equals(other.path_))
			return false;
		if (scriptsByName_ == null) {
			if (other.scriptsByName_ != null)
				return false;
		} else if (!scriptsByName_.equals(other.scriptsByName_))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Plugin '" + name_ + "': " + scriptsByName_.keySet();
	}

	public String getName() {
		return name_;
	}

	public String getPath() {
		return path_;
	}

	public Map<String, Script> getScriptsByName() {
		return scriptsByName_;
	}
}
