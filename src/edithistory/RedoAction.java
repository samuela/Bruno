package edithistory;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class RedoAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private UndoController undoController;

	public RedoAction(UndoController undoController) {
		super("Redo");
		this.undoController = undoController;
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (undoController.canRedo()) {
			undoController.redo();
		}
	}

	protected void updateRedoState() {
		if (undoController.canRedo()) {
			setEnabled(true);
			putValue(Action.NAME, undoController.getRedoPresentationName());
		} else {
			setEnabled(false);
			putValue(Action.NAME, "Redo");
		}
	}
}