package edithistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import com.google.common.collect.Lists;

/**
 * This class represents several edits put into one.
 */
public class CompoundEdit implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<MyUndoableEdit> edits;
    private CompoundEdit parent;
    private String type;// addition, deletion, or empty
    private int length;
    private String comment;
    private boolean visible;
    private CompoundEdit mask;
    private boolean isMask;
    private boolean isRevert;

    public CompoundEdit() {
	edits = new ArrayList<>();
	setType("empty");
	setVisible(false);
	setMask(this);
	setIsMask(false);
	setIsRevert(false);
    }

    public CompoundEdit(MyUndoableEdit e, CompoundEdit parent) {
	this();
	edits.add(e);
	setParent(parent);
	setType(e.getType());
	setLength(e.getLength());
    }

    public void undo(Document document) {
	for (MyUndoableEdit e : Lists.reverse(edits)) {
	    e.undo(document);
	}
    }

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
	return visible;
    }

    public void setVisible(boolean visible) {
	this.visible = visible;
    }

    public CompoundEdit getMask() {
	return mask;
    }

    public void setMask(CompoundEdit mask) {
	this.mask = mask;
    }
    
    public boolean getIsMask()
    {
	return isMask;
    }

    public void setIsMask(boolean isMask)
    {
	this.isMask = isMask;
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