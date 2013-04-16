package plugins;

import java.io.File;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Script {
	private String extension_;
	private Plugin plugin_;
	private String path_;
	private String name_;

	public String getExtension() {
		return extension_;
	}

	public Plugin getPlugin() {
		return plugin_;
	}

	public String getPath() {
		return path_;
	}

	public String getName() {
		return name_;
	}

	public Script(String path, Plugin plugin) throws IllegalArgumentException {
		extension_ = path.substring(path.lastIndexOf('.') + 1);
		plugin_ = plugin;
		path_ = path;
		String withExt = (new File(path)).getName();
		int dot = withExt.lastIndexOf('.');
		if (dot == -1) {
			throw new IllegalArgumentException(
					"Invalid script name - no extension: " + path);
		} else {
			name_ = withExt.substring(0, withExt.lastIndexOf('.'));
		}
	}

	public String toString() {
		return "Script type=" + extension_ + " plugin=" + plugin_;
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(extension_, plugin_,
				path_, name_);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Script)) {
			return false;
		} else {
			Script s = (Script) o;
			return s.getExtension().equals(getExtension())
					&& s.getPlugin() == getPlugin()
					&& s.getPath().equals(getPath())
					&& s.getName().equals(getName());
		}
	}
}
