package edithistory;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import java.awt.LayoutManager;
import java.awt.CardLayout;
import java.awt.event.*;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class EditHistoryView extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JSplitPane splitPane;
    private RSyntaxTextArea textArea;
    private LayoutManager layout;
    private Box nodesView;
    private UndoController undoController;
    private Edit clickedEdit;
    private JTextArea comment;

    public EditHistoryView(UndoController undoController)
    {
	this.undoController = undoController;
	layout = new CardLayout();
	setLayout(layout);

	textArea = new RSyntaxTextArea();
	textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
	textArea.setCodeFoldingEnabled(true);
	textArea.setAntiAliasingEnabled(true);
	textArea.setEditable(false);
	RTextScrollPane sp = new RTextScrollPane(textArea);
	sp.setFoldIndicatorEnabled(true);
	sp.setLineNumbersEnabled(true);

	nodesView = new Box(BoxLayout.Y_AXIS);
	comment = new JTextArea(3, 15);
	comment.setEditable(false);
	comment.getDocument().addDocumentListener(new MyDocumentListener());

	JSplitPane rightSide = new JSplitPane(JSplitPane.VERTICAL_SPLIT, comment, sp);

	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(nodesView), rightSide);
	//left side should have the nodes
	//right side should have the text pane with a little box above it
	//where you can write a tag/comment/name
	add(splitPane);
	splitPane.setDividerLocation(70);
	rightSide.setDividerLocation(20);
    }

    private class MyDocumentListener implements DocumentListener
    {
	@Override
	    public void changedUpdate(DocumentEvent e){}
	@Override
	    public void insertUpdate(DocumentEvent e){update();}
	@Override
	    public void removeUpdate(DocumentEvent e){update();}
	public void update()
	{
	    if (clickedEdit != null){
		clickedEdit.setComment(comment.getText());
	    }
	}
    }

    public void addNode(Edit edit)
    {
	nodesView.add(new NodeComponent(edit, undoController));
	revalidate();
    }

    public void setDocument(Document doc)
    {
	textArea.setDocument(doc);
	textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void setClickedEdit(Edit e)
    {
	clickedEdit = e;
	comment.setText(e.getComment());
	comment.setEditable(true);
    }

    public Edit getClickedEdit()
    {
	return clickedEdit;
    }

}
