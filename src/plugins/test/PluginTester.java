package plugins.test;

import java.io.*;

import org.junit.Test;
import plugins.*;

import javax.script.ScriptException;

import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: jonathan
 * Date: 4/11/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginTester {

    @Test
    public void badFilesTest(){
        //TODO: 1. no extension 2. name already exists 3. permissions
    }
    @Test
    public void loadPluginTest() {
        PluginManager simpleManager = new SimplePluginManager();
        File f = new File("plugins/hello");
        Plugin p = simpleManager.loadPlugin(f);
        Script hello = p.getScriptByName("helloworld");

        //check manager state
        assertTrue(((SimplePluginManager) simpleManager).hasEngine("js"));
        assertTrue(((SimplePluginManager) simpleManager).supportsScript(hello));

        //check plugin state
        assertEquals("hello", p.getName());
        assertEquals(f.getAbsolutePath(), p.getPath());

                //check script state
        assertEquals("js", hello.getExtension());
        assertTrue(p == hello.getPlugin());
        assertEquals(f.getAbsolutePath() + "/helloworld.js", hello.getPath());
        assertEquals("helloworld", hello.getName());

        /*
        try {
            simpleManager.executeScript(hello);
        } catch (ScriptException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  */
    }

    @Test
    public void executeTest() {
        PluginManager simpleManager = new SimplePluginManager();
        File f = new File("plugins/hello");
        Plugin p = simpleManager.loadPlugin(f);

        Script writeHello = p.getScriptByName("write");

        assertTrue(((SimplePluginManager) simpleManager).supportsScript(writeHello));

        //   System.out.println(writeHello==null);
        try {
            simpleManager.executeScript(writeHello);

        } catch (ScriptException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        File written = new File("plugins/hello/hello.txt");
        assertTrue(written.exists());
        written.delete();
    }
/*
    //JUST SCREWING AROUND WITH JAVASCRIPT
    @Test
    public void alertTest(){
        PluginManager pm = new SimplePluginManager();
        File f = new File("plugins/hello");
        pm.loadPlugin(f);
        try{
            ((SimplePluginManager) pm).executeScript("alert");
        } catch (ScriptException e){
            e.printStackTrace();
        }
    }          */
}
