package frontend;

import java.awt.BorderLayout;

import javax.swing.JComponent;

public class ComponentPlaceholder extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -509163587624428060L;

	private JComponent contents;

	public ComponentPlaceholder() {
	
	}

	public ComponentPlaceholder(JComponent contents) {
		setContents(contents);
	}

	public JComponent getContents() {
		return contents;
	}

	public void setContents(JComponent contents) {
		if (this.contents != null) {
			remove(this.contents);
		}
		setLayout(new BorderLayout());
		this.contents = contents;
		add(contents, BorderLayout.CENTER);
	}
}
