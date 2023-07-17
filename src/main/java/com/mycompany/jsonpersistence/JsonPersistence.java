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
        
        
        // Compress database
        Map<String, byte[]> compressedDB = new HashMap<>();
        for (Entry<String, PersonList> i : database.entrySet()) {
            try {
                
                // Compress table and add to compressed version
                JSONArray jsonArr = i.getValue().toJson();
                byte[] tableCompressed = compression.compress(jsonArr.toString());
                compressedDB.put(i.getKey(), tableCompressed);
            }
            catch ( Exception ex ) {
                System.out.println("\nError compressing table:\t" + i.getKey() + "\n" + ex );
            }
        }
        
        // Scope out
        for (Entry< String, byte[] > i : compressedDB.entrySet()) {
            System.out.println("Table '" + i.getKey() + "' Compressed Size:\t" + i.getValue().length);
        }
        
        
        // Initialize required vars
        String dbOut = "personDB.out.zip";
        FileOutputStream fileOut;
        ZipOutputStream zipOut;
        
        // Compress
        System.out.println("\nZipping DB to file:\t" + dbOut);
        try {
            
            // Initialize stream handlers
            System.out.println("Initializing output file handlers");
            fileOut = new FileOutputStream(dbOut);
            zipOut = new ZipOutputStream( new BufferedOutputStream(fileOut));
            
            // Add each compressed table
            System.out.println("Adding each table to output zip");
            for (Entry<String, byte[]> table: compressedDB.entrySet() ) {
                
                // Create a zip entry for active table
                ZipEntry zipElm = new ZipEntry(table.getKey());
                System.out.println("Processing table:\t" + zipElm.getName());
                
                // Add element to zip
                zipElm.setSize(table.getValue().length);
                zipOut.putNextEntry(zipElm);
                zipOut.write(table.getValue());
                zipOut.flush();
                zipOut.closeEntry();
            }
            
            // Close output zip stream
            System.out.println("\nCompression complete, closing output zip handler");
            zipOut.close();
        }
        catch (Exception ex) {
            System.out.println("\nError zipping DB to file:\n" + ex);
        }
        
        
        /**
         * Unzip
        */
        
        // Initialize vars
        System.out.println("\nUnzipping:\t" + dbOut);
        Map<String, byte[]> decompressedDB = new HashMap();
        Map<String, PersonList> decompressedData = new HashMap();
        try {
            
            // Rebuild compressed DB
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(dbOut));
            ZipEntry zipElm;
            while ( ( zipElm = zipIn.getNextEntry() ) != null ) {
                
                // Fetch data
                String tableName = zipElm.getName();
                byte[] tableData = zipIn.readAllBytes();
                System.out.println("\nFetched table '" + tableName + "' from zip:\tSize = " + tableData.length);
                
                // Add to compressed holder
                System.out.println("Adding record to the compressed table holder");
                decompressedDB.put(tableName, tableData);
                
                // Decompress to JSON
                System.out.println("Decompressing data");
                JSONArray decompData = new JSONArray(compression.decompress(tableData));
                System.out.println(decompData.toString(4));
                
                // Convert back into table
                System.out.println("Converting JSON Array back into PersonList");
                PersonList decompressed = new PersonList(decompData);
                decompressedData.put(tableName, decompressed);
                System.out.println("Records added back in = " + decompressed.getPersonList().size());
            }
            
            // Close input stream handlers
            zipIn.closeEntry();
            zipIn.close();
        }
        catch ( Exception ex) {
            System.out.println("\nError unzipping database:\n\n" + ex);
        }
    }
}
