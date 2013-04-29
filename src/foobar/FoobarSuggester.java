package foobar;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public abstract class FoobarSuggester {
	public static final List<Fooable> getSuggestions(String query,
			Collection<Fooable> fooables) {
		return Lists.newArrayList(fooables);
	}
}
