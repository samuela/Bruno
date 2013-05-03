package foobar;

import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptException;

import plugins.PluginManager;
import plugins.Script;

/**
 * A Fooable wrapper for Scripts.
 * 
 * @author samuelainsworth
 * 
 */
public class ScriptFooable implements Fooable {

	private final PluginManager manager;
	private final Script script;

	public ScriptFooable(PluginManager manager, Script script) {
		this.manager = manager;
		this.script = script;
	}

	@Override
	public String getName() {
		return script.getName();
	}

	@Override
	public Set<String> getKeywords() {
		Set<String> r = new HashSet<>();
		r.add(script.getName());
		return r;
	}

	@Override
	public void doAction() {
        try {
            manager.executeScript(script);
        } catch (ScriptException e) {
            //e.printStackTrace();
            System.err.println("Error in script " + script.getName());
        }
    }

	@Override
	public String toString() {
		return "ScriptFooable " + script.getName();
	}

}
