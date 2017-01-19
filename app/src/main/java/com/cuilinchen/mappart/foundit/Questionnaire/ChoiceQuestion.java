package com.cuilinchen.mappart.foundit.Questionnaire;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haspire on 5/1/2016.
 */
public class ChoiceQuestion<T> extends Question {
    private List<T> choices = new ArrayList<>();
    private int answer;

    public ChoiceQuestion(String _question, String _tag) {
        super(_question, _tag);
    }

    public List<T> getAllChoices(){
        return choices;
    }

    public ChoiceQuestion<T> putChoice(T choice){
        choices.add(choice);
        return this;
    }

    public T getChoice(int i){
        return choices.get(i);
    }

    public void setChoices(int i){
        answer = i;
    }

    public T getAnswer(){
        return choices.get(answer);
    }


}
