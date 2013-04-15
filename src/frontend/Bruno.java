package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Bruno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987233037023049749L;

	private final JTabbedPane tabPane;
	private final JSplitPane splitPane;
	private final RSyntaxTextArea textArea;

	public Bruno() {
		setTitle("Bruno");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Key bindings
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_O,
						InputEvent.META_DOWN_MASK), "openFile");
		getRootPane().getActionMap().put("openFile", new AbstractAction() {

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
		textArea = new RSyntaxTextArea(20, 60);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);

		// Side pane
		tabPane = new JTabbedPane();
		tabPane.addTab("Projects", new ProjectExplorer(this));
		tabPane.addTab("Edit History", new JPanel());

		// Split Pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textArea,
				tabPane);
		splitPane.setOneTouchExpandable(true);

		setContentPane(splitPane);
	}

	public void close() {
		setVisible(false);
		dispose();
		System.exit(0);
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
