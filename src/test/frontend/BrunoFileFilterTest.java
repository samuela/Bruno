package test.frontend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.bruno.frontend.BrunoFileFilter;
import org.junit.Test;

public class BrunoFileFilterTest {

	@Test
	public void test() {
		FileFilter ff = new BrunoFileFilter();
		assertTrue(ff.accept(new File("README")));
		assertTrue(ff.accept(new File("poop.java")));
		assertFalse(ff.accept(new File("poop.class")));
		assertFalse(ff.accept(new File(".poop")));
		assertFalse(ff.accept(new File("poop.bruno~")));
	}

}
