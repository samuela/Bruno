importPackage(Packages.javax.swing);

var line = JOptionPane.showInputDialog(bruno, "Enter line number:");

var textArea = bruno.getEditingWindow().getTextArea();
if (line >= 1 && line <= textArea.getLineCount()){
    textArea.setCaretPosition(textArea.getLineStartOffset(line-1));
} 
