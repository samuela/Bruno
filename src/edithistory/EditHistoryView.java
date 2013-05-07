package edithistory;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import java.awt.LayoutManager;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.awt.Component;

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
    private NodeComponent selectedNode;
    private JTextArea comment;

    public EditHistoryView(UndoController uc) {
	this.undoController = uc;
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

	JSplitPane rightBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
						comment, sp);

	JPanel rightSide = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.HORIZONTAL;
	JButton revert = new JButton("Revert");
	c.weightx = 0.5;
	c.gridx = 0;
	c.gridy = 0;
	rightSide.add(revert, c);
	revert.addActionListener(new ActionListener() {
		@Override
		    public void actionPerformed(ActionEvent e) {
		    if (selectedNode != null) {
			undoController.revert(selectedNode.getEdit());
		    }
		}
	    });

	JButton expandNode = new JButton("Expand");
	c.gridx = 1;
	rightSide.add(expandNode, c);
	expandNode.addActionListener(new ActionListener(){
		@Override
		    public void actionPerformed(ActionEvent e){
		    if (selectedNode != null){
			expand(selectedNode);
		    }
		}
	    });

	c.anchor = GridBagConstraints.PAGE_END;
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 0.0;
	c.weighty = 1.0;
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 1;
	rightSide.add(rightBottom, c);

	// increase scroll speed!
	JScrollPane nodesViewScrollPane = new JScrollPane(nodesView);
	nodesViewScrollPane.getVerticalScrollBar().setUnitIncrement(16);

	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				   nodesViewScrollPane, rightSide);
	add(splitPane);
	splitPane.setDividerLocation(70);
	rightBottom.setDividerLocation(20);
    }

    private class MyDocumentListener implements DocumentListener {
	@Override
	    public void changedUpdate(DocumentEvent e) {
	}
	
	@Override
	    public void insertUpdate(DocumentEvent e) {
	    update();
	}
	
	@Override
	    public void removeUpdate(DocumentEvent e) {
	    update();
	}
	
	public void update() {
	    if (selectedNode != null) {
		selectedNode.setComment(comment.getText());
	    }
	}
    }

    public void revalidateNodeComponents() {
	splitPane.setDividerLocation(70);
	splitPane.setDividerLocation(splitPane.getLastDividerLocation());
    }

    public void addEdit(CompoundEdit edit)
    {
	NodeComponent newNode = new NodeComponent(edit, undoController);
	nodesView.add(newNode);
	edit.setVisible(true);
	revalidateNodeComponents();
    }

    public void addEditAtBeginning(CompoundEdit edit)
    {
	NodeComponent newNode = new NodeComponent(edit, undoController);
	nodesView.add(newNode, 0);
	edit.setVisible(true);
	revalidateNodeComponents();
    }

    public void compress(NodeComponent n1, NodeComponent n2)
    {
	Component[] nodeComponents = nodesView.getComponents();
	int index1 = -1;
	int index2 = -1;
	for (int i = 0; i < nodeComponents.length; i++) {
	    if (nodeComponents[i] == n1) {
		index1 = i;
	    }
	    if (nodeComponents[i] == n2) {
		index2 = i;
	    }
	    if (index1 >= 0 && index2 >= 0)
		break;
	}
	int lower = (index1 <= index2) ? index1 : index2;
	int higher = (index1 <= index2) ? index2 : index1;
	CompoundEdit mask = ((NodeComponent) nodeComponents[higher]).getEdit();
	mask.setIsMask(true);
	((NodeComponent) nodeComponents[higher]).setColor();
	for (int i = higher; i > lower; i--) {
	    CompoundEdit toRemove = ((NodeComponent) nodesView.getComponent(lower)).getEdit();
	    toRemove.setVisible(false);
	    toRemove.setMask(mask);
	    nodesView.remove(lower);
	}
	revalidateNodeComponents();
    }

    public void expand(NodeComponent node)
    {
	if (node.getEdit().getIsMask()){
	    Component[] nodeComponents = nodesView.getComponents();
	    int index = -1;
	    for (int i=0; i < nodeComponents.length; i++){
		if (nodeComponents[i] == node){
		    index = i;
		    break;
		}
	    }
	    CompoundEdit edit = node.getEdit().getParent();
	    while (edit != null && !edit.getVisible()){
		if (edit.getMask() == node.getEdit()){
		    nodesView.add(new NodeComponent(edit, undoController), index);
		    edit.setVisible(true);
		    edit.setMask(edit);
		}
		edit = edit.getParent();
	    }
	    node.getEdit().setIsMask(false);
	    node.setColor();
	    revalidateNodeComponents();
	}
    }

    /* Getters and Setters */
    public NodeComponent getSelectedNode()
    {
	return selectedNode;
    }

    public void setSelectedNode(NodeComponent selectedNode)
    {
	this.selectedNode = selectedNode;
	comment.setText(selectedNode.getComment());
	comment.setEditable(true);
    }
    
    public void setDocument(Document doc) {
	textArea.setDocument(doc);
    }

    public Component[] getNodeComponents()
    {
	return nodesView.getComponents();
    }
    
}

