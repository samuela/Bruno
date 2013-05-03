package test.plugins;

import foobar.ScriptFooable;
import org.junit.Ignore;
import org.junit.Test;
import plugins.Plugin;
import plugins.PluginManager;
import plugins.Script;
import plugins.SimplePluginManager;

import javax.script.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Socket;
import java.util.Set;
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
    public void stringTest(){
        int[] ar = {0};
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByExtension("js");
        engine.put("ar", ar);
        try {
            engine.eval("ar[0] = 1;");
        } catch (ScriptException e) {
            System.err.println("stringTest failed");
        }
        assertTrue(1 == ar[0]);
        try{
            engine.eval("ar[0]=2;");
        } catch (ScriptException e) {
            System.err.println("stringTest failed");
        }
        assertTrue(2 == ar[0]);
    }

    @Test
    public void noExtension(){
        SimplePluginManager simple = new SimplePluginManager();
        Plugin p = simple.loadPlugin(new File("plugins/noextension"));
     //   System.out.println(p.getScriptsByName());
        assertTrue(p==null);
    }

    @Test
    public void cantRead(){
        try{
            SimplePluginManager simple = new SimplePluginManager();
            simple.loadPlugin(new File("plugins/cantread"));
            Plugin helloagain = simple.loadPlugin(new File("plugins/helloagain"));
            assertTrue(simple.contains(helloagain, "helloworld", "py"));
        } catch (IllegalArgumentException e) {
            System.err.println("test cantRead failed because plugin is missing");
        }
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
        SimplePluginManager simpleManager = new SimplePluginManager();
        File f = new File("plugins/hello");
        Plugin p = simpleManager.loadPlugin(f);
        Script hello = p.getScriptByName("helloworld");

        //check manager state
        assertTrue((simpleManager).hasEngine("js"));
        assertTrue((simpleManager).supportsScript(hello));

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
         //   simpleManager.executeScript(hello);
        } catch (ScriptException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void multipleTimes(){
        SimplePluginManager s = new SimplePluginManager();
        s.loadPlugin(new File("plugins/write"));
        for(int i = 0; i < 2; i++){


        try {
            s.executeScript("write");
        } catch (ScriptException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        }
    }

    @Test
    public void executeTest() {
        SimplePluginManager simpleManager = new SimplePluginManager();
        File f = new File("plugins/hello");
        Plugin p = simpleManager.loadPlugin(f);

        Script writeHello = p.getScriptByName("write");

        assertTrue((simpleManager).supportsScript(writeHello));

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
     //   System.out.println(expose);
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

    @Test (expected=IllegalArgumentException.class)
    public void pluginNameExists(){
        SimplePluginManager simple = new SimplePluginManager();
        Plugin p = simple.loadPlugin(new File("plugins/hello"));
        Plugin p2 = simple.loadPlugin(new File("plugins/hello"));
        assertTrue(p2==null);
    }

    @Test
    public void getAllScriptFooablesTest(){
        SimplePluginManager p = new SimplePluginManager();
        Set<ScriptFooable> sfoos = p.getAllScriptFooables(new File("plugins"));
       // System.out.println(sfoos);
        //this particular plugins directory has 7 distinctly named, viable scripts
        assertTrue(7 == sfoos.size());
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
