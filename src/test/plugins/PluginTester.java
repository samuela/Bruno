package test.plugins;

import org.junit.Ignore;
import org.junit.Test;
import plugins.Plugin;
import plugins.PluginManager;
import plugins.Script;
import plugins.SimplePluginManager;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

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
    public void noExtension(){
        PluginManager simple = new SimplePluginManager();
        Plugin p = simple.loadPlugin(new File("plugins/noextension"));
     //   System.out.println(p.getScriptsByName());
        assertTrue(p.getScriptsByName().isEmpty());
    }

    @Test
    public void cantRead(){
        SimplePluginManager simple = new SimplePluginManager();
        simple.loadPlugin(new File("plugins/cantread"));
        Plugin helloagain = simple.loadPlugin(new File("plugins/helloagain"));
        assertTrue(simple.contains(helloagain, "helloworld", "py"));
    }

    @Test
    public void duplicateName(){
        SimplePluginManager simple = new SimplePluginManager();
        Plugin helloagain = simple.loadPlugin(new File("plugins/helloagain"));
        Plugin hello = simple.loadPlugin(new File("plugins/hello"));
        assertTrue(simple.contains(helloagain, "helloworld", "py"));
        assertTrue(simple.contains(hello, "alert", "js"));
        assertTrue(simple.contains(hello, "write", "py"));
        //KEY ASSERT
        assertFalse(simple.contains(hello, "helloworld", "js"));
        //just to be sure script with dup name belongs only to the first plugin searched
        assertFalse(simple.contains(hello, "helloworld", "py"));
    }

    @Test
    public void badExtension(){
        SimplePluginManager simple = new SimplePluginManager();
        Plugin p = simple.loadPlugin(new File("plugins/badext"));
        assertFalse(simple.contains(p, "ahoy", "py~"));
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


        //SEE IT IN ACTION
        try {

            simpleManager.executeScript(hello);
        } catch (ScriptException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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

    @Test
    public void exposeMultipleScripts() throws ScriptException {
        SimplePluginManager simple = new SimplePluginManager();
        Plugin expose = simple.loadPlugin(new File("plugins/expose"));

       //expose i to javascript
        Script inc = expose.getScriptByName("inc");

        int[] ar = {3};
        simple.exposeVariable("ar", ar);
        simple.executeScript(inc);
        assertTrue(4==ar[0]);

        //expose i to python
        Script sq = expose.getScriptByName("sq");
        simple.exposeVariable("ar", ar);
        simple.executeScript(sq);
        assertTrue(16 == ar[0]);
    }


    @Test
    public void exposeObject() throws ScriptException {
        SimplePluginManager simple = new SimplePluginManager();
        Plugin expose = simple.loadPlugin(new File("plugins/expose"));
        Script array = expose.getScriptByName("array");
        int[] ar = {0};
        simple.exposeVariable("ar", ar);
        simple.executeScript(array);
     //   int[] ar2 = (int[])simple.getVariable("ar");
        assertTrue(1 == ar[0]);

        Socket s = new Socket();
        assertFalse(s.isClosed());
        simple.exposeVariable("s", s);
        simple.executeScript(expose.getScriptByName("close"));
        assertTrue(s.isClosed());
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
