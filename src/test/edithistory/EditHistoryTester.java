package test.edithistory;

import org.bruno.edithistory.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.junit.Ignore;
import org.junit.Test;

import javax.script.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;


public class EditHistoryTester
{

    public RSyntaxTextArea makeTextArea()
    {
	RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
	return textArea;
    }

    public List<String> stringToList(String s)
    {
	List<String> answer = new ArrayList<>();
	for (int i=0; i<s.length(); i++){
	    answer.add(s.substring(i, i+1));
	}
	return answer;
    }
    
    @Test
	public void testStringToList()
    {
	List<String> answer = new ArrayList<>();
	answer.add("e");
	answer.add("x");
	answer.add("a");
	answer.add("m");
	answer.add("p");
	answer.add("l");
	answer.add("e");
	assertTrue(answer.equals(stringToList("example")));
    }

    @Test
	public void addTextTest()
    {
	RSyntaxTextArea textArea = makeTextArea();
	UndoController undoController = new UndoController(textArea);
	assertTrue(undoController.getTextArea() == textArea);
	textArea.append("a few words");
	CompoundEdit undoEdit = undoController.getLastUndoEdit();
	CompoundEdit displayEdit = undoController.getLastDisplayEdit();
	assertTrue(displayEdit.getType().equals("addition"));
	assertTrue(displayEdit.getLength() == "a few words".length());
	assertTrue(undoEdit.getType().equals("addition"));
	assertTrue(undoEdit.getLength() == "a few words".length());
    }

    @Test
	public void removeTextTest()
    {
	RSyntaxTextArea textArea = makeTextArea();
	UndoController undoController = new UndoController(textArea);
	textArea.append("a few words");
	try{
	    textArea.getDocument().remove(0, textArea.getDocument().getLength());
	}
	catch(BadLocationException ex){}
	CompoundEdit undoEdit = undoController.getLastUndoEdit();
	CompoundEdit displayEdit = undoController.getLastDisplayEdit();
	assertTrue(displayEdit.getType().equals("deletion"));
	assertTrue(displayEdit.getLength() == "a few words".length());
	assertTrue(undoEdit.getType().equals("deletion"));
	assertTrue(undoEdit.getLength() == "a few words".length());
    }

    @Test
	public void chunkingTest()
    {
	RSyntaxTextArea textArea = makeTextArea();
	UndoController undoController = new UndoController(textArea);
	String toAdd = "lots of things";
	for (String c : stringToList(toAdd)){
	    textArea.append(c);
	}
	CompoundEdit undoEdit = undoController.getLastUndoEdit();
	CompoundEdit displayEdit = undoController.getLastDisplayEdit();
	assertTrue(undoEdit.getLength() == 1);
	assertTrue(displayEdit.getLength() == " things".length());
    }

    @Test
	public void undoTest()
    {

    }
    
}
