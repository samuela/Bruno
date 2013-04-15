package frontend;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import undotree.UndoTree;

public class Bruno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987233037023049749L;

	private final JTabbedPane tabPane;
	private final JSplitPane splitPane;
	private final RSyntaxTextArea textArea;

	private final UndoTree undo;
	private final UndoAction undoAction;
	private final RedoAction redoAction;

	public Bruno() {
		setTitle("Bruno");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Key bindings
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()), "open");
		getRootPane().getActionMap().put("open", new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4189934329254672244L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(getRootPane());
			}

		});

		// Text area
		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);
		sp.setLineNumbersEnabled(true);

		// Setup editor tree
		undo = new UndoTree();

		undoAction = new UndoAction();
		redoAction = new RedoAction();

		Document doc = textArea.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {

			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				// Remember the edit and update the menus.
				undo.addEdit(e.getEdit());
				undoAction.updateUndoState();
				redoAction.updateRedoState();
			}

		});

		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()),
				undoAction);
		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()
						+ Event.SHIFT_MASK), redoAction);

		// Side pane
		tabPane = new JTabbedPane();
		tabPane.addTab("Projects", new ProjectExplorer(this));
		tabPane.addTab("Edit History", new JPanel());

		// Split Pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, tabPane);
		splitPane.setOneTouchExpandable(true);

		setContentPane(splitPane);
	}

	public void openFile(File file) {
		StringBuilder contents = new StringBuilder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				contents.append(scanner.nextLine()
						+ System.getProperty("line.separator"));
			}
			textArea.setText(contents.toString());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Failed to open file " + file,
					"File opening error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	public void close() {
		setVisible(false);
		dispose();
		System.exit(0);
	}

	class RedoAction extends AbstractAction {

		public static final long serialVersionUID = 1L;

		public RedoAction() {
			super("Redo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.redo();
			} catch (CannotRedoException ex) {
				System.out.println("Unable to redo: " + ex);
				ex.printStackTrace();
			}
			updateRedoState();
			undoAction.updateUndoState();
		}

		protected void updateRedoState() {
			if (undo.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Redo");
			}
		}
	}

	class UndoAction extends AbstractAction {

		public static final long serialVersionUID = 1L;

		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
			}
			updateUndoState();
			redoAction.updateRedoState();
		}

		protected void updateUndoState() {
			if (undo.canUndo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Undo");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Bruno b = new Bruno();
				b.setVisible(true);

				// Can't set divider location until after frame has been packed
				// or set visible. Fuck swing.
				b.splitPane.setDividerLocation(0.7);
			}

		});
	}
}
