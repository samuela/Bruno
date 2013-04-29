package foobar;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public final class Foobar extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The complete collection of Fooables callable from this Foobar
	 */
	private final Collection<Fooable> fooables;

	/**
	 * Factory for creating new popups
	 */
	private final PopupFactory factory = PopupFactory.getSharedInstance();
	
	/**
	 * The active Popup
	 */
	private Popup popup;
	
	/**
	 * The active FoobarResults popup
	 */
	private FoobarResults foobarResults;

	protected Foobar() {
		super(new BorderLayout());

		// Store all Fooables in a HashSet
		this.fooables = new HashSet<>();

		// Add the FoobarField to the Foobar
		this.add(new FoobarField());

		this.setVisible(true);
	}

	public void addFooable(Fooable f) {
		this.fooables.add(f);
	}

	public void addFooables(Collection<Fooable> f) {
		this.fooables.addAll(f);
	}

	protected void executeFooable() {
		this.foobarResults.getSelectedValue().doAction();
		this.hideSuggestions();
		((JTextField) this.getComponent(0)).setText("");
	}

	protected String getCompletion(String query) {
		for (Fooable fooable : this.fooables) {
			if (fooable.getName().startsWith(query)) {
				return fooable.getName();
			}
		}

		return query;
	}

	/**
	 * Display the FoobarResults popup with corresponding suggestions for the
	 * term 'query'
	 * 
	 * @param query
	 *            The term by which to search for suggestions
	 */
	protected void displaySuggestions(String query) {
		this.hideSuggestions();

		// Create a list of suggestions based on the complete list of Fooables
		List<Fooable> suggestions = FoobarSuggester.getSuggestions(query,
				this.fooables);

		this.foobarResults = new FoobarResults(this, suggestions);
		this.popup = this.factory.getPopup(this, foobarResults, 0, 75);
		this.foobarResults.setSelectedIndex(0);

		this.popup.show();
	}

	/**
	 * Hide the FoobarResults popup.
	 */
	protected void hideSuggestions() {
		if (popup != null)
			this.popup.hide();
	}

	/**
	 * If possible, select the next index in the FoobarResults popup.
	 */
	protected void selectDown() {
		if (this.foobarResults.getSelectedIndex() < this.foobarResults
				.getLastVisibleIndex()) {
			this.foobarResults.setSelectedIndex(this.foobarResults
					.getSelectedIndex() + 1);
		}
	}

	/**
	 * If possible, select the previous index in the FoobarResults popup.
	 */
	protected void selectUp() {
		if (this.foobarResults.getSelectedIndex() > 0) {
			this.foobarResults.setSelectedIndex(this.foobarResults
					.getSelectedIndex() - 1);
		}
	}
}
