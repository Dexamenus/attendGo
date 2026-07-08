/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Dialog.EditKaryawan;
import com.mongodb.client.model.Filters;
import dao.GenericDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import object.Karyawan;
import org.bson.conversions.Bson;
import panel.DataKaryawan;
import util.EncryptionUtils;

/**
 *
 * @author HP VICTUS
 *////saya menggunakan GenericDAO Karyawan sebagai penghubung antara aplikasi dengan database MongoDB.
public class KaryawanServices {

    // Inisialisasi GenericDAO khusus untuk entitas Karyawan
    // Menggunakan koleksi "karyawan" dan referensi Class Karyawan [3]
    private final GenericDAO<Karyawan> DAO;

    public KaryawanServices(){
        this.DAO = new GenericDAO<>("Karyawan", Karyawan.class);
    }

    /**
     * 1.CREATE: Fungsi untuk menyimpan data karyawan baru ke MongoDB [2], [3]
     *
     * @param karyawanBaru
     *///digunakan untuk menambahkan data karyawan baru dari UID RFID-password
    public void tambahKaryawan(Karyawan karyawanBaru) {
        DAO.save(karyawanBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    public void tambahKaryawan(String uidRfid, String idKaryawan, String namaLengkap, String departemen, String email, String password) {
        Karyawan karyawanBaru = new Karyawan(uidRfid, idKaryawan, namaLengkap, departemen, email, password);
        DAO.save(karyawanBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    /**
     * 2. READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     *///Fungsi ini digunakan untuk menampilkan data karyawan ke tampilan GUI
    public void tampilkanDaftarKaryawan() {
        List<Karyawan> daftar = DAO.findAll();
        System.out.println("--- Daftar Karyawan Bank ---");
        for (Karyawan k : daftar) {
            System.out.println(k.toString()); // Menggunakan format toString di sumber [7]
        }
    }

    /**
     * 2.READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     *
     * @param panelTarget
     * @param key
     */
    public void tampilKaryawan(JPanel panelTarget, String key) {
        //1. 
        // Menampilkan data berdasarkan request
        // key "null/kosong" = get all data
        // key "filled" = get specific data

        List<Karyawan> daftarKaryawan;
        if (key.isEmpty()) {
            //Mengambil data dari database menggunakan GenericDAO
            daftarKaryawan = DAO.findAll();
        } else {
            //Mengambil data dari database menggunakan GenericDAO
            //berdasarkan kata kunci yang diketik
            daftarKaryawan = cariKaryawan(key);
        }
        // 2. Membersihkan panel target utama sebelum memuat data baru
        panelTarget.removeAll();

        // Mengubah layout panel target menjadi BorderLayout
        panelTarget.setLayout(new BorderLayout());
        // Mengatur warna background utama menjadi biru
        panelTarget.setBackground(new Color(242, 242, 242));

        // Membuat panel grid khusus untuk menampung kotak/card
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false); // Transparan agar warna biru panelTarget terlihat
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Memberi jarak dari tepi layar
        

        // 3. Iterasi data dan menambahkannya ke panel grid
        // 3. Iterasi data dan menambahkannya ke panel grid
        try {
            // Warna font global untuk teks di dalam kartu
            Color warnaTeks = new Color(70, 70, 70); // Abu-abu gelap (Nyaman dibaca)

            for (Karyawan k : daftarKaryawan) {
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 0));
                cardPanel.setBackground(Color.WHITE); // Background kartu jadi Putih bersih

                // Border abu-abu muda yang elegan
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                JLabel lblNama = new JLabel("Nama: " + k.getNamaLengkap());
                lblNama.setForeground(warnaTeks);

                JLabel lblIDK = new JLabel("ID Karyawan: " + EncryptionUtils.decrypt(k.getIdKaryawan()));
                lblIDK.setForeground(warnaTeks);

                JLabel lblDept = new JLabel("Departmen: " + k.getDepartemen());
                lblDept.setForeground(warnaTeks);

                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Jarak tombol dirapatkan sedikit
                controlPanel.setBackground(Color.WHITE); // Harus sama dengan background kartu

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(new Color(52, 152, 219)); // Biru kalem (Modern Flat UI)
                tombolEdit.setForeground(Color.WHITE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EditKaryawan.txtUID.setText(k.getUidRfid());
                        EditKaryawan.txtKRID.setText(EncryptionUtils.decrypt(k.getIdKaryawan()));
                        EditKaryawan.txtKRID.setEnabled(false);
                        EditKaryawan.txtNama.setText(k.getNamaLengkap());
                        EditKaryawan.txtDept.setSelectedItem(k.getDepartemen());
                        EditKaryawan.txtEmail.setText(k.getEmail());
                    }
                });

                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setBackground(new Color(231, 76, 60)); // Merah pastel (Tidak terlalu menyilaukan)
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addActionListener((ActionEvent e) -> {
                    // ... (Biarkan logika delete milikmu tetap seperti aslinya di sini) ...
                    hapusKaryawan(k.getIdKaryawan()); 
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                cardPanel.add(lblNama);
                cardPanel.add(lblIDK);
                cardPanel.add(lblDept);
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
     * 3.READ (One): Mencari satu karyawan spesifik berdasarkan UID RFID [5],
     * [6] Sangat krusial untuk alur Tap Kartu pada Pertemuan 14 [8].
     *
     * @param key
     * @return
     */
    public List<Karyawan> cariKaryawan(String key) {
        List<Bson> filters = new ArrayList<>();
        // Get all fields from the Karyawan class
        for (Field field : Karyawan.class.getDeclaredFields()) {
            // Skip the uidRfid field and non-string fields if necessary
            if (field.getName().equals("uidRfid")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        // Search and return Karyawan objects directly
        List<Karyawan> results = DAO.findMany(Filters.or(filters));
        return results;
    }

    /**
     * 4.UPDATE: Memperbarui data karyawan menggunakan filter Bson [5], [6]
     *
     * @param newK
     */
    public void updateKaryawan(Karyawan newK) {
        Bson filter = Filters.eq("idKaryawan", newK.getIdKaryawan());
        Karyawan k = DAO.findOne(filter);
        if (k != null) {
            DAO.update(filter, newK);
            DataKaryawan.showData("");
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }
    }///Sistem akan mencari data berdasarkan idKaryawan

    /**
     * @param idK
     */
    public void hapusKaryawan(String idK) {
        Bson filter = Filters.eq("idKaryawan", idK);
        DAO.delete(filter); // Menggunakan deleteOne [6]
        DataKaryawan.showData("");
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }
}///Fungsi ini digunakan untuk menghapus data karyawan berdasarkan ID karyawan
