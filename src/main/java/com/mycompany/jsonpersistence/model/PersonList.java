/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsonpersistence.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author kenna
 */
public class PersonList {
    
    private List<Person> personList;
    
    public PersonList() {
        this.personList = new ArrayList<>();
    }
    
    public PersonList(List<Person> data) {
        this.personList = data;
    }
    
    
    /**
     * Construct PersonList from JSONArray
     * 
     * @param data 
     */
    public PersonList(JSONArray data) {
        this.personList = new ArrayList<>();
        while ( data.toList().size() > 0 ) {
            JSONObject elm = (JSONObject) data.remove(0);
            this.personList.add( new Person(elm));
        }
    }
    
    
    public void addPerson(Person record) {
        this.personList.add(record);
    }
    
    public boolean remove(Person record) {
        return this.personList.remove(record);
    }
    
    public List<Person> getPersonList() {
        return this.personList;
    }
    
    
    public Person getPersonAt(int ind) {
        if ( ind < 0 || ind > personList.size() -1 ) {
            return null;
        }
        return personList.get(ind);
    }
    
    /**
     * Convert person list to JSON, order does not matter because
     *  each Person already has Id set. So process can be parallelized
     * @return 
     */
    public JSONArray toJson() {
        List<JSONObject> jsonList = personList.stream()
            .parallel()
            .map( Person::toJson )
            .toList()
        ;
        return new JSONArray(jsonList);
    }
}
