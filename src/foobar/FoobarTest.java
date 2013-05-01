package foobar;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

		// Populate the Foobar with dummy data
		foobar.addFooables(this.fooables);

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

	/**
	 * Dummy data for Foobar
	 */
	private ArrayList<Fooable> fooables = Lists.newArrayList(new Fooable() {
		@Override
		public String getName() {
			return "rest";
		}

		@Override
		public Set<String> getKeywords() {
			return Sets.newHashSet("rest", "best", "crest");
		}

		@Override
		public void doAction() {
			System.out.println(this.getName() + " executed");
		}

		@Override
		public String toString() {
			return this.getName();
		}
	}, new Fooable() {
		@Override
		public String getName() {
			return "test";
		}

		@Override
		public Set<String> getKeywords() {
			return Sets.newHashSet("test", "best", "treat");
		}

		@Override
		public void doAction() {
			System.out.println(this.getName() + " executed");
		}

		@Override
		public String toString() {
			return this.getName();
		}
	}, new Fooable() {
		@Override
		public String getName() {
			return "try";
		}

		@Override
		public Set<String> getKeywords() {
			return Sets.newHashSet("try", "by", "tie");
		}

		@Override
		public void doAction() {
			System.out.println(this.getName() + " executed");
		}

		@Override
		public String toString() {
			return this.getName();
		}
	});
}
