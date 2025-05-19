package aplication;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ScalableUtils {

    public static Consumer<JComponent> createScaler(int screenWidth, int screenHeight) {
        final int BASE_WIDTH = 1024;  
        final int BASE_HEIGHT = 768;

        return comp -> {
            Rectangle bounds = comp.getBounds();
            double scaleX = (double) screenWidth / BASE_WIDTH;
            double scaleY = (double) screenHeight / BASE_HEIGHT;
            double scale = Math.min(scaleX, scaleY) * 0.9;

            comp.setBounds(
                (int) (bounds.x * scale),
                (int) (bounds.y * scale),
                (int) (bounds.width * scale),
                (int) (bounds.height * scale)
            );

            if (comp instanceof JLabel || comp instanceof JButton) {
                Font originalFont = comp.getFont();
                comp.setFont(new Font(
                    originalFont.getName(),
                    originalFont.getStyle(),
                    (int) (originalFont.getSize() * scale)
                ));
            }
        };
    }

}
