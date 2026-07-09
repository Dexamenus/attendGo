package services;

import com.mongodb.client.model.Filters;
import dao.GenericDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import object.Karyawan;
import object.LogAbsensi;
import org.bson.conversions.Bson;
import util.EncryptionUtils;
import util.SecurityUtils;

public class LogAbsensiService {
    
    private final GenericDAO<LogAbsensi> logDAO = new GenericDAO<>("log_absensi", LogAbsensi.class);
    private final GenericDAO<Karyawan> karyawanDAO = new GenericDAO<>("Karyawan", Karyawan.class);

    public void simpanLog(String hashedUid, String status) {
        LogAbsensi log = new LogAbsensi(
            java.util.UUID.randomUUID().toString(), 
            hashedUid, 
            LocalDateTime.now(), 
            status
        );
        logDAO.save(log); 
    }
    
    public void catatAbsensiScan(String rawUid, String status) {
        String hashedUid = SecurityUtils.getHash(rawUid, SecurityUtils.SHA_256);
        simpanLog(hashedUid, status);
    }

    public boolean catatAbsensiManualDenganId(String karyawanId, String status) {
        try {
            // 1. Enkripsi ID mentah yang diinput
            String encryptedId = util.EncryptionUtils.encrypt(karyawanId); 
            
            // ================================================================
            // 🔍 KODE DEBUG SEMENTARA (SANGAT PENTING)
            // ================================================================
            System.out.println("\n========== 🔴 DEBUG ABSENSI MANUAL 🔴 ==========");
            System.out.println("ID Mentah yang kamu ketik : " + karyawanId);
            System.out.println("Hasil Enkripsi dicari    : " + encryptedId);
            
            // Ambil semua data karyawan dari MongoDB untuk di-print ke console
            List<Karyawan> semuaKaryawan = karyawanDAO.findAll();
            System.out.println("Total data Karyawan di DB : " + semuaKaryawan.size());
            
            for (Karyawan kar : semuaKaryawan) {
                System.out.println("👉 Ada di DB -> ID Terenkripsi: [" + kar.getIdKaryawan() + "] | Nama: " + kar.getNamaLengkap());
            }
            System.out.println("================================================\n");
            // ================================================================
            
            // 2. Cari karyawan berdasarkan ID yang SUDAH DI-ENKRIPSI
            org.bson.conversions.Bson filterId = com.mongodb.client.model.Filters.eq("idKaryawan", encryptedId); 
            Karyawan k = karyawanDAO.findOne(filterId);
            
            if (k != null) {
                String rawUid = k.getUidRfid(); 
                catatAbsensiScan(rawUid, status);
                return true; 
            }
        } catch (Exception e) {
            System.err.println("ERROR pada enkripsi/pencarian ID Karyawan: " + e.getMessage());
            e.printStackTrace();
        }
        return false; 
    }

    public DefaultTableModel getFilteredLogTable(Date tanggalCari, String statusCari, String namaCari) {
        String[] columnNames = {"ID Log", "Nama Karyawan", "Tanggal", "Waktu Absen", "Status Kehadiran"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Bson> filters = new ArrayList<>();

            if (statusCari != null && !statusCari.equalsIgnoreCase("Semua") && !statusCari.trim().isEmpty()) {
                filters.add(Filters.eq("status", statusCari));
            }

            if (tanggalCari != null) {
                LocalDate localDate = tanggalCari.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDateTime startOfDay = localDate.atStartOfDay(); 
                LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX); 
                
                filters.add(Filters.gte("waktu", startOfDay));
                filters.add(Filters.lte("waktu", endOfDay));
            }

            List<LogAbsensi> listLog;
            if (filters.isEmpty()) {
                listLog = logDAO.findAll(); 
            } else {
                Bson combinedFilter = Filters.and(filters);
                listLog = logDAO.findMany(combinedFilter);
            }

            DateTimeFormatter tglFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter jamFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            List<Karyawan> listKaryawan = karyawanDAO.findAll();

            for (LogAbsensi log : listLog) {
                String namaKaryawan = "Tidak Diketahui";
                String hashDariLog = log.getUidRfid(); 

                for (Karyawan karyawan : listKaryawan) {
                    String hashKaryawan = SecurityUtils.getHash(karyawan.getUidRfid(), SecurityUtils.SHA_256);
                    if (hashKaryawan != null && hashKaryawan.equals(hashDariLog)) {
                        namaKaryawan = karyawan.getNamaLengkap();
                        break; 
                    }
                }

                if (namaCari != null && !namaCari.trim().isEmpty()) {
                    if (!namaKaryawan.toLowerCase().contains(namaCari.toLowerCase())) {
                        continue; 
                    }
                }

                Object[] row = {
                    log.getIdLog(),
                    namaKaryawan,
                    log.getWaktuTap().format(tglFormat), 
                    log.getWaktuTap().format(jamFormat), 
                    log.getStatus()
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}