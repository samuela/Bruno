package frontend;

import java.io.File;

/**
 * Model of a document should store information like the edit history, file
 * location, etc.
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

	public void setFile(File file) {
		this.file = file;
	}

	public File getMetadataFile() {
		if (file == null) {
			return null;
		} else {
			return new File(getFile().getAbsolutePath() + Bruno.FILE_EXT);
		}
	}

}
