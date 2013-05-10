package org.bruno.foobar;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bruno.frontend.Bruno;
import org.bruno.frontend.ProjectExplorer;

/**
 * A wrapper Fooable for opening files.
 * 
 * @author samuelainsworth
 * 
 */
public class FileFooable implements Fooable {

	private Bruno parentApp;
	private ProjectExplorer projectExplorer;
	private File file;

	public FileFooable(Bruno parentApp, ProjectExplorer projectExplorer,
			File file) {
		this.parentApp = parentApp;
		this.projectExplorer = projectExplorer;
		this.file = file;
	}

	/**
	 * @return The name of the file
	 */
	@Override
	public String getName() {
		return file.getName();
	}

	/**
	 * @return A set of keywords for finding this Fooable
	 */
	@Override
	public Set<String> getKeywords() {
		Set<String> r = new HashSet<>();
		// split path and add as keywords
		String[] splitFilename = file.getAbsolutePath().split(File.separator);
		for (int i = splitFilename.length - 1; i >= 0; i--) {
			String filenamePiece = splitFilename[i];
			if (filenamePiece.equals(projectExplorer.getCurrentFolder()
					.getName())) {
				break;
			}
			r.add(filenamePiece);
		}
		return r;
	}

	/**
	 * Open the file
	 */
	@Override
	public void doAction() {
		parentApp.openFile(file);
		parentApp.toggleFoobar();
	}

	/**
	 * @return The name of this Fooable
	 */
	@Override
	public String toString() {
		return file.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileFooable other = (FileFooable) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}

}
