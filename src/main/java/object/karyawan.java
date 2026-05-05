/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

/**
 *
 * @author ADVAN
 */
public class karyawan {

    
    private String uidRfid;
    private String idKaryawan;
    private String namaLengkap;
    private String departemen;
    private String email;
    private String alamat;
    private int noTelpon;
    
    public karyawan(){
        
    }
    
    public karyawan(String uidRfid, String idKaryawan, String namaLengkap, String departemen, String email, String alamat, int noTelpon) {
        this.uidRfid = uidRfid;
        this.idKaryawan = idKaryawan;
        this.namaLengkap = namaLengkap;
        this.departemen = departemen;
        this.email = email;
        this.alamat = alamat;
        this.noTelpon = noTelpon;
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getNoTelpon() {
        return noTelpon;
    }

    public void setNoTelpon(int noTelpon) {
        this.noTelpon = noTelpon;
    }

    
}
