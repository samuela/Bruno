package edithistory;

import java.util.List;
import java.util.ArrayList;

public class CompoundEdit extends Edit
{
    private Edit top;
    private Edit bottom;
	
    public CompoundEdit(Edit parent)
    {
	super(parent);
    }

    public CompoundEdit(Edit from, Edit to)
    {
	this((from.getOrder() <= to.getOrder())? from.getParent() : to.getParent());
	boolean fromIsTop = from.getOrder() <= to.getOrder();
	top = fromIsTop? from : to;
	bottom = fromIsTop? to : from;
	
	if (top.getParent() != null)
	    top.getParent().setChild(this);
	if (bottom.getChild() != null)
	    bottom.getChild().setParent(this);
	setType(bottom.getType());
    }

    @Override
	public boolean canUndo()
    {
	return bottom.canUndo();
    }

    @Override
	public boolean canRedo()
    {
	return bottom.canRedo();
    }

    @Override
	public boolean undone()
    {
	return bottom.undone();
    }

    @Override
	public void undo()
    {
	bottom.undo();
    }
    
    @Override
	public void redo()
    {
	bottom.redo();
    }
    
    @Override
	public void undo(Edit edit)
    {
	bottom.undo(edit);
    }
    
    @Override
	public void redo(Edit edit)
    {
	bottom.redo(edit);
    }
    
    @Override
	public void backInTime()
    {
	Edit current = bottom;
	while (current != top){
	    current.backInTime();
	    current = current.getParent();
	}
	current.backInTime();
    }

    @Override
	public void forwardInTime()
    {
	Edit current = top;
	while (current != bottom){
	    current.forwardInTime();
	    current = current.getChild();
	}
	current.forwardInTime();
    }

    @Override
	public boolean isCompound()
    {
	return true;
    }

    public Edit getTop()
    {
	return top;
    }

    public Edit getBottom()
    {
	return bottom;
    }

}
