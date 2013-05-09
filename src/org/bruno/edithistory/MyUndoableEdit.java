package org.bruno.edithistory;

import java.io.Serializable;
import javax.swing.text.BadLocationException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.undo.UndoableEdit;

/**
 * @author mscheer
 * This class is a wrapper around UndoableEdit that provides more flexibility
 * in that it isn't fixed to a particular Document.
 */
public class MyUndoableEdit implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int offset;//offset where this edit occured in the document
    private String type;//addition or deletion
    private int length;//length of change
    private String text;//text added or deleted

    /**
     * @param e an UndoableEdit that just occurred.
     * Creates a MyUndoableEdit representing the change made by e
     */
    public MyUndoableEdit(UndoableEdit e)
    {
	AbstractDocument.DefaultDocumentEvent event = (AbstractDocument.DefaultDocumentEvent) e;
	setOffset(event.getOffset());
	setType(event.getPresentationName());
	setLength(event.getLength());
	Document document = event.getDocument();
	try{
	    if (getType().equals("addition")){
		setText(document.getText(getOffset(), getLength()));
	    }
	    else if (getType().equals("deletion")){
		e.undo();
		setText(document.getText(getOffset(), getLength()));
		e.redo();
	    }
	}
	catch(BadLocationException ex){
	    ex.printStackTrace();//This should never happen
	}
    }

    /* Methods */
    public void undo(Document document)
    {
	try{
	    if (getType().equals("addition")){
		document.remove(getOffset(), getLength());
	    }
	    else if (getType().equals("deletion")){
		document.insertString(getOffset(), getText(), null);
	    }
	}
	catch(BadLocationException ex){
	    System.out.println(toString());
	    ex.printStackTrace();
	}
    }
    
    /* Getters and Setters */
    public int getOffset()
    {
	return offset;
    }

    public void setOffset(int offset)
    {
	this.offset = offset;
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

    public String getText()
    {
	return text;
    }

    public void setText(String text)
    {
	this.text = text;
    }

    public String toString()
    {
	String ans = "";
	ans += "text: "+getText();
	ans += " offset: "+getOffset();
	ans += " type: "+getType();
	return ans;
    }

}