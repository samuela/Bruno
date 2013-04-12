package bruno.undoTree;

import java.util.*;
import javax.swing.undo.UndoableEdit;

public class Node<T>
{

    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    /* Constructors */
    public Node()
	{
	    children = new ArrayList<>();
	}
    
    public Node(T data, Node<T> parent)
	{
	    this.data = data;
	    this.parent = parent;
	    children = new ArrayList<>();
	}
    
    /* Other */
    public boolean isEmpty()
    {
	return (data == null);
    }

    /* Getters and Setters */
    public T getData()
    {
	return data;
    }
    
    public void setData(T data)
    {
	this.data = data;
    }

    public Node<T> getParent()
    {
	return parent;
    }

    public void setParent(Node<T> parent)
    {
	this.parent = parent;
    }

    public List<Node<T>> getChildren()
    {
	return children;
    }

    public void setChildren(List<Node<T>> children)
    {
	this.children = children;
    }

    public void addChild(Node<T> child)
    {
	children.add(child);
    }

}