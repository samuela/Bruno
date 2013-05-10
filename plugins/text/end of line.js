var textArea = bruno.getEditingWindow().getTextArea();
var caretPosition = textArea.getCaretPosition();
var line = textArea.getLineOfOffset(caretPosition);
var end = textArea.getLineEndOffset(line);
if (textArea.getLineCount() == line + 1 || end == 0){
    textArea.setCaretPosition(end);
}
else{
    textArea.setCaretPosition(end-1);
}