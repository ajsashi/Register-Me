package com.register.me.model.data.model;

import java.util.List;

/**
 * Created by Jennifer - AIT on 14-02-2020AM 11:06.
 */
public class PersInfo {

    private final String apiKey;
    String question;
    String answer;
    /*
     * 1 - EditText
     * 2 - RadioButton
     * 3 - Spinner
     * 4 - RadioRadio
     * 5 - RadioSpinner
     */
    int viewType;
    /*
     * 1 - text
     * 2 - email
     * 3- password
     * 4 - number
     * */
    private final int inputType;
    /*
     * 1 - action_next;
     * 2 - action_done
     * 2 - password
     * */
    private final int action;

    private PersInfo subQA;

    private List<String> subList = null;
    private boolean isMandatory;

    public PersInfo(String question, String answer, int type, int inputType, int action, String apiKey, PersInfo subQA, List<String> subList,boolean isMandatory) {
        this.question = question;
        this.answer = answer;
        this.viewType = type;
        this.inputType = inputType;
        this.action = action;
        this.apiKey = apiKey;
        this.subQA = subQA;
        this.subList = subList;
        this.isMandatory = isMandatory;
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getInputType() {
        return inputType;
    }

    public int getAction() {
        return action;
    }

    public String getApiKey() {
        return apiKey;
    }

    public PersInfo getSubQA() {
        return subQA;
    }

    public void setSubQA(PersInfo subQA) {
        this.subQA = subQA;
    }

    public List<String> getSubList() {
        return subList;
    }

    public void setSubList(List<String> subList) {
        this.subList = subList;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }
}
