/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.transactions;

import com.app.model.Cart;
import com.app.model.Product;
import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.TransactionBody;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author Denys.Prokopiuk
 */
public class Main5TransactionsRollback {

    public static void main(String[] args) {
        String mongodbURI = "mongodb+srv://root:rootpassword@cluster0-daeju.mongodb.net/test?retryWrites=true&w=majority";
        MongoClient client = Utils.getMongoClient(mongodbURI, "com.app.model");
        ClientSession clientSession = client.startSession();

        try {
            TransactionBody<String> txnBody = new MyTransactionBodyWithError("testtransactions", client, clientSession);
            TransactionOptions txnOptions = Utils.getTransactionOptions();
            clientSession.startTransaction(txnOptions);
            String res = txnBody.execute();
            clientSession.commitTransaction();
            System.out.println("Res=" + res);
        } catch (RuntimeException e) {
            e.printStackTrace();
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }

    }

}
