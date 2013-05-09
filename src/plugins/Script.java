package plugins;

import errorhandling.ErrorLogger;
import org.xml.sax.ErrorHandler;

import java.io.*;

/**
 * Created with IntelliJ IDEA. User: jonathan Date: 4/10/13 Time: 10:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class Script {

	private final String extension_;
	private final Plugin plugin_;
	private final String path_;
	private final String name_;
    private BufferedReader reader_;
    private final File file_;

//   private final String text_;

    public String getExtension() {
		return extension_;
	}

	public Plugin getPlugin() {
		return plugin_;
	}

	public String getPluginName() {
		return plugin_.getName();
	}

	public String getPath() {
		return path_;
	}

	public String getName() {
		return name_;
	}

	/**
	 * 
	 * @param path
	 *            script file location
	 * @param plugin
	 *            contains this script
	 * @throws IllegalArgumentException
	 *             if path does not have an extension
	 */
	public Script(String path, Plugin plugin, BufferedReader reader, File f) throws IllegalArgumentException {
    //    text_ = text;
        file_ = f;
        reader_ = reader;
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
		if (name_.isEmpty()) {
			throw new IllegalArgumentException(
					"Invalid script name - hidden file");
		}
	}

	@Override
	public String toString() {
		return "Script: name=" + name_ + " type=" + extension_ + " plugin=" + plugin_.getName();
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects
				.hashCode(extension_, path_, name_);
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

    public Reader getFileReader(){
        return reader_;
    }

    public void resetReader() {
        try {
            reader_ = new BufferedReader(new FileReader(file_));
        } catch (FileNotFoundException e) {
            ErrorLogger.log("Unable to reset script " + this.getName() + ". Restart Bruno.");
        }
    }
 /*
    public String getText() {
        return text_;
    }
    */
}
