package edithistory;

import java.util.List;
import java.util.ArrayList;

public class CompoundEdit extends Edit
{
    private List<Edit> edits;

    public CompoundEdit(Edit parent)
    {
	super(parent);
	edits = new ArrayList<>();
    }

    public CompoundEdit(Edit from, Edit to)
    {
	this((from.getOrder() <= to.getOrder())? from.getParent() : to.getParent());
	boolean fromIsTop = from.getOrder() <= to.getOrder();
	Edit top = fromIsTop? from : to;
	Edit bottom = fromIsTop? to : from;
	Edit current = top;
	while (current != bottom){
	    edits.add(current);
	    current = current.getChild();
	}
	
	if (from.getParent() != null)
	    from.getParent().setChild(this);
	if (to.getChild() != null)
	    to.getChild().setParent(this);
    }
	
    

}