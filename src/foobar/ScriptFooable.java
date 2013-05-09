package foobar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptException;

import plugins.PluginManager;
import plugins.Script;

/**
 * A wrapper Fooable for executing scripts.
 * 
 * @author samuelainsworth
 * 
 */
public class ScriptFooable implements Fooable {

	private final PluginManager manager;
	private final Script script;

	/**
	 * Create a Fooable wrapper for manager using script.
	 * 
	 * @param manager
	 *            The plugin manager
	 * @param script
	 *            The script
	 */
	public ScriptFooable(PluginManager manager, Script script) {
		this.manager = manager;
		this.script = script;
	}

	/**
	 * @return The name of the Fooable
	 */
	@Override
	public String getName() {
		return script.getName();
	}

	/**
	 * @return A set of keywords for searching for this Fooable
	 */
	@Override
	public Set<String> getKeywords() {
		Set<String> r = new HashSet<>();
		r.addAll(Arrays.asList(getName().split(" ")));
		return r;
	}

	/**
	 * Execute this Fooable's script.
	 */
	@Override
	public void doAction() {
		try {
			manager.executeScript(script);
		} catch (ScriptException e) {
			// e.printStackTrace();
			System.err.println("Error in script " + script.getName());
		}
	}

	@Override
	public String toString() {
		return script.getName();
	}

}
