package foobar;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doAction() {
		try {
			manager.executeScript(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

}
