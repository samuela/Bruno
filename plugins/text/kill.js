var textArea = bruno.getEditingWindow().getTextArea();
var caretPosition = textArea.getCaretPosition();
var line = textArea.getLineOfOffset(caretPosition);
var end = textArea.getLineEndOffset(line);
textArea.setSelectionStart(caretPosition);
textArea.setSelectionEnd(end);
textArea.cut();
