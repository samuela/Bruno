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
    public static final int brightNess = 235;
    //    private boolean selectedForCompound;

    public NodeComponent(UndoController uc) {
	undoController = uc;
	setBorder(thinBlackBorder);
	setOpaque(true);
	//	selectedForCompound = false;

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
	if (view.getSelectedNode() != null)
		view.getSelectedNode().setBorder(thinBlackBorder);
	setBorder(thickBlackBorder);
	view.setSelectedNode(this);
	undoController.getView().setDocument(getDocument());
    }

    public void mouseClicked(MouseEvent e) {
	/*	int modifiers = e.getModifiers();
	if (modifiers == 4 || modifiers == 18) {
	    changeSelectionForCompound();
	    }*/
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
	    setBackground(new Color(0, brightNess, 0));
	} else if (edit.getType().equals("deletion")) {
	    setBackground(new Color(brightNess, 0, 0));
	} else {
	    setBackground(Color.gray);// top node
	}
    }

    public void setComment(String comment)
    {
	edit.setComment(comment);
	if (!(comment.equals(""))) {
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

    /*    public void revert() {

	Document restoredDocument = getDocument();
	Document currentDocument = undoController.getDocument();
	try {
	    currentDocument.remove(0, currentDocument.getLength());
	    currentDocument.insertString(0,
					 restoredDocument.getText(0, restoredDocument.getLength()),
					 null);
	} catch (BadLocationException e) {
	}
	}*/

    /*    public boolean isSelectedForCompound() {
	return selectedForCompound;
	}*/

    /*    public void selectForCompound() {
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
	} else
	    selectForCompound();
	    }*/

    /*    public Edit getLastEdit() {
	return edit;
	}*/

    /*
     * public void setUndoController(UndoController undoController) {
     * this.undoController = undoController; }
     */
    public UndoController getUndoController() {
	return undoController;
    }

    /*
     * public NodeComponent makeOppositeComponent(EditHistoryView view) { return
     * edit.getOppositeEdit(undoController); }
     */

}
