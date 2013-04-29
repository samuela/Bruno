package foobar;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.util.Collections;

import javax.swing.JTextField;

public final class FoobarField extends JTextField {
	private static final long serialVersionUID = 1L;

	protected FoobarField() {
		super();
		
		// Listen for key presses
		this.addKeyListener(new FoobarKeyListener());

		// Prevent default tab behavior
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
				Collections.<AWTKeyStroke> emptySet());

		// Default size
		this.setPreferredSize(new Dimension(220, 30));
		
		this.setVisible(true);
	}
}
