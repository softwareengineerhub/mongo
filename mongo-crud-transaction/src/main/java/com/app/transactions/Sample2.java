/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.transactions;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.Collection;

/**
 *
 * @author Denys.Prokopiuk
 */
public class Sample2 {

    public static void main(String[] args) {
        String mongodbURI = "mongodb+srv://root:rootpassword@cluster0-daeju.mongodb.net/test?retryWrites=true&w=majority";
        MongoClient client = Utils.getMongoClient(mongodbURI, "com.app.model");
        //ClientSession clientSession = client.startSession();

        try {

            MongoDatabase db = client.getDatabase("testtransactions");
            //MongoCollection<Cart> coll1 = db.getCollection("cart", Cart.class);
            MongoCollection<Document> collection = db.getCollection("compositekey");
            //save(1, "mykeyb1", "value1", collection);
            //save(22, "mykeyb2", "value2", collection);
            //save(3, "mykeyb3", "value3", collection);

          //  update2("mykeyb1", "UPDATE-VALUE", collection);
          //  remove2("mykeyb1", collection);

              update2("mykeyb2", "UPDATE-VALUE2", collection);
              remove2("mykeyb2", collection);


        } catch (RuntimeException e) {
            e.printStackTrace();
            //clientSession.abortTransaction();
        } finally {
          //  clientSession.close();
        }
    }

    public static void update2(String keyBCurrentValue, String keyBUpdateCandidate, MongoCollection<Document> collection) {
        FindIterable<Document> itr = collection.find();
        for(Document item: itr){
            Document composeIdCurrent = (Document) item.get("_id");
            if(keyBCurrentValue.equals(composeIdCurrent.get("keyB"))){
                Document composeId = new Document();
                composeId.put("keyA", composeIdCurrent.get("keyA"));
                composeId.put("keyB", keyBUpdateCandidate);
                item.put("_id", composeId);
                collection.insertOne(item);
            }
        }
    }

    public static void remove2(String keyBCurrentValue, MongoCollection<Document> collection) {
        FindIterable<Document> itr = collection.find();
        for(Document item: itr){
            Document composeIdCurrent = (Document) item.get("_id");
            if(keyBCurrentValue.equals(composeIdCurrent.get("keyB"))){
                collection.deleteOne(item);
            }
        }
    }





    public static void update(String keyBCurrentValue, String keyBUpdateCandidate, MongoCollection<Document> collection) {
        FindIterable<Document> itr = collection.find();
        //DataItem dataItem = null;
        for(Document item: itr){
            Document composeId = (Document) item.get("_id");
            //System.out.println(composeId.get("keyB"));



            if(keyBCurrentValue.equals(composeId.get("keyB"))){
                //composeId.put("keyB", keyBUpdateCandidate);

                Document keyDocument = new Document();
                keyDocument.put("keyA", composeId.get("keyA"));
                keyDocument.put("keyB", keyBUpdateCandidate);

                Document filter = new Document();
                filter.put("_id", composeId);
                Document update = new Document();
                Document updateData = new Document();
                updateData.put("_id", keyDocument);

                update.put("$set", updateData);
                UpdateOptions updateOptions = new UpdateOptions();
                updateOptions.upsert(false);
                collection.updateMany(filter, update, updateOptions);
            }


        }
    }


    public static void save(int keyA, String keyB, String value, MongoCollection<Document> collection) {
        Document document = new Document();
        Document keyDocument = new Document();
        keyDocument.put("keyA", keyA);
        keyDocument.put("keyB", keyB);
        document.put("_id", keyDocument);
        //document.put("type", dataItem.getType());
        document.put("value", value);
        //MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        collection.insertOne(document);
    }
    
}
