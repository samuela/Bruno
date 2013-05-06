package edithistory;

import javax.swing.UIManager;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class UndoController implements UndoableEditListener
{
    private CompoundEdit lastUndoEdit;
    private CompoundEdit toUndo;
    private CompoundEdit lastDisplayEdit;
    private UndoAction undoAction;
    private JTextArea textArea;
    private EditHistoryView view;

    public UndoController(JTextArea textArea)
    {
	this.textArea = textArea;
	undoAction = new UndoAction(this);
	lastUndoEdit = new CompoundEdit();
	lastDisplayEdit = new CompoundEdit();
	toUndo = lastUndoEdit;
	view = new EditHistoryView(this);
	view.addEdit(lastDisplayEdit);
    }

    @Override
	public void undoableEditHappened(UndoableEditEvent e)
    {
	addEdit(new SingleEdit(new MyUndoableEdit(e.getEdit())));
	undoAction.updateUndoState();
    }

    public void addEdit(Edit e)
    {
	if (!lastUndoEdit.addEdit(e, "undo")){
	    lastUndoEdit = new CompoundEdit(e, lastUndoEdit);
	}
	if (!lastDisplayEdit.addEdit(e, "display")){
	    lastDisplayEdit = new CompoundEdit(e, lastDisplayEdit);
	    view.addEdit(lastDisplayEdit);
	}
	toUndo = lastUndoEdit;
    }

    public boolean canUndo()
    {
	return (!toUndo.getType().equals("empty"));
    }

    public void undo()
    {
	CompoundEdit currentToUndo = toUndo;
	toUndo.undo(textArea.getDocument());
	toUndo = currentToUndo.getParent();
    }

    public Document restoreTo(CompoundEdit edit)
    {
	Document document = textArea.getDocument();
	RSyntaxDocument restoredDocument = new RSyntaxDocument(SyntaxConstants.SYNTAX_STYLE_JAVA);
	try {
	    restoredDocument.insertString(0,document.getText(0, document.getLength()), null);
	} 
	catch (BadLocationException e1) {
	}
	CompoundEdit e = lastDisplayEdit;
	while (e != edit){
	    e.undo(restoredDocument);
	    e = e.getParent();
	}
	return restoredDocument;
    }

    public void revert(CompoundEdit edit)
    {
	Document restored = restoreTo(edit);
	try{
	    textArea.replaceRange(restored.getText(0, restored.getLength()), 0, textArea.getDocument().getLength());
	}
	catch(BadLocationException ex){}
    }

    /* Getters and Setters */
    public UndoAction getUndoAction()
    {
	return undoAction;
    }

    public String getUndoPresentationName()
    {
	return UIManager.getString("AbstractUndoaleEdit.undoText");
    }

    public EditHistoryView getView()
    {
	return view;
    }

}