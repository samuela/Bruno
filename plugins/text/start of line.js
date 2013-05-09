var textArea = bruno.getEditingWindow().getTextArea();
var caretPosition = textArea.getCaretPosition();
var line = textArea.getLineOfOffset(caretPosition);
var start = textArea.getLineStartOffset(line);
textArea.setCaretPosition(start);
