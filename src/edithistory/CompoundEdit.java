package edithistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import com.google.common.collect.Lists;

/**
 * This class represents several edits put into one.
 */
public class CompoundEdit implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<MyUndoableEdit> edits;
    private CompoundEdit parent;
    private String type;// addition, deletion, or empty
    private int length;
    private String comment;
    private Mask mask;
    private List<Mask> masks;
    private boolean isRevert;

    public CompoundEdit() {
	edits = new ArrayList<>();
	setType("empty");
	setIsRevert(false);
	masks = new ArrayList<>();
    }

    public CompoundEdit(MyUndoableEdit e, CompoundEdit parent) {
	this();
	edits.add(e);
	setParent(parent);
	setType(e.getType());
	setLength(e.getLength());
    }

    /**
     * Undoes the effect of this CompoundEdit on a document.
     */
    public void undo(Document document) {
	for (MyUndoableEdit e : Lists.reverse(edits)) {
	    e.undo(document);
	}
    }

    /**
     * Tries to add a new MyUndoableEdit to this edit.
     */
    public boolean addEdit(MyUndoableEdit e, String method) {
	if (method.equals("undo")) {
	    return false;
	} else {
	    String changedText = e.getText();
	    if (e.getLength() > 1)
		return false;
	    else if (!getType().equals(e.getType()))
		return false;
	    else if (getLength() >= 5
		     && (changedText.equals(" ") || changedText.equals("\n") || changedText
			 .equals("\t"))) {
		return false;
	    } else {
		edits.add(e);
		setLength(getLength() + e.getLength());
		return true;
	    }
	}
    }

    /* Getters and Setters */
    public List<MyUndoableEdit> getEdits() {
	return edits;
    }

    public void setEdits(List<MyUndoableEdit> edits) {
	this.edits = edits;
    }

    public CompoundEdit getParent() {
	return parent;
    }

    public void setParent(CompoundEdit parent) {
	this.parent = parent;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public int getLength() {
	return length;
    }

    public void setLength(int length) {
	this.length = length;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public boolean getVisible() {
	return (mask == null);
    }

    public Mask getMask() {
	return mask;
    }

    public void setMask(Mask mask) {
	this.mask = mask;
    }

    public List<Mask> getMasks()
    {
	return masks;
    }

    public void addMask(Mask mask)
    {
	masks.add(mask);
    }

    public Mask getLastMask()
    {
	return masks.get(masks.size() - 1);
    }

    public void removeLastMask()
    {
	masks.remove(masks.size() - 1);
    }

    public boolean getIsMask()
    {
	return (!masks.isEmpty());
    }
    
    public boolean getCanExpand()
    {
	return getIsMask() && (!getIsRevert() || masks.size() > 1);
    }

    public boolean getIsRevert()
    {
	return isRevert;
    }

    public void setIsRevert(boolean isRevert)
    {
	this.isRevert = isRevert;
    }
}
