package aplication;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LetterDocumentFilter extends DocumentFilter {
    private int maxLength;

    public LetterDocumentFilter() {
        this.maxLength = Integer.MAX_VALUE; 
    }

    public LetterDocumentFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
            throws BadLocationException {
        if (string == null) {
            return;
        }
        
        if (isLetter(string) && (fb.getDocument().getLength() + string.length()) <= maxLength) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
            throws BadLocationException {
        if (text == null) {
            return;
        }
        
        if (isLetter(text) && (fb.getDocument().getLength() - length + text.length()) <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isLetter(String text) {
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*");
    }
}