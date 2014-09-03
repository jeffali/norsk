package com.norsk3.entities;

/**
 * Defines a basic couple of (question, answer)
 * @author jeffali
 */
public class Response extends Question {
    private String answer;

    public Response(Long id, String question, String answer) {
        super(id, question);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
