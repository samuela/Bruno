importPackage(Packages.javax.swing);
importPackage(Packages.java.io);
importPackage(Packages.org.bruno.frontend);

var rootFolder = bruno.getProjectExplorer().getCurrentFolder();

function process(file) {
  if (file.isFile()) {
    var doc = new org.bruno.frontend.DocumentModel(file);
    if (doc.getMetadataFile().exists()) {
      // Process a file
      println(file);

      var ew = new org.bruno.frontend.EditingWindow(null, doc);

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

if (rootFolder != null) {
  var comment = JOptionPane.showInputDialog(bruno, "Enter edit message to revert to:");
  process(rootFolder);
} else {
  JOptionPane.showMessageDialog(null, "Must have a working folder to revert files in!");
}