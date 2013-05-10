package test.edithistory;

import org.bruno.edithistory.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.junit.Ignore;
import org.junit.Test;

import javax.script.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Socket;
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

    @Test
    public void addTextTest()
    {
	RSyntaxTextArea textArea = makeTextArea();
	UndoController undoController = new UndoController(textArea);
	assertTrue(undoController.getTextArea() == textArea);
	textArea.append("tons of tons of new words ");
	System.out.println(textArea.getText());
	CompoundEdit lastEdit = undoController.getLastUndoEdit();
	//	assertTrue(lastEdit.getType().equals("addition"));
	System.out.println(lastEdit.getType());
	/*	assertTrue(lastEdit.getLength() == 
		assertTrue(*/
    }

}
