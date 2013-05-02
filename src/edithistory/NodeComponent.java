package edithistory;

import java.util.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class NodeComponent extends JPanel
{
    private static final long serialVersionUID = 1L;
    private UndoController undoController;
    private Edit edit;
    public static final Border thinBlackBorder = BorderFactory.createLineBorder(Color.black, 1, false);
    public static final Border thickBlackBorder = BorderFactory.createLineBorder(Color.black, 3, false);
    public static final Border thickBlueBorder = BorderFactory.createLineBorder(Color.blue, 3, false);
    private boolean selectedForCompound;

    public NodeComponent(UndoController uc)
    {
	undoController = uc;
	setBorder(thinBlackBorder);
	setOpaque(true);
	selectedForCompound = false;
	
	addMouseListener(new MouseAdapter(){
		@Override
		    public void mouseEntered(MouseEvent e)
		{
		    EditHistoryView view = undoController.getView();
		    if (view.getClickedNode() != null)
			if (!view.getClickedNode().isSelectedForCompound())
			    view.getClickedNode().setBorder(thinBlackBorder);
		    if (!selectedForCompound)
			setBorder(thickBlackBorder);
		    view.setClickedNode(NodeComponent.this);
		    undoController.getView().setDocument(getDocument());
		}
		
		@Override
		    public void mouseClicked(MouseEvent e)
		{
		    if ((SwingUtilities.isRightMouseButton(e) ||
			 e.getModifiers() == 18)){
			changeSelectionForCompound();
		    }
		}
	    });
    }
    
    public NodeComponent(Edit edit, UndoController undoController)
    {
	this(undoController);
	this.edit = edit;
	setColor();
    }
    
    @Override
	public Dimension getPreferredSize()
    {
	Dimension d = super.getPreferredSize();
	return new Dimension((int) d.getWidth(), 50);
    }

    @Override
	public Dimension getMaximumSize()
    {
	return new Dimension(5000000, 50);
    }

    public void setColor()
    {
	if (edit.getType().equals("addition")){
	    setBackground(Color.green);
	}
	else if (edit.getType().equals("deletion")){
	    setBackground(Color.red);
	}
	else{
	    setBackground(Color.gray);//top node
	}
    }

    public void setComment(String comment)
    {
	edit.setComment(comment);
	if (!(comment.equals(""))){
	    setBackground(Color.orange);
	}
	else
	    setColor();
    }

    public String getComment()
    {
	return edit.getComment();
    }
    
    public Document getDocument()
    {
	undoController.backInTime(edit);
	Document document = undoController.getDocument();
	RSyntaxDocument restoredDocument = new RSyntaxDocument(SyntaxConstants.SYNTAX_STYLE_JAVA);
	try{
	    restoredDocument.insertString(0,document.getText(0,document.getLength()),null);
	}
	catch(BadLocationException e1){}
	undoController.forwardInTime(edit);
	return restoredDocument;
    }
    
    public void revert()
    {
	Document restoredDocument = getDocument();
	Document currentDocument = undoController.getDocument();
	try{
	    currentDocument.remove(0, currentDocument.getLength());
	    currentDocument.insertString(0, restoredDocument.getText(0, restoredDocument.getLength()), null);
	}
	catch(BadLocationException e){}
    }
    
    public boolean isSelectedForCompound()
    {
	return selectedForCompound;
    }

    public void selectForCompound()
    {
	if (!isSelectedForCompound()){
	    selectedForCompound = true;
	    setBorder(thickBlueBorder);
	    undoController.selectNodeForCompound(this);
	}
    }

    public void deselectForCompound()
    {
	if (isSelectedForCompound()){
	    selectedForCompound = false;
	    undoController.deselectNodeForCompound(this);
	    setBorder(thinBlackBorder);
	}
    }

    public void changeSelectionForCompound()
    {
	if (isSelectedForCompound()){
	    deselectForCompound();
	}
	else
	    selectForCompound();
    }

    /*    public Edit getEdit()
    {
	return edit;
	}*/
    
    /*    public void setUndoController(UndoController undoController)
    {
	this.undoController = undoController;
    }

    public UndoController getUndoController()
    {
	return undoController;
	}*/

}
