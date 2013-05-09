package edithistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a compression of nodes
 */
public class Mask implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<CompoundEdit> maskedEdits;
    private CompoundEdit maskingEdit;

    public Mask(CompoundEdit maskingEdit)
    {
	this.maskingEdit = maskingEdit;
	maskingEdit.addMask(this);
	maskedEdits = new ArrayList<>();
    }

    public void addEdit(CompoundEdit edit)
    {
	maskedEdits.add(edit);
	edit.setMask(this);
    }

    public List<CompoundEdit> getMaskedEdits()
    {
	return maskedEdits;
    }


}