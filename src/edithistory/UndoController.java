package edithistory;

import java.io.Serializable;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoableEdit;

public class UndoController implements UndoableEditListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3590651720208476996L;

	private Edit lastEdit;
	private UndoAction undoAction;
	private RedoAction redoAction;
	private EditHistoryView view;
	private Document document;
	private NodeComponent toCompound1;
	private NodeComponent toCompound2;

	public UndoController(Document document) {
		lastEdit = new Edit();
		undoAction = new UndoAction(this);
		redoAction = new RedoAction(this);
		view = new EditHistoryView(this);
		this.document = document;
		view.addNode(lastEdit);
	}

	// For deserialization purposes
	public UndoController(Edit lastEdit) {
		this.lastEdit = lastEdit;
		this.document = lastEdit.getDocument();
		view = new EditHistoryView(this);

		// add edits
		Edit edit = lastEdit;
		while (edit.getParent() != null) {
			edit = edit.getParent();
		}
		while (edit != null) {
			view.addNode(edit);
			edit = edit.getChild();
		}

		// add compressions

		undoAction = new UndoAction(this);
		redoAction = new RedoAction(this);
		undoAction.updateUndoState();
		redoAction.updateRedoState();
	}

	public void undo() {
		lastEdit = new UndoEdit(lastEdit);
		view.addNode(lastEdit);
		undoAction.updateUndoState();
		redoAction.updateRedoState();
	}

	public void redo() {
		lastEdit = new RedoEdit(lastEdit);
		view.addNode(lastEdit);
		undoAction.updateUndoState();
		redoAction.updateRedoState();
	}

	public boolean canUndo() {
		return lastEdit.canUndo();
	}

	public boolean canRedo() {
		return lastEdit.canRedo();
	}

	public void addEdit(UndoableEdit e) {
		if (lastEdit.addEdit(e))
			return;
		else {
			createNewEdit(e);
		}
	}

	public void createNewEdit(UndoableEdit e) {
		lastEdit = new TextEdit(e, lastEdit);
		view.addNode(lastEdit);
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		addEdit(e.getEdit());
		undoAction.updateUndoState();
		redoAction.updateRedoState();
	}

	public void backInTime(Edit e) {
		Edit timeFrame = lastEdit;
		while (timeFrame != e) {
			timeFrame.backInTime();
			timeFrame = timeFrame.getParent();
		}
	}

	public void forwardInTime(Edit e) {
		Edit timeFrame = e.getChild();
		while (timeFrame != null) {
			timeFrame.forwardInTime();
			timeFrame = timeFrame.getChild();
		}
	}

	public Document getDocument() {
		return document;
	}

	public EditHistoryView getView() {
		return view;
	}

	public void selectNodeForCompound(NodeComponent node) {
		if (toCompound1 == null)
			toCompound1 = node;
		else if (toCompound2 == null) {
			toCompound2 = node;
			view.addCompoundNode(toCompound1, toCompound2, "");
			toCompound1.deselectForCompound();
			toCompound2.deselectForCompound();
			toCompound1 = null;
			toCompound2 = null;
		}
	}

	public void deselectNodeForCompound(NodeComponent node) {
		if (toCompound1 == node) {
			toCompound1 = null;
		}
	}

	public void expandCompoundNode(CompoundNodeComponent node) {
		view.expandCompoundNode(node);
	}

	/* Utilities */
	public void updateUndoState() {
		undoAction.updateUndoState();
	}

	public void updateRedoState() {
		redoAction.updateRedoState();
	}

	public String getUndoPresentationName() {
		return lastEdit.getUndoPresentationName();
	}

	public String getRedoPresentationName() {
		return lastEdit.getRedoPresentationName();
	}

	public UndoAction getUndoAction() {
		return undoAction;
	}

	public RedoAction getRedoAction() {
		return redoAction;
	}

}
