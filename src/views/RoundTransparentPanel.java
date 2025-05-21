package views;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundTransparentPanel extends JPanel {
    private float opacity;
    private int cornerRadius;

    public RoundTransparentPanel(float opacity, int cornerRadius) {
        this.opacity = opacity;
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make sure the panel is not opaque
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        super.paintComponent(g2d);
        g2d.dispose();
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }
}
