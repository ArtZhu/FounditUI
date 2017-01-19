package com.cuilinchen.mappart.foundit.Questionnaire;

/**
 * Created by Haspire on 5/1/2016.
 */
public class StringQuestion extends Question {
    private String answer;

    public StringQuestion(String _question, String _tag) {
        super(_question, _tag);
    }

    public void setAnswer(String _answer){
        answer = _answer;
    }

    public String getAnswer(){
        return answer;
    }
}
