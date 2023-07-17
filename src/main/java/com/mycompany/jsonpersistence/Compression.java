/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsonpersistence;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;


/**
 *
 * @author kenna
 */
public class Compression {
    
    public Compression(){}
    
    /**
     * GZIP input string
     * 
     * @param input
     * @return - Compress string
     * @throws IOException 
     */
    public byte[] compress(String input) throws IOException {
        
        // Initialize vars
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(byteArr);
        
        // Compress input to UTF-8
        gzip.write(input.getBytes("UTF-8"));
        gzip.close();
        return byteArr.toByteArray();
    }
    
    
    /**
     * Compress a database of JSON Arrays
     * 
     * @param input
     * @return
     * @throws IOException 
     */
    public Map<String, byte[]> compressDB(Map<String, JSONArray> input) throws IOException {
        Map<String, byte[]> output = new HashMap();
        for ( Entry<String, JSONArray> i : input.entrySet() ) {
            byte[] compressedTable = compress(i.getValue().toString());
            output.put(i.getKey(), compressedTable);
        }
        return output;
    }
    
    
    /**
     * Zip the compressed database to target file
     * 
     * @param database
     * @param targetFile
     * @return 
     */
    public boolean zipDatabase(Map<String, byte[]> database, String targetFile) {
        
        // Initialize key vars
        boolean zipState = true;
        FileOutputStream fileOut;
        ZipOutputStream zipOut;
        
        // Compress database to target feil
        try {
            
            // Set stream handlers
            fileOut = new FileOutputStream(targetFile);
            zipOut = new ZipOutputStream( new BufferedOutputStream(fileOut));
            for (Entry<String, byte[]> table: database.entrySet() ) {
                
                // Create a zip entry for active table
                ZipEntry zipElm = new ZipEntry(table.getKey());
                zipElm.setSize(table.getValue().length);
                zipOut.putNextEntry(zipElm);
                zipOut.write(table.getValue());
                zipOut.flush();
                zipOut.closeEntry();
            }
            
            // Close stream handler
            zipOut.closeEntry();
            zipOut.close();
            fileOut.close();
        } 
        catch ( Exception ex ) {
            zipState = false;
        }
        
        return zipState;
    }
    
    /**
     * Decompress input string
     * 
     * @param compressedString
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public String decompress(byte[] compressedString) throws UnsupportedEncodingException, IOException {
        
        // Decompress & initialize readers
        GZIPInputStream gzipInpStream = new GZIPInputStream(new ByteArrayInputStream(compressedString));
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(gzipInpStream, "UTF-8"));
        
        
        // Read into output string
        String output = "";
        String line;
        while ( (line = buffRead.readLine()) != null ) {
            output += line;
        }
        return output;
    }
    
    
    /**
     * Unzip database, providing the compressed byte arrays
     * 
     * @param databaseFile
     * @return 
     */
    public Map<String, byte[]> unzipDatabase(String databaseFile) {
    
        // Initialize vars
        Map<String, byte[]> output = new HashMap();
        ZipInputStream zipIn;
        FileInputStream fileIn;
        ZipEntry zipElm;
        
        // Try decompress database
        try {
            
            // Try create file handlers
            fileIn = new FileInputStream(databaseFile);
            zipIn = new ZipInputStream(fileIn);
            
            // Fetch zip elements
            while ( (zipElm = zipIn.getNextEntry()) != null ) {
            
                // Fetch data
                String tableName = zipElm.getName();
                byte[] tableData = zipIn.readAllBytes();
                
                // Add data
                output.put(tableName, tableData);
            }
            
            // Close handlers
            fileIn.close();
            zipIn.closeEntry();
            zipIn.close();
        }
        catch (Exception ex) {
            output = null;
        }
        
        // Return output
        return output;
    }
    
    
    /**
     * Unzip database file to JSON Array DB
     * 
     * @param databaseFile
     * @return 
     */
    public Map<String, JSONArray> unzipToJson(String databaseFile) {
    
        // Initialize vars
        Map<String, JSONArray> output = new HashMap();
        ZipInputStream zipIn;
        FileInputStream fileIn;
        ZipEntry zipElm;
        
        // Try decompress database
        try {
            
            // Try create file handlers
            fileIn = new FileInputStream(databaseFile);
            zipIn = new ZipInputStream(fileIn);
            
            // Fetch zip elements
            while ( (zipElm = zipIn.getNextEntry()) != null ) {
            
                // Fetch data
                String tableName = zipElm.getName();
                byte[] tableCompressedData = zipIn.readAllBytes();
                
                // Decompress
                JSONArray tableData = new JSONArray(decompress(tableCompressedData));
                
                // Add data
                output.put(tableName, tableData);
            }
            
            // Close handlers
            fileIn.close();
            zipIn.closeEntry();
            zipIn.close();
        }
        catch (Exception ex) {
            output = null;
        }
        
        // Return output
        return output;
    }
}
