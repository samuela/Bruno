package undotree;

import java.util.List;

import javax.swing.UIManager;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import com.google.common.collect.Lists;

public class UndoTree // this could implement UndoableEdit or extend
						// AbstractUndoableEdit or CompoundEdit or UndoManager.
// For the moment however, it doesn't need to. It probably should though
// eventually.
{
	private Node<UndoableEdit> currentNode;
	private Node<UndoableEdit> toRedo;

	public UndoTree() {
		currentNode = new Node<UndoableEdit>();
	}

	public boolean addEdit(UndoableEdit e) {
		Node<UndoableEdit> newNode = new Node<UndoableEdit>(e, currentNode);
		currentNode.addChild(newNode);
		currentNode = newNode;
		return true;
	}

	public boolean canUndo() {
		return (!currentNode.isEmpty() && currentNode.getData().canUndo());
	}

	public void undo() throws CannotUndoException {
		if (!canUndo())
			throw new CannotUndoException();
		Node<UndoableEdit> parentNode = currentNode.getParent();
		currentNode.getData().undo();
		currentNode = parentNode;
	}

	public boolean canRedo() {
		List<Node<UndoableEdit>> edits = currentNode.getChildren();
		if (edits.isEmpty())
			return false;
		boolean answer = false;
		for (Node<UndoableEdit> node : Lists.reverse(edits)) {
			UndoableEdit edit = node.getData();
			if (edit.canRedo()) {
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
		toRedo.getData().redo();
		currentNode = toRedo;
	}

	public String getUndoPresentationName() {
		if (canUndo()) {
			return currentNode.getData().getUndoPresentationName();
		} else {
			return UIManager.getString("AbstractUndoableEdit.undoText");
		}
	}

	public String getRedoPresentationName() {
		if (canRedo()) {
			return toRedo.getData().getUndoPresentationName();
		} else {
			return UIManager.getString("AbstractUndoableEdit.redoText");
		}
	}

}