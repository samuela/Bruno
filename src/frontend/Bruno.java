package frontend;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.apple.eawt.Application;
import java.awt.Image;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import undotree.UndoController;

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
        setBar();
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

		// Setup undo tree
		UndoController undoController = new UndoController();
		textArea.getDocument().addUndoableEditListener(undoController);

		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()),
				undoController.getUndoAction());
		textArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()
						       + Event.SHIFT_MASK), undoController.getRedoAction());

		// Side pane
		tabPane = new JTabbedPane();
		tabPane.addTab("Projects", new ProjectExplorer(this));
		tabPane.addTab("Edit History", new JPanel());

		// Split Pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, tabPane);
		splitPane.setOneTouchExpandable(true);

		setContentPane(splitPane);
	}

    private void setBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem item = new JMenuItem("Woah");
        file.add(item);
        menuBar.add(file);
        setJMenuBar(menuBar);
    }

    private static void setMacStuff(){
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    private static void setNiceties() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        if(System.getProperty("os.name").equals("Mac OS X")){
            setMacStuff();
        }
        Application application = Application.getApplication();
        Image image = Toolkit.getDefaultToolkit().getImage("resources/*.jpg");
        application.setDockIconImage(image);
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


	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            setNiceties();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
