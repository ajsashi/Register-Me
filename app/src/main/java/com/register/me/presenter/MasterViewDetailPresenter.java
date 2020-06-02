package com.register.me.presenter;

import android.content.Context;

import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.UserInfo;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

public class MasterViewDetailPresenter {

    private Context context;

    public void init(Context context) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }

    public ArrayList<KeyValue> getUserDetails(UserInfo userInfo) {
        ArrayList<KeyValue> list = new ArrayList<>();
        UserInfo.User info = userInfo.getData().getUser();
        list.add(new KeyValue("First Name", info.getFirstName(), null));
         String lastName = info.getLastName();
        if (lastName.isEmpty()) {
            lastName = "-";
        }
        list.add(new KeyValue("Last Name", lastName !=null? lastName:"-", null));
        list.add(new KeyValue("E-Mail Address", info.getEmailAddress()!=null? info.getEmailAddress():"-", null));
        list.add(new KeyValue("Telephone", info.getTelephone()!=null? info.getTelephone():"-", null));
        if(info.getOrganization()!=null) {
            list.add(new KeyValue("Company Name", info.getOrganization().getCompanyName(), null));
            list.add(new KeyValue("Division", info.getOrganization().getDivision(), null));
            list.add(new KeyValue("Address", info.getOrganization().getAddress(), null));
            list.add(new KeyValue("City", info.getOrganization().getCity(), null));
            list.add(new KeyValue("State", info.getOrganization().getState(), null));
            list.add(new KeyValue("Postal Code", info.getOrganization().getPostalCode(), null));
            list.add(new KeyValue("Country", info.getOrganization().getCountry(), null));
        }
        return list;

    }

}
