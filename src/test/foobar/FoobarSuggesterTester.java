package test.foobar;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bruno.foobar.FileFooable;
import org.bruno.foobar.Fooable;
import org.bruno.foobar.FoobarSuggester;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Test the FoobarSuggester. Additional unit testing and system testing was done
 * by hand using the GUI.
 * 
 * @author Frank Goodman
 * 
 */
public class FoobarSuggesterTester {
	/**
	 * The collection of Fooables
	 */
	private static Set<Fooable> fooables = new HashSet<>();

	/**
	 * Setup the collection of Fooables
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fooables.add(new Fooable() {
			@Override
			public String getName() {
				return "turtles";
			}

			@Override
			public Set<String> getKeywords() {
				return Sets.newHashSet(getName());
			}

			@Override
			public void doAction() {

			}

			@Override
			public String toString() {
				return getName();
			}
		});

		fooables.add(new Fooable() {
			@Override
			public String getName() {
				return "no way to find me!";
			}

			@Override
			public Set<String> getKeywords() {
				return Sets.newHashSet();
			}

			@Override
			public void doAction() {

			}

			@Override
			public String toString() {
				return getName();
			}
		});

		fooables.add(new FileFooable(null, null, new File("test.txt")));
	}

	/**
	 * Test the suggester with an open command
	 */
	@Test
	public void testFoobarOpen() {
		assertTrue(FoobarSuggester.getSuggestions("open t", fooables).size() == 1);
		assertTrue(FoobarSuggester.getSuggestions("open t", fooables).get(0)
				.equals(new FileFooable(null, null, new File("test.txt"))));
	}

	/**
	 * Test the Foobar
	 */
	@Test
	public void testFoobar() {
		assertTrue(FoobarSuggester.getSuggestions("", fooables).size() == 2);
		assertFalse(FoobarSuggester.getSuggestions("", fooables).contains(
				new FileFooable(null, null, new File("test.txt"))));
	}

	/**
	 * Test the suggester
	 */
	@Test
	public void testFoobarSuggester() {
		assertTrue(FoobarSuggester.getSuggestions("tur", fooables).size() == 2);
		assertTrue(FoobarSuggester.getSuggestions("", fooables).get(0)
				.toString().equals("turtles"));
	}
}
