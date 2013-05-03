package edithistory;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import javax.swing.text.Document;
import java.awt.event.*;

//import com.google.common.collect.Lists;

public class CompoundNodeComponent extends NodeComponent
{
    private static final long serialVersionUID = 1L;
    private List<NodeComponent> nodes;

    public CompoundNodeComponent(UndoController undoController)
    {
	super(undoController);
	setColor();
	nodes = new ArrayList<>();
    }

    public CompoundNodeComponent(UndoController undoController, List<NodeComponent> nodes)
    {
	this(undoController);
	this.nodes = nodes;
    }

    @Override
	public void setColor()
    {
	setBackground(new Color(0, 0, brightNess));
    }

    @Override
	public void setComment(String comment)
    {
	nodes.get(0).setComment(comment);
    }

    @Override
	public String getComment()
    {
	return nodes.get(0).getComment();
    }

    @Override
	public Document getDocument()
    {
	return nodes.get(0).getDocument();
    }

    @Override
	public void mouseClicked(MouseEvent e)
    {
	super.mouseClicked(e);
	int modifiers = e.getModifiers();
	if (modifiers == 19 || modifiers == 5){
	    getUndoController().expandCompoundNode(this);
	}
    }

    public List<NodeComponent> getHiddenNodes()
    {
	return nodes;
    }

    @Override
	public Edit getLastEdit()
    {
	return nodes.get(0).getLastEdit();
    }

    /*    @Override
	public NodeComponent makeOppositeComponent(EditHistoryView view)
    {
	NodeComponent first = null;
	NodeComponent last = null;
	for (int i=0; i<nodes.size(); i++){
	    NodeComponent n = nodes.get(i);
	    if (i==0){
		first = n.makeOppositeComponent(view);
		last = first;
	    }
	    else
		last = n.makeOppositeComponent(view);
	}
	return view.addCompoundNode(first, last, "");
	}*/
    
}