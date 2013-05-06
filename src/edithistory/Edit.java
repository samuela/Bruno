package edithistory;

import java.io.Serializable;
import javax.swing.text.Document;

public interface Edit
{
    public void undo(Document document);
    public String getType();
    public int getLength();
    public boolean isCompound();
}