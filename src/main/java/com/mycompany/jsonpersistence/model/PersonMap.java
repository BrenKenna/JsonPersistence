/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kenna
 */
public class PersonMap {
    
    private Map<Integer, Person> personMap;
    
    public PersonMap() {
        this.personMap = new HashMap<>();
    }
    
    public PersonMap(Map<Integer, Person> data) {
        this.personMap = data;
    }
    
    
    public boolean addPerson(Person record) {
        if ( personMap.containsKey(record.getId()) ) {
            return false;
        }
        personMap.put(record.getId(), record);
        return true;
    }
    
    
    public boolean dropPerson(Person record) {
        if ( !personMap.containsKey(record.getId()) ) {
            return false;
        }
        personMap.remove(record.getId());
        return true;
    }
    
    public boolean dropPerson(int personId) {
        if ( !personMap.containsKey(personId) ) {
            return false;
        }
        personMap.remove(personId);
        return true;
    }
    
    
    public boolean containsPerson(Person record) {
        return personMap.containsKey(record.getId());
    }
    
    public boolean containsPerson(int personId) {
        return personMap.containsKey(personId);
    }
    
    public Map<Integer, Person> getPersonMap() {
        return this.personMap;
    }
}
