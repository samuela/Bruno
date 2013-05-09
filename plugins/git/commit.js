importPackage(java.lang);
importPackage(java.util);
importPackage(Packages.javax.swing);


//println("dialog");
//var al = new ArrayList();


//var pb = new ProcessBuilder("git", "commit", "-m", mes);
//var pb = new ProcessBuilder("ls");
var expl = bruno.getProjectExplorer();
var file = expl.getCurrentFolder();
println("file" + file);
if(file==null){
    JOptionPane.showMessageDialog(null, "no directory loaded.", "commit failed", JOptionPane.ERROR_MESSAGE);
} else {
  //    pb.directory(file);
      var mes = JOptionPane.showInputDialog(bruno, "Enter commit message...");
      println(mes);
    Runtime.getRuntime().exec("git commit -m " + mes, null, file);
    //  pb.start();

    println("commit done");
}
