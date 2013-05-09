package org.bruno.frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733810294719463417L;

	private String placeholderText;

	public void setPlaceholderText(String placeholderText) {
		this.placeholderText = placeholderText;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (placeholderText != null
				&& !placeholderText.isEmpty()
				&& getText().isEmpty()
				&& !(FocusManager.getCurrentKeyboardFocusManager()
						.getFocusOwner() == this)) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setBackground(Color.gray);
			g2.setFont(new Font("Verdana", Font.ITALIC, 12));
			g2.drawString(placeholderText, 10, 20); // figure out x, y from
													// font's
			// FontMetrics and size of
			// component.
			g2.dispose();
		}
	}
}
