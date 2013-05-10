package org.bruno.frontend;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class BrunoFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return !f.isHidden() && !f.getName().endsWith(Bruno.FILE_EXT)
                && !f.getName().endsWith("~");
    }

    @Override
    public String getDescription() {
        return "All Bruno files";
    }

}
