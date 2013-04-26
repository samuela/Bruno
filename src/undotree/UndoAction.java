package undotree;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
    public static final long serialVersionUID = 1L;
    private UndoController undoController;
    private UndoTree undoTree;

    public UndoAction(UndoController undoController) {
	super("Undo");
	this.undoController = undoController;
	this.undoTree = undoController.getUndoTree();
	setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
	try {
	    undoTree.undo();
	} catch (CannotUndoException ex) {
	    System.out.println("Unable to undo: " + ex);
	    ex.printStackTrace();
	}
	updateUndoState();
	undoController.updateRedoState();
    }

    protected void updateUndoState() {
	if (undoTree.canUndo()) {
	    setEnabled(true);
	    putValue(Action.NAME, undoTree.getUndoPresentationName());
	} else {
	    setEnabled(false);
	    putValue(Action.NAME, "Undo");
	}
    }
}
