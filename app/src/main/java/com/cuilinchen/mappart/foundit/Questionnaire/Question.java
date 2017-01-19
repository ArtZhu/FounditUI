package com.cuilinchen.mappart.foundit.Questionnaire;

import java.io.Serializable;

/**
 * Created by Haspire on 5/1/2016.
 */
public abstract class Question implements Serializable {
    private Question _this = this;
    private String question;
    private String tag;

    public Question(String _question, String _tag){
        question = _question;
        tag = _tag;
    }

    public String getQuestion(){
        return question;
    }
    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Question){
            return ((Question) o).getQuestion().equals(_this.getQuestion())
                && ((Question) o).getTag().equals(getTag());
        }
        return false;
    }
}
