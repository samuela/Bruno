package frontend;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import edithistory.UndoController;

/**
 * An editing view controller. Accepts a document and manages its editing and
 * undo views as well as saving and loading.
 * 
 * @author samuelainsworth
 * 
 */
public class EditingWindow {

	private final DocumentModel doc;
	private RSyntaxTextArea textArea;
	private RTextScrollPane scrollPane;
	private UndoController undoController;

	public EditingWindow(DocumentModel doc) throws FileNotFoundException {
		this.doc = doc;

		// Read contents of file
		StringBuilder contents = new StringBuilder();
		if (doc.getFile() != null) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(doc.getFile());
				while (scanner.hasNextLine()) {
					contents.append(scanner.nextLine()
							+ System.getProperty("line.separator"));
				}
			} finally {
				scanner.close();
			}
		}

		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		scrollPane = new RTextScrollPane(textArea);
		scrollPane.setFoldIndicatorEnabled(true);
		scrollPane.setLineNumbersEnabled(true);

		// Set text in text area
		textArea.setText(contents.toString());

		// Stay at the top of the document
		textArea.setCaretPosition(0);

		// Setup undo tree
		undoController = new UndoController(textArea.getDocument());
		textArea.getDocument().addUndoableEditListener(undoController);

		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()),
				undoController.getUndoAction());
		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()
						+ Event.SHIFT_MASK), undoController.getRedoAction());

	}

	/**
	 * Save the document.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		if (doc.getFile() == null) {
			// TODO check if the file is non-empty and ask to save
		} else {
			Writer writer = new OutputStreamWriter(new FileOutputStream(
					doc.getFile()));
			writer.write(textArea.getText());
			writer.close();
		}
	}

	public JComponent getView() {
		return scrollPane;
	}

	public UndoController getUndoController() {
		return undoController;
	}
}
