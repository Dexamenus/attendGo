/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.*;
import javax.swing.JPanel;

public class PanelBulat extends JPanel {

    public PanelBulat() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(220,220,220));

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth()-diameter)/2;
        int y = (getHeight()-diameter)/2;

        g2.fillOval(x,y,diameter,diameter);

        g2.dispose();
    }
}
