package edithistory;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;
import javax.swing.undo.UndoableEdit;

public class UndoController implements UndoableEditListener
{
    private Edit lastEdit;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private EditHistoryView view;
    private Document document;

    public UndoController(Document document)
    {
	lastEdit = new Edit();
	undoAction = new UndoAction(this);
	redoAction = new RedoAction(this);
	view = new EditHistoryView(this);
	this.document = document;
    }

    public void undo()
    {
	lastEdit = new UndoEdit(lastEdit);
	view.addNode(lastEdit);
    }

    public void redo()
    {
	lastEdit = new RedoEdit(lastEdit);
	view.addNode(lastEdit);
    }

    public boolean canUndo()
    {
	return lastEdit.canUndo();
    }

    public boolean canRedo()
    {
	return lastEdit.canRedo();
    }

    public void addEdit(UndoableEdit e)
    {
	if (lastEdit.addEdit(e))
	    return;
	else{
	    createNewEdit(e);
	}
    }

    public void createNewEdit(UndoableEdit e)
    {
	lastEdit = new TextEdit(e, lastEdit);
	view.addNode(lastEdit);
    }
    
    @Override
	public void undoableEditHappened(UndoableEditEvent e)
    {
	addEdit(e.getEdit());
	undoAction.updateUndoState();
	redoAction.updateRedoState();
    }

    public void backInTime(Edit e)
    {
	Edit timeFrame = lastEdit;
	while (timeFrame != e){
	    timeFrame.backInTime();
	    timeFrame = timeFrame.getParent();
	}
    }

    public void forwardInTime(Edit e)
    {
	Edit timeFrame = e.getChild();
	while (timeFrame != null){
	    timeFrame.forwardInTime();
	    timeFrame = timeFrame.getChild();
	}
    }

    public Document getDocument()
    {
	return document;
    }

    public EditHistoryView getView()
    {
	return view;
    }

    /* Utilities */
    public void updateUndoState()
    {
	undoAction.updateUndoState();
    }

    public void updateRedoState()
    {
	redoAction.updateRedoState();
    }
    
    public String getUndoPresentationName()
    {
	return lastEdit.getUndoPresentationName();
    }

    public String getRedoPresentationName()
    {
	return lastEdit.getRedoPresentationName();
    }

    public UndoAction getUndoAction()
    {
	return undoAction;
    }

    public RedoAction getRedoAction()
    {
	return redoAction;
    }

}
