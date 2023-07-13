/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jsonpersistence;

import Model.*;
import appLogging.AppLogging;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

/**
 *
 * @author kenna
 */
public class JsonPersistence {

    private static final Logger appLogger = AppLogging.logger;
    
    
    public static void main(String[] args) {
        
        // Make PersonList and Map
        int nPerson = 30;
        appLogger.info("Creating Person Collection Objects of size:\nN=" + nPerson);
        PersonList personList = ModelGenerator.makePersonList(nPerson);
        PersonMap personMap = ModelGenerator.makePersonMap(nPerson);
        
        
        // Convert PersonList to JSONArray
        appLogger.info("Converting PersonList to JSONArr");
        JSONArray jsonArr = personList.toJson();
        appLogger.info(jsonArr.toString(4));
    }
}
