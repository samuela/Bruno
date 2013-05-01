package edithistory;

import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.undo.UndoableEdit;
import java.util.Date;
import javax.swing.text.BadLocationException;

public class Edit
{
    private Edit parent;
    private Edit child;
    private int size;
    private String type;
    private int order;
    private Date date;
    private Edit affectedEdit;
    private String comment;

    /* Constructors */
    public Edit()
    {
	setSize(0);
	setType("empty");
	setOrder(0);
	setDate(new Date());
	setAffectedEdit(this);
	setComment("");
    }

    public Edit(Edit parent)
    {
	this();
	parent.setChild(this);
	setParent(parent);
	setOrder(parent.getOrder() + 1);
    }

    /* Methods */
    public boolean canUndo()
    {
	if (getParent() != null)
	    return getParent().canUndo();
	else
	    return false;
    }

    public boolean canRedo()
    {
	if (getParent() != null)
	    return getParent().canRedo();
	else
	    return false;
    }
    
    public boolean undone()
    {
	if (getParent() != null)
	    return getParent().undone();
	else
	    return false;
    }

    public void undo()
    {
	undo(this);
    }
    
    public void redo()
    {
	redo(this);
    }

    public void undo(Edit edit)
    {
	if (getParent() != null)
	    getParent().undo(edit);
    }

    public void redo(Edit edit)
    {
	if (getParent() != null)
	    getParent().redo(edit);
    }
    
    public boolean addEdit(UndoableEdit e)
    {
	return false;
    }

    public void backInTime(){}

    public void forwardInTime(){}

    /* Getters and Setters */
    public Edit getParent()
    {
	return parent;
    }

    public void setParent(Edit parent)
    {
	this.parent = parent;
    }

    public Edit getChild()
    {
	return child;
    }

    public void setChild(Edit child)
    {
	this.child = child;
    }

    public int getSize()
    {
	return size;
    }

    public void setSize(int size)
    {
	this.size = size;
    }

    public String getType()
    {
	return type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public int getOrder()
    {
	return order;
    }

    public void setOrder(int order)
    {
	this.order = order;
    }

    public Date getDate()
    {
	return date;
    }

    public void setDate(Date date)
    {
	this.date = date;
    }

    public Edit getAffectedEdit()
    {
	return affectedEdit;
    }

    public void setAffectedEdit(Edit edit)
    {
	affectedEdit = edit;
    }

    public String getComment()
    {
	return comment;
    }

    public void setComment(String comment)
    {
	this.comment = comment;
    }

    public String getUndoPresentationName()
    {
	return UIManager.getString("AbstractUndoaleEdit.undoText");
    }

    public String getRedoPresentationName()
    {
	return UIManager.getString("AbstractUndoableEdit.redoText");
    }

    /* Utility Methods */
    public static int editSize(UndoableEdit e)
    {
	AbstractDocument.DefaultDocumentEvent event = (AbstractDocument.DefaultDocumentEvent) e;
	return event.getLength();
    }

    public static String oppositeType(String type)
    {
	if (type.equals("addition"))
	    return "deletion";
	else if (type.equals("deletion"))
	    return "addition";
	else
	    return "";
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
    
}