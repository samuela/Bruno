package foobar;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class FoobarTest extends JFrame {
	private static final long serialVersionUID = 1L;

	private Foobar foobar;

	/**
	 * Create a new JFrame to test the Foobar component
	 */
	public FoobarTest() {
		super("Foobar Test");

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Prevent resizing
		this.setResizable(false);

		// Add a new Foobar to the frame
		foobar = new Foobar();
		this.add(foobar);

		this.pack();
	}

	public Foobar getFoobar() {
		return foobar;
	}

	/**
	 * Create a new FoobarTest
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FoobarTest().setVisible(true);
			}
		});
	}
}
