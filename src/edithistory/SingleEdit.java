package edithistory;

import java.io.Serializable;
import javax.swing.text.Document;

public class SingleEdit implements Edit, Serializable
{
    private static final long serialVersionUID = 1L;
    private MyUndoableEdit edit;

    public SingleEdit(){}

    public SingleEdit(MyUndoableEdit e)
    {
	setEdit(e);
    }

    @Override
	public void undo(Document document)
    {
	edit.undo(document);
    }

    /* Getters and Setters */
    public MyUndoableEdit getEdit()
    {
	return edit;
    }

    public void setEdit(MyUndoableEdit edit)
    {
	this.edit = edit;
    }
    
    @Override
	public String getType()
    {
	return edit.getType();
    }

    @Override
	public int getLength()
    {
	return edit.getLength();
    }
 
    @Override
	public boolean isCompound()
    {
	return false;
    }

    public String getText()
    {
	return edit.getText();
    }
   
}