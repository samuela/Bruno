package undotree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.CompoundEdit;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;

import com.google.common.collect.Lists;

public class UndoNode {

    private List<UndoableEdit> edits;
    private UndoNode parent;
    private List<UndoNode> children;
    private int editSize;

    /* Constructors */
    public UndoNode() {
	edits = new ArrayList<>();
	children = new ArrayList<>();
	editSize = 0;
    }

    public UndoNode(UndoableEdit edit, UndoNode parent) {
	edits = new ArrayList<>();
	edits.add(edit);
	this.parent = parent;
	children = new ArrayList<>();
	editSize = changedText(edit).length();
    }

    /* Methods */
    public boolean isEmpty() {
	return (edits.isEmpty());
    }

    public void addEdit(UndoableEdit e)
    {
	edits.add(e);
	editSize += changedText(e).length();
    }

    public boolean canUndo()
    {
	if (edits.isEmpty())
	    return false;
	boolean ans = true;
	for (UndoableEdit e : edits){
	    if (!e.canUndo()){
		ans = false;
		break;
	    }
	}
	return ans;
    }

    public void undo()
    {
	for (UndoableEdit e : Lists.reverse(edits)){
	    e.undo();
	}
    }

    public boolean canRedo()
    {
	if (edits.isEmpty())
	    return false;
	boolean ans = true;
	for (UndoableEdit e : edits){
	    if (!e.canRedo()){
		ans = false;
		break;
	    }
	}
	return ans;
    }

    public void redo()
    {
	for (UndoableEdit e : edits){
	    e.redo();
	}
    }

    public static String changedText(UndoableEdit e)
    {
	AbstractDocument.DefaultDocumentEvent event = (AbstractDocument.DefaultDocumentEvent) e;
	String text = "";
	try{
	    text = event.getDocument().getText(event.getOffset(), event.getLength());
	}
	catch(BadLocationException ex){
	    ex.printStackTrace();
	    //This should never happen
	}
	return text;
    }

    /* Getters and Setters */
    public UndoNode getParent() {
	return parent;
    }

    public void setParent(UndoNode parent) {
	this.parent = parent;
    }

    public List<UndoNode> getChildren() {
	return children;
    }

    public void setChildren(List<UndoNode> children) {
	this.children = children;
    }

    public void addChild(UndoNode child) {
	children.add(child);
    }

    public int getEditSize(){
	return editSize;
    }

    public String getUndoPresentationName()
    {
	if (!(edits.isEmpty())){
	    return edits.get(0).getUndoPresentationName();
	}
	else
	    return UIManager.getString("AbstractUndoableEdit.undoText");
    }

    public String getRedoPresentationName()
    {
	if (!(edits.isEmpty())){
	    return edits.get(0).getRedoPresentationName();
	}
	else
	    return UIManager.getString("AbstractUndoableEdit.redoText");
    }



}