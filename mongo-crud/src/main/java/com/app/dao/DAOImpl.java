/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.model.DataItem;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Denys.Prokopiuk
 */
public class DAOImpl implements DAO {
    
    private String mongoDB = "my_crud";
    private String mongoCollection = "data_item";
    private MongoDatabase mongoDatabase;
    
    public DAOImpl() {
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            mongoDatabase = mongoClient.getDatabase(mongoDB);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }    
    
    @Override
    public void save(DataItem dataItem) {
        Document document = new Document();
        document.put("type", dataItem.getType());
        document.put("value", dataItem.getValue());
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        collection.insertOne(document);
    }

    @Override
    public void update(DataItem dataItem, String typeFilter) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        Document filter = new Document();
        filter.put("type", typeFilter);
        Document update = new Document();
        Document updateData = new Document();
        updateData.put("type", dataItem.getType());
        updateData.put("value", dataItem.getValue());
        update.put("$set", updateData);
        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);
        collection.updateMany(filter, update, updateOptions);
    }

    @Override
    public void delete(String valueFilter) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        Document filter = new Document();
        filter.put("value", valueFilter);
        collection.deleteMany(filter);
    }

    @Override
    public List<DataItem> getDataItemByType(String type) {
        List<DataItem> resultList = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        Document document = new Document();
        document.put("type", type);
        FindIterable<Document> itr = collection.find(document);
        DataItem dataItem = null;
        for(Document item: itr){
            dataItem = new DataItem();                        
            dataItem.setType(item.getString("type"));
            dataItem.setValue(item.getString("value"));
            resultList.add(dataItem);
        }
        return resultList;
    }

    @Override
    public List<DataItem> getDataItemByValue(String value) {
        List<DataItem> resultList = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        Document document = new Document();
        document.put("value", value);
        FindIterable<Document> itr = collection.find(document);
        DataItem dataItem = null;
        for(Document item: itr){
            dataItem = new DataItem();                        
            dataItem.setType(item.getString("type"));
            dataItem.setValue(item.getString("value"));
            resultList.add(dataItem);
        }
        return resultList;
    }

    @Override
    public List<String> getValuesByType(String type) {
        List<String> resultList = new ArrayList<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        Document document = new Document();
        document.put("type", type);
        FindIterable<Document> itr = collection.find(document).projection(Projections.include("value"));
        for (Document item : itr) {
            resultList.add(item.getString("value"));
        }
        return resultList;
    }
    
    
    
}
