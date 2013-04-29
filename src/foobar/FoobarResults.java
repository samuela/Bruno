package foobar;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public final class FoobarResults extends JList<Fooable> {
	private static final long serialVersionUID = 1L;

	private final DefaultListModel<Fooable> model;

	protected FoobarResults(final Foobar parent, List<Fooable> results) {
		super();

		// Use the DefaultListModel
		this.model = new DefaultListModel<>();
		this.setModel(this.model);

		// Allow a maximum of one option to be selected
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Select the first option
		this.setSelectedIndex(0);

		// Execute any Fooable that is double-clicked
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					parent.executeFooable();
			}
		});

		// Populate the result list with up to 5 Fooables
		for (Fooable result : results.subList(0, results.size() > 5 ? 5
				: results.size())) {
			this.model.addElement(result);
		}

		// Adjust the size of the result list to fit all the Fooables
		this.setPreferredSize(new Dimension(parent.getParent().getWidth(),
				results.size() * 17));

		this.setVisible(true);
	}
}
