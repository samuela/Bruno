package edithistory;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction
{
    private static final long serialVersionUID = 1L;
    private UndoController undoController;

    public RedoAction(UndoController undoController)
    {
	super("Redo");
	this.undoController = undoController;
	setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
	if (undoController.canRedo()){
	    undoController.redo();
	    updateRedoState();
	    undoController.updateUndoState();
	}
    }

    protected void updateRedoState()
    {
	if (undoController.canRedo()){
	    setEnabled(true);
	    putValue(Action.NAME, undoController.getRedoPresentationName());
	}
	else{
	    setEnabled(false);
	    putValue(Action.NAME, "Redo");
	}
    }
}