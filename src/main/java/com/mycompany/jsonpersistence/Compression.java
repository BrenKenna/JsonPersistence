/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsonpersistence;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
}
