/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jsonpersistence;

import com.mycompany.jsonpersistence.model.ModelGenerator;
import com.mycompany.jsonpersistence.model.PersonMap;
import com.mycompany.jsonpersistence.model.PersonList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author kenna
 */
public class JsonPersistence {

    private static final Logger appLogger = LogManager.getLogger(JsonPersistence.class);
    private static final Compression compression = new Compression();
    
    public static void main(String[] args) {
        
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
}
