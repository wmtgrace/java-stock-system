
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public class TFLengthRestricted extends PlainDocument{
    private final int limit;

    public TFLengthRestricted(int limit) {
        this.limit = limit;
    }
    @Override
    public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
        if (str == null)
            return;

         if ((getLength() + str.length()) <= limit) {
            super.insertString(offs, str, a);
         }
    }
}
