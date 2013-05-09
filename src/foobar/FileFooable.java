package foobar;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import frontend.Bruno;
import frontend.ProjectExplorer;

/**
 * A Fooable for Files.
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

	@Override
	public String getName() {
		return file.getName();
	}

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

	@Override
	public void doAction() {
		System.out.println("opening " + file);
		parentApp.openFile(file);
		parentApp.toggleFoobar();
	}

	@Override
	public String toString() {
		return file.getName();
	}

}
