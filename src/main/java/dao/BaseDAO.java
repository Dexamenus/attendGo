package dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP VICTUS
 */
import java.util.List;
import org.bson.conversions.Bson;

public interface BaseDAO<T> {
    void save(T entity);
    void update(Bson filter, T entity);
    void delete(Bson filter);
    // Operasi Searching/Reading
    List<T> findAll();
    T findOne(Bson filter); // Mencari satu data spesifik
    List<T> findMany(Bson filter);
}
