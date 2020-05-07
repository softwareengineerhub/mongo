package com.app.transactions;

import com.mongodb.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Utils {


    public static TransactionOptions getTransactionOptions(){
        TransactionOptions txnOptions = TransactionOptions.builder()
                .readPreference(ReadPreference.primary())
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .build();
        return txnOptions;
    }

    public static CodecRegistry getCodecRegistry(String modelPackage){
        getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        //PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("com.mongodb.models").build();
        //PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("com.app.model").build();
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(modelPackage).build();
        CodecRegistry codecRegistryA = fromRegistries(MongoClient.getDefaultCodecRegistry());
        CodecRegistry codecRegistryB = fromProviders(pojoCodecProvider);
        CodecRegistry codecRegistry = fromRegistries(codecRegistryA, codecRegistryB);
        return codecRegistry;
    }


    public static MongoClient getMongoClient(String serverUrl, String modelPackage){
        CodecRegistry codecRegistry = getCodecRegistry(modelPackage);
        MongoClientOptions.Builder options = new MongoClientOptions.Builder().codecRegistry(codecRegistry);

        String mongodbURI = "mongodb+srv://user:userpwd@cluster0-daeju.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI uri = new MongoClientURI(mongodbURI, options);
        return new MongoClient(uri);
    }
}
