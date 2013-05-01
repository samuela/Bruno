package frontend;

import java.io.File;

/**
 * Model of a document should store information like the edit history, file
 * location, etc.
 * 
 * TODO Incorporate edit history
 * 
 * @author samuelainsworth
 * 
 */
public class DocumentModel {
	private File file;

	public DocumentModel() {
		this.file = null;
	}

	public DocumentModel(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

}
