package com.app.transactions;

import com.app.model.Cart;
import com.app.model.Product;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.TransactionBody;

public class MyTransactionBodyWithError implements TransactionBody<String> {

    private String globalName;
    private MongoClient client;
    private ClientSession clientSession;

    public MyTransactionBodyWithError(String globalName, MongoClient client, ClientSession clientSession) {
        this.globalName = globalName;
        this.client = client;
        this.clientSession = clientSession;
    }

    @Override
    public String execute() {
        MongoDatabase db = client.getDatabase(globalName);

        MongoCollection<Cart> coll1 = db.getCollection("cart", Cart.class);
        MongoCollection<Product> coll2 = db.getCollection("product", Product.class);

        Cart cart2 = new Cart();
        cart2.setName("Name2");
        cart2.setValue(2);

        Product product2 = new Product();
        product2.setTitle("Title2");
        product2.setAmount(2);


        coll1.insertOne(clientSession, cart2);
        coll2.insertOne(clientSession, product2);

        String s = null;
        s.toString();

        return "Inserted into collections in different databases";
    }
}
