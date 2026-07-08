package dao;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import util.MongoM;
import org.bson.conversions.Bson;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP VICTUS
 *///Tujuannya agar satu class DAO dapat digunakan untuk berbagai jenis data tanpa mengulang crud
public class GenericDAO<T> implements BaseDAO<T> {
///bersifat fleksibel dan dapat menerima tipe data apa saja.
    private final MongoCollection<T> collection;
    private final Class<T> clazz;
//tidak hanya bisa digunakan untuk data karyawan, tetapi bisa log absensi dan data admin

//Di dalam constructor, terdapat parameter collectionName dan Class<T> clazz
    public GenericDAO(String collectionName, Class<T> clazz) {
        this.clazz = clazz;
        this.collection = MongoM.getDatabase().getCollection(collectionName, clazz);
    }///digunakan untuk menentukan nama collection MongoDB dan tipe object yang akan diproses. pakenya ini

    @Override
    public void save(T entity) {
    }

    @Override
    public void update(Bson filter, T entity) {
        collection.replaceOne(filter, entity);
    }//memperbarui data berdasarkan filter tertentu dengan replaceOne(filter, entity).

    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }///enghapus data menggunakan deleteOne(filter).

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }///untuk mengambil seluruh data dari collection database.

    @Override
    public T findOne(Bson filter) {
        return collection.find(filter).first();
    }////digunakan untuk mencari satu data berdasarkan filter tertentu.

    @Override
    public List<T> findMany(Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }////untuk mencari banyak data sekaligus sesuai filter pencarian.

}
