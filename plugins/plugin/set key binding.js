importPackage(java.lang);
importPackage(Packages.javax.swing);
importPackage(java.awt.event);
importPackage(java.io)

var key = JOptionPane.showInputDialog(bruno, "Enter a letter.");
while(key==null || !key.matches("[a-zA-Z]")){
    key = JOptionPane.showInputDialog(bruno, "Please enter one letter.");
}
key = key.toUpperCase();

var script = JOptionPane.showInputDialog(bruno, "Enter a script name.");

var filePath = bruno.SUPPORT_DIR + "/plugins/config/key bindings.js";
var pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));

    pw.println("bindKeyToScript(KeyEvent.VK_" + key + ", \"control\", \"" + script + "\");");
    pw.close();





