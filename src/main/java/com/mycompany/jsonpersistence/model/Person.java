/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsonpersistence.model;


import org.json.JSONObject;

/**
 *
 * @author kenna
 */
public class Person {
    
    // ID
    private static int currentId = -1;
    private int id;
    
    // String
    private String fName, lName;

    // Char
    private char mInitial;
    
    // Boolean
    private boolean active;
    
    // Double
    private double rank;
    
    
    /**
     * Default constructor
     */
    public Person() {
        this.id = getNextId();
    }
    
    
    /**
     * Construct with data
     * 
     * @param fName
     * @param lName
     * @param mInitial
     * @param active
     * @param rank 
     */
    public Person(String fName, String lName, char mInitial, boolean active, double rank) {
        this.id = getNextId();
        this.fName = fName;
        this.lName = lName;
        this.mInitial = mInitial;
        this.active = active;
        this.rank = rank;
    }
    
    
    /**
     * Construct from person
     * 
     * @param input 
     */
    public Person(JSONObject input) {
        this.id = (int) input.get("Id");
        this.fName = (String) input.get("First Name");
        this.lName = (String) input.get("Last Name");
        this.mInitial = input.get("Middle Initial").toString().charAt(0);
        this.active = (boolean) input.get("Active");
        this.rank = Double.parseDouble(input.get("Rank").toString());
        getNextId();
    }
    
    
    /**
     * Convert to JSON Object
     * @return 
     */
    public JSONObject toJson() {
        JSONObject output = new JSONObject();
        output.put("Id", this.id);
        output.put("First Name", this.fName);
        output.put("Last Name", this.lName);
        output.put("Middle Initial", String.valueOf(this.mInitial));
        output.put("Active", this.active);
        output.put("Rank", this.rank);
        return output;
    }
    
    /**
     * Auto increment Ids
     * 
     * @return Id 
     */
    private static int getNextId() {
        currentId++;
        return currentId;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public char getmInitial() {
        return mInitial;
    }

    public void setmInitial(char mInitial) {
        this.mInitial = mInitial;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", fName=" + fName
            + ", lName=" + lName
            + ", mInitial=" + mInitial
            + ", active=" + active
            + ", rank=" + rank
        + '}';
    }
}
