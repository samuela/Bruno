package foobar;

import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: jonathan
 * Date: 4/29/13
 * Time: 12:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Fooable {
        public String getName(); // the name of this Fooable
        public Set<String> getKeywords(); // these are the word by which Foobar searches for this Fooable
        public void doAction(); // this is called when a suggestion is selected
        public String toString(); // should return whatever you want to appear in the suggestion menu, typically getName()
}

