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
    private CompoundEdit lastEdit;
    private CompoundEdit toUndo;
    private UndoAction undoAction;
    private JTextArea textArea;
    private EditHistoryView view;

    public UndoController(JTextArea textArea)
    {
	this.textArea = textArea;
	undoAction = new UndoAction(this);
	lastEdit = new CompoundEdit();
	toUndo = lastEdit;
	view = new EditHistoryView(this);
	view.addEdit(lastEdit);
    }

    @Override
	public void undoableEditHappened(UndoableEditEvent e)
    {
	addEdit(new MyUndoableEdit(e.getEdit()));
	undoAction.updateUndoState();
    }

    public void addEdit(MyUndoableEdit e)
    {
	if (!lastEdit.addEdit(e))
	    createNewEdit(e);
	toUndo = lastEdit;
    }

    public void createNewEdit(MyUndoableEdit e)
    {
	lastEdit = new CompoundEdit(e, lastEdit);
	view.addEdit(lastEdit);
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
	CompoundEdit e = lastEdit;
	while (e != edit){
	    e.undo(restoredDocument);
	    e = e.getParent();
	}
	return restoredDocument;
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