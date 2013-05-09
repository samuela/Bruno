importPackage(Packages.javax.swing);

var comment = JOptionPane.showInputDialog(bruno, "Enter comment to search for:");

bruno.getEditingWindow().getUndoController().showLastComment(comment);