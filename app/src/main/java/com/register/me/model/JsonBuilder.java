package com.register.me.model;

import com.google.gson.JsonObject;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.QandA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:19.
 */
public class JsonBuilder {

    public JsonObject getUserSignUpJson(String email, String password, String role) {
        JsonObject object = new JsonObject();
        object.addProperty("email", email);
        object.addProperty("password", password);
        object.addProperty("role", role);
        return object;
    }

    public JsonObject getAvatarJson(String encodedValue) {
        JsonObject object = new JsonObject();
        object.addProperty("data", encodedValue);
        return object;
    }

    public JsonObject getUserUpdateJson(ArrayList<QandA> info) {
        JsonObject object = new JsonObject();
        for (QandA item : info) {
            String quest = item.getQuestion().toLowerCase().replace(" ", "");
            if (!quest.equals("email") && !quest.equals("notification")) {
                object.addProperty(quest, item.getAnswer());
            } else if (quest.equals("notification")) {
                object.addProperty("emailnotification", item.getAnswer());
            }
        }
        return object;
    }

    public JsonObject addProductJson(List<QandA> questList) {
        JsonObject object = new JsonObject();
        for (QandA item : questList) {
            if (item.getSubQA() == null) {
                setAnswer(object, item);
            }else {
                setAnswer(object, item.getSubQA());
                setAnswer(object, item);
            }
        }
        return object;
    }

    private void setAnswer(JsonObject object, QandA item) {
        String answer = item.getAnswer();
        if (answer.equals("true") || answer.equals("false")) {
            boolean boolConvert = Boolean.parseBoolean(answer);
            object.addProperty(item.getApiKey(), boolConvert);
        } else {
            object.addProperty(item.getApiKey(), answer);
        }
    }

    public JsonObject initiateBidding(ArrayList<KeyValue> map){
        JsonObject object = new JsonObject();
        for (KeyValue item : map){
            object.addProperty(item.getKey(),item.getValue());
        }
        return object;
    }

    public JsonObject uploadDocument(ArrayList<QandA> document){
        JsonObject object = new JsonObject();
        for(QandA item : document){
            object.addProperty(item.getQuestion(),item.getAnswer());
        }
        return object;
    }
}
