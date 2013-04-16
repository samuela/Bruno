package plugins;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:27 PM To
 * change this template use File | Settings | File Templates.
 */
public class Plugin {
	private Map<String, Script> scriptsByName_;
	private String name_;
	private String path_;

	public Map<String, Script> getScriptsByName() {
		return new HashMap<String, Script>(scriptsByName_);
	}

	public String getName() {
		return name_;
	}

	public String getPath() {
		return path_;
	}

	public Plugin(String path) {
		scriptsByName_ = new HashMap<String, Script>();
		path_ = path;
		name_ = findNameFromPath(path_);
	}

	private String findNameFromPath(String path) {
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
}
