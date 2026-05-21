/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

/**
 *
 * @author HP VICTUS
 */
public class Karyawan1 {

    private String uidRfid;
    private String idKaryawan;
    private String namaLengkap;
    private String departemen;
    private String email;
    private String password;
    
    public Karyawan1() {
    }
    
    public Karyawan1(String uidRfid, String idKaryawan, String namaLengkap, String departemen, String email, String password) {
        this.uidRfid = uidRfid;
        this.idKaryawan = idKaryawan;
        this.namaLengkap = namaLengkap;
        this.departemen = departemen;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Karyawan{" + "uidRfid=" + uidRfid + ", idKaryawan=" + idKaryawan + ", namaLengkap=" + namaLengkap + ", departemen=" + departemen + ", email=" + email + ", password=" + password + '}';
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
