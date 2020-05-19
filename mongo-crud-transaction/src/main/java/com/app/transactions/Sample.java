/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.transactions;

import com.app.model.Cart;
import com.mongodb.MongoClient;
import com.mongodb.TransactionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.TransactionBody;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

/**
 *
 * @author Denys.Prokopiuk
 */
public class Sample {

    public static void main(String[] args) {
        String mongodbURI = "mongodb+srv://root:rootpassword@cluster0-daeju.mongodb.net/test?retryWrites=true&w=majority";
        MongoClient client = Utils.getMongoClient(mongodbURI, "com.app.model");
        //ClientSession clientSession = client.startSession();

        try {

            MongoDatabase db = client.getDatabase("testtransactions");
            //MongoCollection<Cart> coll1 = db.getCollection("cart", Cart.class);
            MongoCollection<Document> collection = db.getCollection("cart");
            Document filter = new Document();
            //filter.put("type", typeFilter);
            Document update = new Document();
            Document updateData = new Document();
            updateData.put("name", "MY-UPDATE");
            update.put("$set", updateData);
            UpdateOptions updateOptions = new UpdateOptions();
            updateOptions.upsert(true);
            collection.updateMany(filter, update, updateOptions);
        } catch (RuntimeException e) {
            e.printStackTrace();
            //clientSession.abortTransaction();
        } finally {
          //  clientSession.close();
        }

    }
    
}
