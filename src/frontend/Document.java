package frontend;

import java.io.File;

/**
 * Model of a document should store information like the edit history, file
 * location, etc.
 * 
 * @author samuelainsworth
 * 
 */
public class Document {
	private File file;

	public Document() {
		this.file = null;
	}

	public Document(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

}
