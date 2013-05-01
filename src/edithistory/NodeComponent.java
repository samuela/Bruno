package edithistory;

import java.util.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class NodeComponent extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Edit edit;

    public NodeComponent(final Edit edit, final UndoController undoController)
    {
	this.edit = edit;
	setBorder(BorderFactory.createLineBorder(Color.black));
	setOpaque(true);
	if (edit.getType().equals("addition")){
	    setBackground(Color.green);
	}
	else if (edit.getType().equals("deletion")){
	    setBackground(Color.red);
	}
	else{
	    setBackground(Color.gray);//top node
	}
	addMouseListener(new MouseAdapter(){
		@Override
		    public void mouseClicked(MouseEvent e)
		{
		    undoController.backInTime(edit);
		    Document document = undoController.getDocument();
		    RSyntaxDocument restoredDocument = new RSyntaxDocument(SyntaxConstants.SYNTAX_STYLE_JAVA);
		    try{
			restoredDocument.insertString(0,document.getText(0,document.getLength()),null);
		    }
		    catch(BadLocationException e1){}
		    undoController.forwardInTime(edit);
		    undoController.getView().setDocument(restoredDocument);
		}
	    });
    }

    @Override
	public Dimension getPreferredSize()
    {
	return new Dimension(20, Math.min(edit.getSize()/10 + 1, 50));
    }

}