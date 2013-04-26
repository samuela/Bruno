package undotree;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;

public class UndoController implements UndoableEditListener
{
    UndoTree undoTree;
    UndoAction undoAction;
    RedoAction redoAction;

    public UndoController()
    {
	undoTree = new UndoTree();
	undoAction = new UndoAction(this);
	redoAction = new RedoAction(this);
    }

    @Override
	public void undoableEditHappened(UndoableEditEvent e)
    {
	undoTree.addEdit(e.getEdit());
	undoAction.updateUndoState();
	redoAction.updateRedoState();
    }

    public UndoTree getUndoTree()
    {
	return undoTree;
    }

    public UndoAction getUndoAction()
    {
	return undoAction;
    }

    public RedoAction getRedoAction()
    {
	return redoAction;
    }

    public void updateUndoState()
    {
	undoAction.updateUndoState();
    }

    public void updateRedoState()
    {
	redoAction.updateRedoState();
    }

}