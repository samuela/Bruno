package foobar;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import frontend.Bruno;

/**
 * A wrapper Fooable for opening files.
 * 
 * @author samuelainsworth
 * 
 */
public class FileFooable implements Fooable {

	private Bruno parentApp;
	private File file;

	/**
	 * Create a Fooable that will open file in parentApp when executed.
	 * 
	 * @param parentApp The parent application
	 * @param file The file to be opened
	 */
	public FileFooable(Bruno parentApp, File file) {
		this.parentApp = parentApp;
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
		r.addAll(Arrays.asList(file.getName().split(".")));
		r.addAll(Arrays.asList(file.getName().split(" ")));
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

}
