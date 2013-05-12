importPackage(Packages.java.io);

var file = new java.io.File("");
if (file.exists()){
    bruno.openFile(file);
}
else{
    bruno.openFile(null);
}