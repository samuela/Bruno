importPackage(Packages.javax.swing);
/*importPackage(Packages.java.io);
importPackage(Packages.org.bruno.frontend);

var rootFolder = bruno.getProjectExplorer().getCurrentFolder();

function process(file) {
    if (file.isFile()) {
	var doc = new org.bruno.frontend.DocumentModel(file);
	if (doc.getMetadataFile().exists()) {
	    // Process a file
	    //      println(file);
	    
	    var ew;
	    var doc = bruno.getEditingWindow().getDoc();
	    if (doc != null && !doc.getFile().equals(file)) {
		ew = new org.bruno.frontend.EditingWindow(null, doc);
	    } else {
		ew = bruno.getEditingWindow();
	    }
	    
	    // Do the revert
	    ew.getUndoController().revertByComment(comment);

	    // Save
	    ew.save();
	}
    } else {
	var fileList = file.listFiles();
	for (var i = 0; i < fileList.length; i++) {
	    process(fileList[i]);
	}
    }
}
*/
var rootFolder = bruno.getProjectExplorer().getCurrentFolder();
if (rootFolder != null) {
    var comment = JOptionPane.showInputDialog(bruno, "Enter edit message to revert to:");
    bruno.revertAll(comment);
} else {
    JOptionPane.showMessageDialog(null, "Must have a working folder to revert files in!");
}