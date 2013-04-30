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
    private UndoTree undoTree;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private EditHistoryView view;
    private Document document;

    public UndoController(Document document)
    {
	undoTree = new UndoTree(this);
	undoAction = new UndoAction(this);
	redoAction = new RedoAction(this);
	view = new EditHistoryView(this);
	this.document = document;
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

    public EditHistoryView getEditHistoryView()
    {
	return view;
    }

    public void updateUndoState()
    {
	undoAction.updateUndoState();
    }

    public void updateRedoState()
    {
	redoAction.updateRedoState();
    }

    public Document getDocument()
    {
	return document;
    }

    public void updateView(UndoNode undoNode)
    {
	view.addNode(undoNode);
    }

    public EditHistoryView getView()
    {
	return view;
    }

}