package edithistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class NodeComponent extends JPanel {
    private static final long serialVersionUID = 1L;
    private UndoController undoController;
    private CompoundEdit edit;
    public static final Border thinBlackBorder = BorderFactory.createLineBorder(Color.black, 1, false);
    public static final Border thickBlackBorder = BorderFactory.createLineBorder(Color.black, 3, false);
    public static final Border thickBlueBorder = BorderFactory.createLineBorder(Color.blue, 3, false);
    public static final int brightness = 235;
    private boolean selectedForCompound;

    public NodeComponent(UndoController uc) {
	undoController = uc;
	setBorder(thinBlackBorder);
	setOpaque(true);
	selectedForCompound = false;

	addMouseListener(new MouseAdapter() {
		@Override
		    public void mouseEntered(MouseEvent e) {
		    NodeComponent.this.mouseEntered(e);
		}

		@Override
		    public void mouseClicked(MouseEvent e) {
		    NodeComponent.this.mouseClicked(e);
		}
	    });
    }

    public NodeComponent(CompoundEdit edit, UndoController undoController) {
	this(undoController);
	this.edit = edit;
	setColor();
    }

    public void mouseEntered(MouseEvent e) {
	EditHistoryView view = undoController.getView();
	if (view.getSelectedNode() != null){
	    view.getSelectedNode().unselectedBorder();
	}
	selectedBorder();
	view.setSelectedNode(this);
	undoController.getView().setDocument(getDocument());
    }
    
    public void mouseClicked(MouseEvent e) {
	int modifiers = e.getModifiers();
	if (modifiers == 4 || modifiers == 18) {
	    changeSelectionForCompound();
	}
    }
    
    @Override
	public Dimension getPreferredSize() {
	Dimension d = super.getPreferredSize();
	return new Dimension((int) d.getWidth(), 50);
    }

    @Override
	public Dimension getMaximumSize() {
	return new Dimension(5000000, 50);
    }

    public void setColor() {
	if (edit.getType().equals("addition")) {
	    setBackground(new Color(0, brightness, 0));
	}
	else if (edit.getType().equals("deletion")) {
	    setBackground(new Color(brightness, 0, 0));
	} 
	else {
	    setBackground(Color.gray);// top node
	}
    }

    public void selectedBorder()
    {
	if (!selectedForCompound)
	    setBorder(thickBlackBorder);
	else
	    setBorder(thickBlueBorder);
    }

    public void unselectedBorder()
    {
	if (!selectedForCompound)
	    setBorder(thinBlackBorder);
	else
	    setBorder(thickBlueBorder);
    }

    public void setComment(String comment)
    {
	edit.setComment(comment);
	if (comment != null && !(comment.equals(""))) {
	    setBackground(Color.orange);
	} else
	    setColor();
    }

    public String getComment()
    {
	return edit.getComment();
    }

    public Document getDocument()
    {
	return undoController.restoreTo(edit);
    }

    public UndoController getUndoController() {
	return undoController;
    }

    public CompoundEdit getEdit()
    {
	return edit;
    }

    public boolean isSelectedForCompound() {
	return selectedForCompound;
    }

    public void selectForCompound() {
	if (!isSelectedForCompound()) {
	    selectedForCompound = true;
	    setBorder(thickBlueBorder);
	    undoController.selectNodeForCompound(this);
	}
    }

    public void deselectForCompound() {
	if (isSelectedForCompound()) {
	    selectedForCompound = false;
	    undoController.deselectNodeForCompound(this);
	    setBorder(thinBlackBorder);
	}
    }

    public void changeSelectionForCompound() {
	if (isSelectedForCompound()) {
	    deselectForCompound();
	} 
	else
	    selectForCompound();
    }

    /*    public Edit getLastEdit() {
	return edit;
	}*/

    /*
     * public void setUndoController(UndoController undoController) {
     * this.undoController = undoController; }
     */


    /*
     * public NodeComponent makeOppositeComponent(EditHistoryView view) { return
     * edit.getOppositeEdit(undoController); }
     */

}
