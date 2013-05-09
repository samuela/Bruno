package org.bruno.foobar;

import java.util.List;

import javax.swing.Popup;
import javax.swing.PopupFactory;

/**
 * FoobarPopupManager creates and manages Popups that display FoobarSuggestions
 * for Foobar.
 * 
 * @author Frank Goodman
 * 
 */
public final class FoobarPopupManager {

	/**
	 * The parent Foobar
	 */
	private final Foobar parent;

	/**
	 * The active Popup being managed
	 */
	private Popup popup;

	/**
	 * The FoobarSuggestions currently (or lastly) displayed within popup
	 */
	private FoobarSuggestions suggestions;

	/**
	 * Create a new FoobarPopupManager belonging to parent.
	 * 
	 * @param parent
	 *            The Foobar containing this object
	 */
	protected FoobarPopupManager(Foobar parent) {
		this.parent = parent;
	}

	/**
	 * Create and show a new Popup containing suggestions as derived from
	 * 'fooables' as its list of Fooables.
	 * 
	 * @param fooables
	 *            The list of Fooables to show in the Popup
	 */
	protected void createPopup(List<Fooable> fooables) {
		// Destroy the previously existing popup if needed
		this.destroyPopup();

		// Create a list of suggestions
		this.suggestions = new FoobarSuggestions(this.parent, fooables);

		// Create a new Popup containing the list of suggestions
		this.popup = PopupFactory.getSharedInstance().getPopup(this.parent,
				this.suggestions, this.parent.getLocationOnScreen().x,
				this.parent.getLocationOnScreen().y + this.parent.getHeight());

		// Display the Popup
		this.popup.show();
	}

	/**
	 * Hide the Popup if present.
	 */
	protected void destroyPopup() {
		if (this.popup != null) {
			this.popup.hide();
		}
	}

	/**
	 * Get the FoobarSuggestions object currently (or last if not showing) used
	 * by this object.
	 * 
	 * @return The last used FoobarSuggestions object
	 */
	protected FoobarSuggestions getSuggestions() {
		return this.suggestions;
	}
}
