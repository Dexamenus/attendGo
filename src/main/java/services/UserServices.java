/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

// Pastikan Anda sudah membuat class/form ini nantinya (Dialog.EditUser dan panel.DataUser)
// import Dialog.EditUser; 
// import panel.DataUser;

import com.mongodb.client.model.Filters;
import dao.GenericDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import object.User;
import org.bson.conversions.Bson;

/**
 * Service untuk mengelola entitas User.
 * Menggunakan GenericDAO sebagai penghubung aplikasi dengan database MongoDB.
 */
public class UserServices {

    // Inisialisasi GenericDAO khusus untuk entitas User
    // Menggunakan koleksi "user" dan referensi Class User
    private final GenericDAO<User> DAO;

    public UserServices() {
        this.DAO = new GenericDAO<>("users", User.class);
    }

    /**
     * 1. CREATE: Fungsi untuk menyimpan data user baru ke MongoDB
     *
     * @param userBaru Objek User yang akan disimpan
     */
    public void tambahUser(User userBaru) {
        DAO.save(userBaru); // Memanggil insertOne melalui GenericDAO
    }

    /**
     * Overload CREATE: Fungsi untuk menyimpan user baru menggunakan parameter langsung
     */
    public void tambahUser(String fullname, String username, String password, LocalDateTime lastLogin) {
        User userBaru = new User(fullname, username, password, lastLogin);
        DAO.save(userBaru);
    }

    /**
     * 2. READ (All): Fungsi untuk mengambil semua data user (Output ke Console)
     */
    public void tampilkanDaftarUser() {
        List<User> daftar = DAO.findAll();
        System.out.println("--- Daftar User ---");
        for (User u : daftar) {
            System.out.println("Nama: " + u.getFullname() + " | Username: " + u.getUsername()); 
        }
    }

    /**
     * 2. READ (GUI): Fungsi untuk menampilkan data user ke dalam JPanel bentuk Grid/Card
     *
     * @param panelTarget Panel kosong yang akan diisi daftar user
     * @param key Kata kunci pencarian (jika kosong, tampilkan semua)
     */
    public void tampilUser(JPanel panelTarget, String key){
        // 1. Menampilkan data berdasarkan request
        List<User> daftarUser;
        if (key == null || key.isEmpty()) {
            daftarUser = DAO.findAll();
        } else {
            daftarUser = cariUser(key);
        }

        // 2. Membersihkan panel target utama sebelum memuat data baru
        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());
        panelTarget.setBackground(new Color(242, 242, 242));

        // Membuat panel grid khusus untuk menampung kotak/card
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 3. Iterasi data dan menambahkannya ke panel grid
        // 3. Iterasi data dan menambahkannya ke panel grid
        try {
            Color warnaTeks = new Color(70, 70, 70); // Teks abu-abu gelap

            for (User u : daftarUser) {
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 0));
                cardPanel.setBackground(Color.WHITE); // Background putih bersih

                // Border abu-abu muda
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                JLabel lblNama = new JLabel("Nama: " + u.getFullname());
                lblNama.setForeground(warnaTeks);

                JLabel lblUsername = new JLabel("Username: " + u.getUsername());
                lblUsername.setForeground(warnaTeks);

                String waktuLogin = (u.getLastLogin() != null) ? u.getLastLogin().toString() : "Belum pernah login";
                JLabel lblLogin = new JLabel("Last Login: " + waktuLogin);
                lblLogin.setForeground(warnaTeks);

                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                controlPanel.setBackground(Color.WHITE);

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(new Color(52, 152, 219)); // Biru kalem
                tombolEdit.setForeground(Color.WHITE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Membuka form edit untuk: " + u.getUsername());
                    }
                });

                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setBackground(new Color(231, 76, 60)); // Merah pastel
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addActionListener((ActionEvent e) -> {
                    // ... (Logika konfirmasi hapus biarkan seperti aslinya) ...
                    hapusUser(u.getUsername());
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                cardPanel.add(lblNama);
                cardPanel.add(lblUsername);
                cardPanel.add(lblLogin);
                cardPanel.add(controlPanel);

                gridPanel.add(cardPanel);
            }

            panelTarget.add(gridPanel, BorderLayout.NORTH);
            panelTarget.revalidate();
            panelTarget.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3. READ (Search): Mencari user menggunakan regex Bson Filters
     *
     * @param key Kata kunci pencarian (nama/username)
     * @return List hasil pencarian
     */
    public List<User> cariUser(String key) {
        List<Bson> filters = new ArrayList<>();
        
        for (Field field : User.class.getDeclaredFields()) {
            // Melewati field password dan lastLogin karena pencarian string (regex)
            // pada field bertipe LocalDateTime atau Hash akan menyebabkan error
            if (field.getName().equals("password") || field.getName().equals("lastLogin")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i")); // "i" = case-insensitive
        }
        
        return DAO.findMany(Filters.or(filters));
    }

    /**
     * 4. UPDATE: Memperbarui data user berdasarkan Username
     *
     * @param newU Objek user yang baru
     */
    public void updateUser(User newU) {
        // Menggunakan username sebagai patokan untuk update
        Bson filter = Filters.eq("username", newU.getUsername());
        User u = DAO.findOne(filter);
        
        if (u != null) {
            DAO.update(filter, newU);
            // DataUser.showData(""); // Refresh UI (Sesuaikan dengan nama class Panel Data User Anda)
            JOptionPane.showMessageDialog(null, "Data user berhasil diperbarui!");
        } else {
            JOptionPane.showMessageDialog(null, "User tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 5. DELETE: Menghapus data user berdasarkan Username
     * * @param username username yang akan dihapus
     */
    public void hapusUser(String username) {
        Bson filter = Filters.eq("username", username);
        DAO.delete(filter);
        
        // DataUser.showData(""); // Refresh UI (Sesuaikan dengan nama class Panel Data User Anda)
        JOptionPane.showMessageDialog(null, "Data user berhasil dihapus.");
    }
}