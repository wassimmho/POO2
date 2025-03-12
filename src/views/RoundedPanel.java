
package views;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private int radius;
    Image backgroundImage;
    private boolean hasGradient;
    private int gradientHeight;
    private Color gradientStartColor;
    private Color gradientEndColor;
    private boolean hasBorder;
    private float borderThickness;
    private Color borderColor;

    public RoundedPanel(int radius) {
        this(radius, false);
    }

    public RoundedPanel(int radius, boolean hasGradient) {
        this.radius = radius;
        this.hasGradient = hasGradient;
        this.gradientHeight = 60;
        this.gradientStartColor = new Color(0, 0, 0, 0);
        this.gradientEndColor = new Color(0, 0, 0, 230);
        setOpaque(false);
        this.hasBorder = false;
        this.borderThickness = 1;
        this.borderColor = Color.WHITE;
    }
    public void setRoundedBorder(Color color, float thickness) {
        this.hasBorder = true;
        this.borderColor = color;
        this.borderThickness = thickness;
        repaint();
    }

    public void removeBorder() {
        this.hasBorder = false;
        repaint();
    }
    public void setGradient(int height, Color startColor, Color endColor) {
        this.gradientHeight = height;
        this.gradientStartColor = startColor;
        this.gradientEndColor = endColor;
        repaint();
    }

    public void setBackgroundImage(String imagePath) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("Warning: Failed to load image from " + imagePath);
                return;
            }
            this.backgroundImage = icon.getImage();
            repaint();
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Create the round rectangle shape
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
            borderThickness/2.0, 
            borderThickness/2.0, 
            getWidth() - borderThickness, 
            getHeight() - borderThickness, 
            radius, 
            radius
        );
    
        // Set the clip to ensure everything is drawn within rounded borders
        g2.setClip(roundedRect);
    
        // Draw background image if exists
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            
            // Draw gradient if enabled
            if (hasGradient) {
                GradientPaint gradient = new GradientPaint(
                    0, getHeight() - gradientHeight, gradientStartColor,
                    0, getHeight(), gradientEndColor
                );
                g2.setPaint(gradient);
                g2.fillRect(0, getHeight() - gradientHeight, getWidth(), gradientHeight);
            }
        } else {
            // Fill with background color if no image
            g2.setColor(getBackground());
            g2.fill(roundedRect);
        }
    
        // Draw border if enabled
        if (hasBorder) {
            g2.setClip(null);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.setColor(borderColor);
            g2.draw(roundedRect);
        }
    
        g2.dispose();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200); // Default size, can be overridden
    }
}
