importClass(org.fife.ui.rsyntaxtextarea.SyntaxConstants);
importClass(javax.swing.JOptionPane);

var languages = ["Java", "JavaScript", "Plain Text", "C", "Scala", "Ruby", "Python", "XML", "JSON", "Lisp", "LaTeX", "Perl", "C++", "CSS"];

var choice = JOptionPane.showInputDialog(
                    null,
                    "Select a language",
                    "Select a language",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    languages,
                    "Java");

bruno.removeCompletion();

var editingWindow = bruno.getEditingWindow();

if (choice == null) {
     // do nothing
}
else if (choice.equals("Java")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    bruno.addJavaCompletion();
}
else if (choice.equals("JavaScript")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
}
else if (choice.equals("Plain Text")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
}
else if (choice.equals("C")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_C);
}
else if (choice.equals("Scala")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_SCALA);
}
else if (choice.equals("Ruby")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_RUBY);
}
else if (choice.equals("Python")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
}
else if (choice.equals("XML")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_XML);
}
else if (choice.equals("JSON")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
}
else if (choice.equals("Lisp")){
   editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
}
else if (choice.equals("LaTeX")){
   editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
}
else if (choice.equals("Perl")){
   editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_PERL);
}
else if (choice.equals("C++")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
}
else if (choice.equals("CSS")) {
    editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
}
else {
    JOptionPane.showMessageDialog(null, "Invalid language choice.", "Select a language", JOptionPane.ERROR_MESSAGE);
}
