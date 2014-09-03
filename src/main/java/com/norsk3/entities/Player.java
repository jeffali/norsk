package com.norsk3.entities;

/**
 * Defines a basic player
 * The number of current failures and the current successes is linked to a game round
 * @author jeffali
 */
public class Player {
    private String name;
    private int failures = 0;
    private int successes = 0;

    public Player(String name) {
        this.name = name;
    }

    public Player() {
        this.name = "AnonymousBastard";
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }
    
    public void incrSuccesses() {
        ++successes;
    }
    
    public void incrFailures() {
        ++failures;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
