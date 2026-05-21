package dao;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import object.MongoM;
import org.bson.conversions.Bson;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP VICTUS
 */
public class GenericDAO<T> implements BaseDAO<T> {

    private final MongoCollection<T> collection;
    private final Class<T> clazz;

    public GenericDAO(String collectionName, Class<T> clazz) {
        this.clazz = clazz;
        this.collection = MongoM.getDatabase().getCollection(collectionName, clazz);
    }

    @Override
    public void save(T entity) {
        collection.insertOne(entity);        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Bson filter, T entity) {
        collection.replaceOne(filter, entity);
    }

    @Override
    public void delete(Bson filter) {
        collection.deleteOne(filter);
    }

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public T findOne(Bson filter) {
        return collection.find(filter).first();
    }

    @Override
    public List<T> findMany(Bson filter) {
        return collection.find(filter).into(new ArrayList<>());
    }

}
