package org.bruno.edithistory;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class is the action the user triggers with command z to undo.
 */
public class UndoAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private UndoController undoController;

	public UndoAction(UndoController undoController) {
		super("Undo");
		this.undoController = undoController;
		setEnabled(false);
		updateUndoState();
	}

	public void actionPerformed(ActionEvent e) {
		if (undoController.canUndo()) {
			undoController.undo();
		}
	}

	protected void updateUndoState() {
		if (undoController.canUndo()) {
			setEnabled(true);
			putValue(Action.NAME, undoController.getUndoPresentationName());
		} else {
			setEnabled(false);
			putValue(Action.NAME, "Undo");
		}
	}
}
