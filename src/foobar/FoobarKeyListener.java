package foobar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public final class FoobarKeyListener implements KeyListener {
	@Override
	public void keyReleased(KeyEvent e) {
		// Get the source text field
		JTextField field = (JTextField) e.getSource();

		// Get the parent Foobar
		Foobar foobar = (Foobar) field.getParent();

		// Get the value of the source text field
		String value = field.getText();

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			// Execute the selected Fooable when 'enter' is released
			foobar.executeFooable();

		} else if (e.getKeyCode() == KeyEvent.VK_TAB) {

			// Autocomplete the input when 'tab' is released
			String completed = foobar.getCompletion(value);
			field.setText(completed);
			foobar.displaySuggestions(completed);

		} else if (e.getKeyCode() == KeyEvent.VK_UP) {

			// Select the previous indexed Fooable when 'up' is released
			foobar.selectUp();
			field.setCaretPosition(value.length());

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			// Select the next indexed Fooable when 'down' is released
			foobar.selectDown();
			field.setCaretPosition(value.length());

		} else if (e.getKeyCode() != KeyEvent.VK_CONTROL
				&& e.getKeyCode() != KeyEvent.VK_META
				&& e.getKeyCode() != KeyEvent.VK_LEFT
				&& e.getKeyCode() != KeyEvent.VK_RIGHT) {

			if (value.equals("")) {
				// If no input is present, hide the suggestions popup
				foobar.hideSuggestions();
			} else {
				// If input is present, display the suggestions popup
				foobar.displaySuggestions(value);
			}

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
}
