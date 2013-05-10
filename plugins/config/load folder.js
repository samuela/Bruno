importPackage(Packages.java.io);
var file = new java.io.File("/Users/mgscheer/Dropbox/Brown/CS/cs032/archiveMoved");
if (file.exists()){
bruno.getProjectExplorer().showFolder(file);
}
else{
bruno.getProjectExplorer().showFolder(null);
}
