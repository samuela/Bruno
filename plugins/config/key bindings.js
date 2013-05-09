importPackage(Packages.javax.swing);
//import some stuff

//key is something like KeyEvent.VK_N for command n
//mask is a string -- either "command" or "control"
//name is a string
function bindKeyToName(key, mask, name){
    if (mask.equals("command")){
	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()), name);
    }
    else if (mask.equals("control")){
	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK()), name);
    }
}

//name is a string
//action is an action which will be called when the key binding is hit
function bindNameToAction(name, action){
    getRootPane().getActionMap().put(name, action);
}

//foo is a fooable
function bindKeyToFooable(key, mask, name, foo){
    bindKeyToName(key, mask, name);
    bindNameToAction(name, bruno.fooAction(foo));
}

//want to bind name to a script