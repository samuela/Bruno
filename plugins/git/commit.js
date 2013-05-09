importPackage(java.lang);
importPackage(java.util);
importPackage(Packages.javax.swing);


//println("dialog");
//var al = new ArrayList();
var mes = JOptionPane.showInputDialog(bruno, "Enter commit message...");
println(mes);

var pb = new ProcessBuilder("git", "commit", "-m", mes);
var d = bruno.getCurrentEditingWindow().getDoc();
println(d);
//var path = f.getPath();
//println(path);
/*var sep = System.getProperty("file.separator");
var dirPath = path.substring(0, lastIndexOf(sep)+1);
pb.directory(new File(dirPath));
pb.start();
*/
//println("dialog ending");
