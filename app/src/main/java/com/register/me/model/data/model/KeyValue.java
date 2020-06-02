package com.register.me.model.data.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jennifer - AIT on 12-02-2020AM 11:46.
 */
public class KeyValue {
    String Key;
    String Value;
    List<String> subList;

    public KeyValue(String key, String value,List<String> subList) {
        Key = key;
        Value = value;
        this.subList = subList;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public List<String> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<String> subList) {
        this.subList = subList;
    }
}
