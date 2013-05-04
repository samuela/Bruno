package edithistory;


public class RedoEdit extends Edit {
	public RedoEdit(Edit parent) {
		super(parent);
		parent.redo(this);
	}

	@Override
	public void backInTime() {
		getAffectedEdit().backInTime();
	}

	@Override
	public void forwardInTime() {
		getAffectedEdit().forwardInTime();
	}

}