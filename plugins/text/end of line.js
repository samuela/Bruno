var textArea = bruno.getEditingWindow().getTextArea();
var caretPosition = textArea.getCaretPosition();
var line = textArea.getLineOfOffset(caretPosition);
var end = textArea.getLineEndOffset(line);
if (end > 0){
    textArea.setCaretPosition(end-1);
}
else{
    textArea.setCaretPosition(end);
}
