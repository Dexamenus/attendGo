/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pallete;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author mnish
 */
public class AutoScaledButton extends JButton {
    private Image image;

    public AutoScaledButton() {
        super();
        initDefault();
    }

    public AutoScaledButton(ImageIcon icon) {
        super();
        if (icon != null) {
            this.image = icon.getImage();
        }
        initDefault();
    }

    private void initDefault() {
        // Mengatur default background menjadi putih
        setBackground(Color.WHITE); 
        
        setContentAreaFilled(false); // Mematikan background bawaan OS agar tidak bentrok
        setFocusPainted(false);      // Menghilangkan garis kotak putus-putus saat diklik
        // setBorderPainted(false);  // Hapus komentar ini jika ingin border dihilangkan
    }

    @Override
    public void setIcon(Icon icon) {
        if (icon instanceof ImageIcon imageIcon) {
            this.image = imageIcon.getImage();
        } else {
            this.image = null;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // 1. Gambar background dasar terlebih dahulu (Putih / sesuai setBackground)
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2. Gambar citra kita di atas background putih
        if (image != null) {
            // RenderingHints agar gambar hasil resize tetap halus
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            // Menggambar citra
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        
        g2.dispose(); // Membersihkan resource Graphics2D
        
        // 3. Panggil fungsi bawaan agar teks tombol digambar di paling atas
        super.paintComponent(g);
    }
}