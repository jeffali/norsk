package com.norsk3.entities;

/**
 * Defines a basic suggestion
 * A random entry from (a1 to a4) is the right answer to the included question
 * @author jeffali
 */
public class Permutation extends Question {
    private String a1;
    private String a2;
    private String a3;
    private String a4;

    public Permutation(Long id, String question) {
        super(id, question);
    }

    public Permutation(Long id, String question, 
            String a1, String a2, String a3, String a4) {
        super(id, question);
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
    }
        
    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    @Override
    public String toString() {
        return "Permutation{" + "id=" + id + ", a1=" + a1 + ", a2=" + a2 + ", a3=" + a3 + ", a4=" + a4 + '}';
    }
}
