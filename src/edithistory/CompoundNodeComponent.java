package edithistory;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import javax.swing.text.Document;

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
	setBackground(Color.blue);
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

    //Can't do different behavior on right click. Maybe on shift right click? Something like that?

}