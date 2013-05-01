package foobar;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import frontend.Bruno;

/**
 * A Fooable for Files.
 * 
 * @author samuelainsworth
 * 
 */
public class FileFooable implements Fooable {

	private Bruno parentApp;
	private File file;

	public FileFooable(Bruno parentApp, File file) {
		this.parentApp = parentApp;
		this.file = file;
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Set<String> getKeywords() {
		// TODO split path and add as keywords
		Set<String> r = new HashSet<>();
		r.add(file.getName());
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
