package foobar;

import java.util.Set;

/**
 * Objects implementing Fooable may be contained in Foobar and executed when
 * specified by the user.
 * 
 * @author Frank Goodman
 * 
 */
public interface Fooable {
	/**
	 * 
	 * @return The name of the Fooable
	 */
	public String getName();

	/**
	 * 
	 * @return A set of the keywords by which this Fooable can be searched
	 */
	public Set<String> getKeywords();

	/**
	 * Executed when this Fooable is selected
	 */
	public void doAction();

	/**
	 * 
	 * @return The display value of the Fooable (typically getName())
	 */
	public String toString();
}
