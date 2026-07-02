package object;

import java.awt.*;
import javax.swing.JPanel;

public class PanelBulatHijau extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // transparan
        setOpaque(false);

        // warna hijau
        g2.setColor(new Color(76, 175, 80));

        // hanya garis
        g2.setStroke(new BasicStroke(6));

        int diameter = Math.min(getWidth(), getHeight()) - 6;

        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        g2.drawOval(x, y, diameter, diameter);

        g2.dispose();
    }

}