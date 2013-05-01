package edithistory;

import java.util.List;
import java.util.ArrayList;
import javax.swing.undo.UndoableEdit;

import com.google.common.collect.Lists;

public class TextEdit extends Edit
{
    private List<UndoableEdit> edits;
    private boolean undone;

    public TextEdit(UndoableEdit e, Edit parent)
    {
	super(parent);
	edits = new ArrayList<>();
	edits.add(e);
	setType(e.getPresentationName());
	setSize(Edit.editSize(e));
	undone = false;
    }

    @Override
	public boolean canUndo()
    {
	if (!undone())
	    return true;
	else
	    return getParent().canUndo();
    }

    @Override
	public boolean canRedo()
    {
	return undone();
    }

    @Override
	public boolean undone()
    {
	return undone;
    }

    @Override
	public void undo(Edit edit)
    {
	if (!undone()){
	    for (UndoableEdit e : Lists.reverse(edits)){
		e.undo();
	    }
	    undone = true;
	    edit.setType(Edit.oppositeType(getType()));
	    edit.setSize(getSize());
	    edit.setAffectedEdit(this);
	}
	else{
	    getParent().undo(edit);
	}
    }

    @Override
	public void redo(Edit edit)
    {
	if (undone() && (!(getParent() instanceof TextEdit) || !getParent().undone())){
	    for (UndoableEdit e : edits){
		e.redo();
	    }
	    undone = false;
	    edit.setType(getType());
	    edit.setSize(getSize());
	    edit.setAffectedEdit(this);
	}
	else{
	    getParent().redo(edit);
	}
    }
 
    @Override
	public void backInTime()
    {
	if (!undone()){
	    for (UndoableEdit e : Lists.reverse(edits)){
		e.undo();
	    }
	    undone = true;
	}
	else{
	    for (UndoableEdit e : edits){
		e.redo();
	    }
	    undone = false;
	}
    }
  
    @Override
	public void forwardInTime()
    {
	backInTime();
    }

}