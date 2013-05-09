//import some stuff

function bindToName(key, name){
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()), name);
}

function bindNameToAction(name, action){
    getRootPane().getActionMap().put(name, action);
}

//want to bind name to a script