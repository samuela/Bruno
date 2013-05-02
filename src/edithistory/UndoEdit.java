package edithistory;

import javax.swing.undo.UndoableEdit;

public class UndoEdit extends Edit
{
    public UndoEdit(Edit parent)
    {
	super(parent);
	parent.undo(this);
    }

    @Override
	public void backInTime()
    {
	getAffectedEdit().forwardInTime();
    }

    @Override
	public void forwardInTime()
    {
	getAffectedEdit().backInTime();
    }

    @Override
	public NodeComponent getOppositeEdit(UndoController undoController)
    {
	return undoController.redo();
    }

}