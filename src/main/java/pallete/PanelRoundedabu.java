package pallete;

import java.awt.*;
import javax.swing.JPanel;

public class PanelRoundedabu extends JPanel {

    public PanelRoundedabu() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // background putih
        g2.setColor(Color.WHITE);

        g2.fillRoundRect(
                3,
                3,
                getWidth()-6,
                getHeight()-6,
                30,
                30);

        // border hijau

        g2.setStroke(new BasicStroke(2));

        g2.setColor(new Color(204,204,204));

        g2.drawRoundRect(
                3,
                3,
                getWidth()-6,
                getHeight()-6,
                30,
                30);

        g2.dispose();

        super.paintComponent(g);

    }

}