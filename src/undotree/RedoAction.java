package undotree;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {
    public static final long serialVersionUID = 1L;
    private UndoController undoController;
    private UndoTree undoTree;

    public RedoAction(UndoController undoController) {
	super("Redo");
	this.undoController = undoController;
	this.undoTree = undoController.getUndoTree();
	setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
	try {
	    undoTree.redo();
	} catch (CannotRedoException ex) {
	    System.out.println("Unable to redo: " + ex);
	    ex.printStackTrace();
	}
	updateRedoState();
	undoController.updateUndoState();
    }

    protected void updateRedoState() {
	if (undoTree.canRedo()) {
	    setEnabled(true);
	    putValue(Action.NAME, undoTree.getRedoPresentationName());
	} else {
	    setEnabled(false);
	    putValue(Action.NAME, "Redo");
	}
    }
}
