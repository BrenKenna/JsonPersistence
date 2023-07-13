/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Map;
import java.util.Random;

/**
 *
 * @author kenna
 */
public class ModelGenerator {
    
    public ModelGenerator(){}
    
    private static final Random rand = new Random();
    
    
    /**
     * Return Person from input
     * 
     * @param fName
     * @param lName
     * @param mInitial
     * @param state
     * @param rank
     * @return - Person
     */
    public static Person makePerson(String fName, String lName, char mInitial, boolean state, double rank) {
        return new Person(fName, lName, mInitial, state, rank);
    }
    
    
    /**
     * Return random person
     * @return - Person
     */
    public static Person makeRandomPerson() {
        Map<String, String> name = NameGenerator.getRandomData();
        double rank = rand.nextDouble(9.9) + 1.1;
        char mInitial = name.get("Last Name").charAt(0);
        boolean state = rand.nextBoolean();
        return makePerson(name.get("First Name"), name.get("Last Name"), mInitial, state, rank);
    }
    
    
    /**
     * Make a random person list of the required size
     * 
     * @param nPerson
     * @return PersonList
     */
    public static PersonList makePersonList(int nPerson) {
        PersonList output = new PersonList();
        for ( int i = 0; i < nPerson; i++ ) {
            Person record = makeRandomPerson();
            output.addPerson(record);
        }
        return output;
    }
    
    
    /**
     * Make a random PersonMap of the required size
     * 
     * @param nPerson
     * @return PersonMap
     */
    public static PersonMap makePersonMap(int nPerson) {
        PersonMap output = new PersonMap();
        for ( int i = 0; i < nPerson; i++ ) {
            Person record = makeRandomPerson();
            output.addPerson(record);
        }
        return output;
    }
}
