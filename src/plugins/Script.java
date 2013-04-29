package plugins;

import foobar.Fooable;

import javax.script.ScriptException;
import java.io.File;
import java.util.HashSet;
import java.util.Set;


/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Script implements Fooable{

    private PluginManager pluginManager_;

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

    @Override
	public String getName() {
		return name_;
	}

    @Override
    public Set<String> getKeywords() {
        Set<String> keyWords = new HashSet<>();
        keyWords.add(name_);
        keyWords.add(plugin_.getName());

        return keyWords;
    }

    @Override
    public void doAction() {
        try {
            pluginManager_.executeScript(this);
        } catch (ScriptException e) {
            System.err.println("Script " + this + " failed");
            e.printStackTrace();
        }
    }

    public Script(String path, Plugin plugin, PluginManager pluginManager) throws IllegalArgumentException {
        pluginManager_ = pluginManager;
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

    @Override
	public String toString() {
		return "Script: type=" + extension_ + " plugin=" + plugin_;
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
