/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jsonpersistence;

import com.mycompany.jsonpersistence.model.ModelGenerator;
import com.mycompany.jsonpersistence.model.PersonList;
import com.mycompany.jsonpersistence.model.mainUtil;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;

/**
 *
 * @author kenna
 */
public class JsonPersistence {

    private static final Compression compression = new Compression();
    
    public static void main(String[] args) {
        
        // Run initial checks
        mainUtil.modelCollectionToJson();
        mainUtil.compressModelCollection();
        
        /**
         * Compress a map of PersonList to a file
         */
        
        // Create a "database"
        System.out.println("\n\nCompressing a Mock Database:");
        Map<String, PersonList> database = new HashMap<>();
        for ( int i = 0; i < 3; i ++) {
            String tableName = "table-" + (i + 1);
            PersonList table = ModelGenerator.makePersonList(10);
            database.put(tableName, table);
        }
        
        
        // Try compress the database
        System.out.println("\nCompressing database");
        Map<String, JSONArray> jsonDb;
        Map<String, byte[]> compressedDB = null;
        try {
            
            // Compress
            jsonDb = mainUtil.personDbToJson(database);
            compressedDB = compression.compressDB(jsonDb);
            
            // Scope out
            for (Entry<String, byte[] > i : compressedDB.entrySet()) {
                System.out.println("Table '" + i.getKey() + "' Compressed Size:\t" + i.getValue().length);
            }
        }
        catch ( Exception ex ) {
            System.out.println("\nError compressing database");
        }
        
        
        // Try zip data
        String dbOut = "personDB.out.zip";
        boolean zipState = false;
        if ( compressedDB != null ) {
            
            // Zip
            System.out.println("\nZipping Database to file");
            zipState = compression.zipDatabase(compressedDB, dbOut);
            System.out.println("Zip state:\t" + zipState);
        }
        else {
            System.out.println("\nSkipping zip as compression failed");
        }

        
        // Try unzip the data
        if ( zipState ) {
            
            // Log and initialize vars
            System.out.println("\nAttempting to unzip database:\t" + dbOut);
            Map<String, byte[]> dbDecompressed;
            Map<String, JSONArray> dbDecompressedJson;
            Map<String, PersonList> dbRebuild;
            
            // As compressed
            dbDecompressed = compression.unzipDatabase(dbOut);
            boolean decompState = dbDecompressed != null;
            System.out.println("Unzip state:\t" + decompState);
            
            // As json
            dbDecompressedJson = compression.unzipToJson(dbOut);
            decompState = dbDecompressedJson != null;
            System.out.println("Decompression to Json state:\t" + decompState);
            
            // As DB
            dbRebuild = mainUtil.jsonDbToPersonList(dbDecompressedJson);
            decompState = dbRebuild != null;
            System.out.println("Rebuild to PersonList DB State: \t" + decompState);
            
            // Print if valid
            if ( decompState ) {
                for ( Entry<String, PersonList> table : dbRebuild.entrySet() ) {
                    System.out.println("\n\nDisplaying data from decompressed table:\t" + table.getKey());
                    System.out.println(table.getValue().toJson().toString(4));
                }
            }
        }
        else {
            System.out.println("\nSkipping unzip as zip failed");
        }
    }
}
