package undotree;

import java.util.List;

import javax.swing.UIManager;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;

import com.google.common.collect.Lists;

public class UndoTree {
    private UndoNode currentNode;
    private UndoNode toRedo;

    public UndoTree() {
	currentNode = new UndoNode();
    }
    
    public void addNode(UndoableEdit e)
    {
	UndoNode newNode = new UndoNode(e, currentNode);
	currentNode.addChild(newNode);
	currentNode = newNode;
    }

    public boolean addEdit(UndoableEdit e)
    {
	if (currentNode.isEmpty() || !currentNode.getChildren().isEmpty()){
	    addNode(e);
	}
	else{
	    // String changedText = UndoNode.changedText(e);
	    int size = UndoNode.editSize(e);
	    if (size > 1){
		addNode(e);
	    }
	    else if (currentNode.getEditSize() >= 5)/* && (changedText.equals(" ") ||
							changedText.equals("\n") ||
							changedText.equals("\t")))*/{
		addNode(e);
	    }
	    else{
		currentNode.addEdit(e);
	    }
	}
	return true;
    }
	
    public boolean canUndo()
    {
	return currentNode.canUndo();
    }

    public void undo() throws CannotUndoException {
	if (!canUndo())
	    throw new CannotUndoException();
	UndoNode parentNode = currentNode.getParent();
	currentNode.undo();
	currentNode = parentNode;
    }
    
    public boolean canRedo() {
	List<UndoNode> children = currentNode.getChildren();
	if (children.isEmpty())
	    return false;
	boolean answer = false;
	for (UndoNode node : Lists.reverse(children)) {
	    if (node.canRedo()) {
		answer = true;
		toRedo = node;
		break;
	    }
	}
	return answer;
    }

    public void redo() throws CannotRedoException {
	if (!canRedo())
	    throw new CannotRedoException();
	toRedo.redo();
	currentNode = toRedo;
    }

    public String getUndoPresentationName() {
	return currentNode.getUndoPresentationName();
    }

    public String getRedoPresentationName() {
	return currentNode.getRedoPresentationName();
    }

}