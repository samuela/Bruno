importClass(org.fife.ui.rsyntaxtextarea.SyntaxConstants);
importClass(javax.swing.JOptionPane);

var languages = ["Java", "C", "Scala", "Ruby", "Python", "XML", "Lisp", "LaTeX", "Perl", "bad language"];

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
else if (choice.equals("Lisp")){
   editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
}
else if (choice.equals("LaTeX")){
   editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
}
else if (choice.equals("Perl")){
   editingWindow.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_PERL);
}
else {
    JOptionPane.showMessageDialog(null, "Invalid language choice.", "Select a language", JOptionPane.ERROR_MESSAGE);
}
