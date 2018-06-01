package com.v_care.Beans;

/**
 * Created by lenovo on 31-Mar-17.
 */
public class Questions_bean
{
    String question_id;
    String question;
    String answer;
    int serialno;

    //creating Getter Setter for the above string variables
    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSerialno() {
        return serialno;
    }

    public void setSerialno(int serialno) {
        this.serialno = serialno;
    }
}
