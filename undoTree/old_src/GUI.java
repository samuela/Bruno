package bruno.undoTree;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class GUI
{

    public void createAndShow()
    {
	JFrame frame = new JFrame("Bruno");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JTextArea textArea = new JTextArea(6, 40);
	textArea.setEditable(true);
	textArea.getDocument().addDocumentListener(new UndoTree());

	frame.getContentPane().add(textArea, BorderLayout.NORTH);
	frame.pack();
	frame.setVisible(true);
    }




}