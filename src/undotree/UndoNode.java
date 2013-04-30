package undotree;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
    private int size;
    //    private List<Date> timeStamps;
    private String type;

    /* Constructors */
    public UndoNode() {
	edits = new ArrayList<>();
	type = "";
	children = new ArrayList<>();
	size = 0;
	//	timeStamps = new ArrayList<>();
	//	addTimeStamp();
    }

    public UndoNode(UndoableEdit edit, UndoNode parent) {
	edits = new ArrayList<>();
	type = edit.getPresentationName();
	addEdit(edit);
	this.parent = parent;
	children = new ArrayList<>();
	//	timeStamps = new ArrayList<>();
	//	addTimeStamp();
    }

    /* Methods */
    public boolean isEmpty() {
	return (edits.isEmpty());
    }

    public void addEdit(UndoableEdit e)
    {
	edits.add(e);
	size += editSize(e);
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
	    String type = e.getPresentationName();
	    if (type.equals("addition")){
		text = event.getDocument().getText(event.getOffset(), event.getLength());
	    }
	}
	catch(BadLocationException ex){
	    System.out.println(e);
	    ex.printStackTrace();
	    //This should never happen
	}
	return text;
	}

    public static int editSize(UndoableEdit e)
    {
	AbstractDocument.DefaultDocumentEvent event = (AbstractDocument.DefaultDocumentEvent) e;
	return event.getLength();
    }

    public List<UndoNode> pathToNode(UndoNode otherNode)
    {
	return pathToNodeHelper(otherNode, children);
    }
    
    public List<UndoNode> pathToNodeHelper(UndoNode otherNode, List<UndoNode> childNodes)
    {
	List<UndoNode> answer = pathDownwardToNode(otherNode, childNodes);
	if (answer != null)
	    return answer;
	else{
	    List<UndoNode> newChildNodes = parent.getChildren();
	    newChildNodes.remove(this);
	    answer = parent.pathToNodeHelper(otherNode, newChildNodes);
	    answer.add(this);
	    return answer;
	}
    }
    
    public List<UndoNode> pathDownwardToNode(UndoNode otherNode, List<UndoNode> childNodes)
    {
	List<UndoNode> answer;
	if (this == otherNode){
	    answer = new ArrayList<>();
	    answer.add(this);
	    return answer;
	}
	else if (childNodes.isEmpty()){
	    return null;
	}
	else{
	    for (UndoNode child : childNodes){
		answer = child.pathDownwardToNode(otherNode, child.getChildren());
		if (answer != null){
		    answer.add(this);
		    return answer;
		}
	    }
	}
	return null;
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
	return size;
    }

    /*    public void addTimeStamp()
    {
	timeStamps.add(new Date());
	}*/

    /*    public List<Date> getTimeStamps()
    {
	return timeStamps;
	}*/

    public String getType()
    {
	return type;
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