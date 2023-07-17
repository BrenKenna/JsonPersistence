/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsonpersistence.model;

import com.mycompany.jsonpersistence.Compression;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author kenna
 */
public class mainUtil {
    
    private static final Compression compression = new Compression();
    
    
    /**
     * Display creation of model collection, and conversion to JSON
     */
    public static void modelCollectionToJson() {
        
        // Make PersonList and Map
        int nPerson = 15;
        //appLogger.info("Creating Person Collection Objects of size:\nN=" + nPerson);
        System.out.println("\nCreating Person Collection Objects of size:\nN=" + nPerson);
        PersonList personList = ModelGenerator.makePersonList(nPerson);
        PersonMap personMap = ModelGenerator.makePersonMap(nPerson);
        
        
        // Convert PersonList to JSONArray
        //appLogger.info("Converting PersonList to JSON Array");
        System.out.println("\n\nConverting PersonList to JSON Array");
        JSONArray jsonArr = personList.toJson();
        //appLogger.info(jsonArr.toString(4));
        System.out.println(jsonArr.toString(4));
        System.out.println(personList.getPersonAt(0));
        System.out.println( (personList.getPersonAt(0)).toJson() );
        
        
        // Convert PersonMap to JSON Object
        //appLogger.info("Converting PersonList to JSON Object");
        System.out.println("\n\nConverting PersonList to JSON Object");
        JSONObject jsonObj = personMap.toJson();
        System.out.println(jsonObj.toString(4));
    }
    
    
    /**
     * Display compression of model collection, decompression
     */
    public static void compressModelCollection() {
        
        int nPerson = 15;
        System.out.println("\nCreating Person Collection Objects of size:\nN=" + nPerson);
        PersonList personList = ModelGenerator.makePersonList(nPerson);
        JSONArray jsonArr = personList.toJson();
        
    
        // Compress & Decompress
        try {
            
            // Compress
            System.out.println("\n\nCompressing PersonList");
            byte[] compressedPersonList = compression.compress(jsonArr.toString());
            System.out.println(
                "Initial Length:\t\t\t" + jsonArr.toString().length() +
                "\nCompressed Length:\t\t" + compressedPersonList.length +
                "\nDisplaying Compressed String:\t" + compressedPersonList
            );
            
            
            // Decompress
            System.out.println("\n\nDecompressing compressed PersonList");
            String decompressedPersonList = compression.decompress(compressedPersonList);
            System.out.println(
               "Decompressed Length:\t\t\t" + decompressedPersonList.length() +
               "\nDisplaying Decompressed String:\n" + decompressedPersonList
            );
            
            
            // Rebuild PersonList
            System.out.println("\n\nRebuilding PersonList from Decompressed String:\n");
            JSONArray arr = new JSONArray(decompressedPersonList);
            System.out.println("Displaying JSON Array of Rebuild:\n" + arr.toString() + "\n\n\n");
            PersonList rebuild = new PersonList(arr);
            System.out.println(
               "Initial PersonList Size:\t" + personList.getPersonList().size() +
               "\nRebuild Size:\t\t" + rebuild.getPersonList().size() +
               "\nDisplaying Rebuilt PersonList:\n" + rebuild.toJson().toString(4)
            );
        }
        catch ( Exception ex ) {
            System.out.println("Error (de)compressing person list:\n" + ex);
        }
    }
    
    
    /**
     * Convert database to JSON
     * 
     * @param database
     * @return 
     */
    public static Map<String, JSONArray> personDbToJson(Map<String, PersonList> database) {
        Map<String, JSONArray> output = new HashMap();
        for ( Entry<String, PersonList> table : database.entrySet() ) {
            output.put(table.getKey(), table.getValue().toJson());
        }
        return output;
    }
    
    
    /**
     * Convert JSONArray database back into PersonList DB
     * 
     * @param database
     * @return 
     */
    public static Map<String, PersonList> jsonDbToPersonList(Map<String, JSONArray> database) {
        Map<String, PersonList> output = new HashMap();
        for ( Entry<String, JSONArray> dbElm : database.entrySet() ) {
            PersonList table = new PersonList(dbElm.getValue());
            output.put(dbElm.getKey(), table);
        }
        return output;
    }
}
