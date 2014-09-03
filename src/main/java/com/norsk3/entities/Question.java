package com.norsk3.entities;

/**
 * Defines a basic question
 * @author jeffali
 */
public class Question {
    protected Long id;
    protected String question;

    public Question(Long id, String question) {
        this.id = id;
        this.question = question;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }
}
