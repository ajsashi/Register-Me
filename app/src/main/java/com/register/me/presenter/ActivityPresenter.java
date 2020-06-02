package com.register.me.presenter;

import android.content.Context;

import com.register.me.model.data.Constants;
import com.register.me.model.data.model.RREApplication;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class ActivityPresenter {
    Context context;
    @Inject
    Constants constants;
    public void init(Context context){
      this.context = context;
        ((BaseActivity)context).injector().inject(this);
    }

   public List<RREApplication.Comment> getComment(){
        return constants.getApplicationData().getData().getComments();
   }

}
