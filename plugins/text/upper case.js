var textArea = bruno.getEditingWindow().getTextArea();
var selected = textArea.getSelectedText();
if (selected != null){
    textArea.replaceSelection(selected.toUpperCase());
}
