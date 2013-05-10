package org.bruno.frontend;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class BrunoFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return !f.isHidden() && !f.getName().endsWith(Bruno.FILE_EXT)
				&& !f.getName().endsWith("~")
				&& !f.getName().endsWith(".class");
	}

	@Override
	public String getDescription() {
		return "All Bruno files";
	}

}
