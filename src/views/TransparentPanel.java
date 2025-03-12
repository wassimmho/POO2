package views;

import java.awt.*;
import javax.swing.*;

public class TransparentPanel extends JPanel {
    private float opacity;

    public TransparentPanel(float opacity) {
        this.opacity = opacity;
        setOpaque(true); // Make sure the panel is opaque
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.paintComponent(g2d);
        g2d.dispose();
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }
}
