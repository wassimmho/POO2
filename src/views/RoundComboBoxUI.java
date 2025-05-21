package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class RoundComboBoxUI extends BasicComboBoxUI {
    private final Color backgroundColor;
    private final Color borderColor;
    private final int arc;

    public RoundComboBoxUI(Color backgroundColor, Color borderColor, int arc) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.arc = arc;
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        comboBox.setOpaque(false);
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arc, arc);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arc, arc);
        g2.dispose();
        super.paint(g, c);
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        // Do nothing, handled in paint()
    }

    // Hide the arrow button
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setVisible(false);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}