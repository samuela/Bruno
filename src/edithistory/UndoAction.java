package edithistory;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction
{
    private static final long serialVersionUID = 1L;
    private UndoController undoController;

    public UndoAction(UndoController undoController)
    {
	super("Undo");
	this.undoController = undoController;
	setEnabled(false);
    }
    
    public void actionPerformed(ActionEvent e)
    {
	if (undoController.canUndo()){
	    undoController.undo();
	    updateUndoState();
	    undoController.updateRedoState();
	}
    }

    protected void updateUndoState()
    {
	if (undoController.canUndo()){
	    setEnabled(true);
	    putValue(Action.NAME, undoController.getUndoPresentationName());
	}
	else{
	    setEnabled(false);
	    putValue(Action.NAME, "Undo");
	}
    }
}
