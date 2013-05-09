importPackage(Packages.javax.swing);

var comment = JOptionPane.showInputDialog(bruno, "Enter comment to revert to:");

bruno.getEditingWindow().getUndoController().revertByComment(comment);