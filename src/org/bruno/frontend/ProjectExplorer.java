package org.bruno.frontend;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import java.util.prefs.Preferences;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;

import org.bruno.foobar.FileFooable;
import org.bruno.frontend.FileSystemTreeModel.TreeFileObject;

public class ProjectExplorer extends JPanel implements DropTargetListener {

    /**
     *
     */
    private static final long serialVersionUID = -6000650682129841885L;

    /*	private static final Preferences prefs = Preferences.userRoot().node(
	"projectExplorer");*/

    private final Bruno parentApp;
    private final CardLayout layout;
    // private final DropTarget dropTarget;
    private final JTree fileTree;
    private File currentFolder;

    public ProjectExplorer(final Bruno parentApp) {
	this.parentApp = parentApp;

	layout = new CardLayout();
	setLayout(layout);

	// Empty label
	JLabel emptyLabel = new JLabel(
				       "<html><div style=\"text-align: center; color: #888888;\">Drag and drop folders<br /> here to get started</div></html>");
	emptyLabel.setFont(new Font("Arial", Font.BOLD, 14));
	emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
	add(emptyLabel, "empty");

	// File tree
	fileTree = new JTree();

	fileTree.addMouseListener(new MouseAdapter() {
		@Override
		    public void mousePressed(MouseEvent e) {
		    int selectedRow = fileTree.getRowForLocation(e.getX(), e.getY());
		    TreePath selectedPath = fileTree.getPathForLocation(e.getX(),
									e.getY());
		    if (selectedRow != -1 && e.getClickCount() == 2) {
			File file = ((TreeFileObject) selectedPath
				     .getLastPathComponent()).getFile();
			if (file.isFile()) {
			    parentApp.openFile(file);
			}
		    }
		}
	    });

	fileTree.addKeyListener(new KeyListener() {

		@Override
		    public void keyTyped(KeyEvent e) {

		}

		@Override
		    public void keyReleased(KeyEvent e) {

		}

		@Override
		    public void keyPressed(KeyEvent e) {
		    TreePath selectedPath = fileTree.getSelectionPath();
		    if (e.getKeyCode() == KeyEvent.VK_ENTER && selectedPath != null) {
			TreeFileObject selected = (TreeFileObject) selectedPath
			    .getLastPathComponent();
			File file = selected.getFile();
			if (file.isFile()) {
			    parentApp.openFile(file);
			}
		    }
		}
	    });

	add(fileTree, "tree");

	// Set up drag n drop
	new DropTarget(this, this);

	/*		// Load in previous folder
			File prevCurrentFolder = new File(prefs.get("currentFolder", ""));
			if (prevCurrentFolder.exists()) {
			showFolder(prevCurrentFolder);
			}*/
    }

    public void showFolder(File folder) {
	if (folder == null) {
	    layout.show(this, "empty");
	} else {
	    fileTree.setModel(new FileSystemTreeModel(folder));
	    layout.show(this, "tree");
	    addFooables(folder);
	}

	if (currentFolder != null) {
	    removeFooables(currentFolder);
	}

	currentFolder = folder;

	/*		// Update Preferences
			prefs.put("currentFolder",
			currentFolder != null ? currentFolder.getAbsolutePath() : "");*/
	printLoadFolder(folder);
    }

    public void printLoadFolder(File folder)
    {
	PrintWriter pw = null;
	try{
	    pw = new PrintWriter(new BufferedWriter(new FileWriter(Bruno.SUPPORT_DIR+"/plugins/config/load folder.js")));
	}
	catch(IOException ex){
	    ex.printStackTrace();
	}
	if (folder == null){
	    pw.println("");
	    pw.flush();
	    pw.close();
	}
	else{
	    pw.println("importPackage(Packages.java.io);");
	    pw.println("bruno.getProjectExplorer().showFolder(new java.io.File("+"\""+folder.getAbsolutePath()+"\""+"));");
	    pw.flush();
	    pw.close();
	}
    }

    private Set<FileFooable> getAllFileFooables(File f) {
	Set<FileFooable> r = new HashSet<>();
	FileFilter bff = new BrunoFileFilter();
	if (bff.accept(f)) {
	    if (f.isFile()) {
		r.add(new FileFooable(parentApp, this, f));
	    } else {
		for (File file : f.listFiles()) {
		    r.addAll(getAllFileFooables(file));
		}
	    }
	}
	return r;
    }

    private void addFooables(File f) {
	if (parentApp != null) {
	    for (FileFooable foo : getAllFileFooables(f)) {
		parentApp.getFoobar().addFooable(foo);
	    }
	}
    }

    private void removeFooables(File f) {
	if (parentApp != null) {
	    for (FileFooable foo : getAllFileFooables(f)) {
		parentApp.getFoobar().removeFooable(foo);
	    }
	}
    }

    @Override
	public void dragEnter(DropTargetDragEvent dtde) {

    }

    @Override
	public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
	public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    @Override
	public void dragExit(DropTargetEvent dte) {

    }

    @SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent dtde) {
	int dropAction = dtde.getDropAction();
	dtde.acceptDrop(dropAction);

	try {
	    Transferable data = dtde.getTransferable();
	    if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
		List<File> files = (List<File>) data
		    .getTransferData(DataFlavor.javaFileListFlavor);
		showFolder(files.get(0));
	    }
	} catch (UnsupportedFlavorException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    dtde.dropComplete(true);
	    repaint();
	}
    }

    public File getCurrentFolder() {
	return currentFolder;
    }
}
