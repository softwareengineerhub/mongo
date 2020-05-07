/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.transactions;

import com.mongodb.MongoClient;
import com.mongodb.TransactionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.TransactionBody;

/**
 *
 * @author Denys.Prokopiuk
 */
public class Main5Transactions2 {

    public static void main(String[] args) {
        String mongodbURI = "mongodb+srv://root:rootpassword@cluster0-daeju.mongodb.net/test?retryWrites=true&w=majority";
        MongoClient client = Utils.getMongoClient(mongodbURI, "com.app.model");
        ClientSession clientSession = client.startSession();

        try {
            TransactionBody<String> txnBody = new MyTransactionBody("testtransactions", client, clientSession);
            TransactionOptions txnOptions = Utils.getTransactionOptions();
            String res = clientSession.withTransaction(txnBody, txnOptions);
            System.out.println("Res=" + res);
        } catch (RuntimeException e) {
            e.printStackTrace();
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }

    }
    
}
