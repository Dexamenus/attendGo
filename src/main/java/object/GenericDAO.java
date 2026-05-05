package object;

import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP VICTUS
 */
public class GenericDAO<T> implements BaseDAO<T>{

    private final String Karyawan;
    private final Class<T> cls; 
    private List<T> dataList = new ArrayList<>();
    
    public GenericDAO(String Karyawan, Class<T> cls) {
        this.Karyawan = Karyawan;
        this.cls = cls;
    }
   

    @Override
    public void save(T entity) {
        dataList.add(entity);
        // Pada Pertemuan 5, clazz akan digunakan oleh MongoDB Driver 5.0.0 
        // untuk mapping POJO (Plain Old Java Object) secara otomatis [2, 7].
        System.out.println("Menyimpan objek tipe: " + cls.getSimpleName() + 
                           " ke koleksi: " + Karyawan);
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(int index, T entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T findByIndex(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
