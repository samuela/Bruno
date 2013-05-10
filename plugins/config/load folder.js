importPackage(Packages.java.io);
var file = new java.io.File("/Users/mgscheer/Library/Application Support/Bruno/plugins");
if (file.exists()){
bruno.getProjectExplorer().showFolder(file);
}
else{
bruno.getProjectExplorer().showFolder(null);
}
