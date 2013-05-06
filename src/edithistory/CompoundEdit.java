package edithistory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import com.google.common.collect.Lists;

import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

/**
 * This class represents several edits put into one.
 */
public class CompoundEdit implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<MyUndoableEdit> edits;
    private CompoundEdit parent;
    private CompoundEdit child;
    private String type;//addition, deletion, or empty
    private int length;
    private String comment;
    
    public CompoundEdit()
    {
	edits = new ArrayList<>();
	setType("empty");
    }

    public CompoundEdit(MyUndoableEdit e, CompoundEdit parent)
    {
	this();
	edits.add(e);
	setParent(parent);
	setType(e.getType());
	setLength(e.getLength());
    }

    public void undo(Document document)
    {
	for (MyUndoableEdit e : Lists.reverse(edits)){
	    e.undo(document);
	}
    }
    
    public boolean addEdit(MyUndoableEdit e)
    {
	String changedText = e.getText();
	if (e.getLength() > 1)
	    return false;
	else if (!getType().equals(e.getType()))
	    return false;
	else if (getLength() >= 5 && (changedText.equals(" ") || changedText.equals("\n") || changedText.equals("\t"))) {
	    return false;
	}
	else{
	    edits.add(e);
	    setLength(getLength() + e.getLength());
	    return true;
	}
    }

    /* Getters and Setters */
    public CompoundEdit getParent()
    {
	return parent;
    }

    public void setParent(CompoundEdit parent)
    {
	this.parent = parent;
    }
    
    public String getType()
    {
	return type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public int getLength()
    {
	return length;
    }

    public void setLength(int length)
    {
	this.length = length;
    }

    public String getComment()
    {
	return comment;
    }

    public void setComment(String comment)
    {
	this.comment = comment;
    }
}