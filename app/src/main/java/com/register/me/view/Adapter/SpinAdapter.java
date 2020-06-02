package com.register.me.view.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.model.CurrencyCode;
import com.register.me.model.data.model.SuccessCertificate;

import java.util.List;

/**
 * Created by Jennifer - AIT on 10-03-2020PM 12:54.
 */
public class SpinAdapter<T> extends ArrayAdapter<T> {

    private final Object obj;
    private Context context;
    private List<T> values;

    public SpinAdapter(Context context, int textViewResourceId, List<T> values, Object object) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        this.obj =object;
    }

    public int getCount() {
        return values.size();
    }

    public T getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        String text = "";
        if (obj instanceof CrreList) {
            CrreList.Expertlist objects = (CrreList.Expertlist) values.get(position);
            text = objects.getExpertName();
        }else if(obj instanceof CurrencyCode.Currencydetail){
            CurrencyCode.Currencydetail obj = (CurrencyCode.Currencydetail) values.get(position);
            text = obj.getCurrencycode();
        }else if(obj instanceof SuccessCertificate.Uncertifiedregion){
            SuccessCertificate.Uncertifiedregion Obj = (SuccessCertificate.Uncertifiedregion) values.get(position);
            text = Obj.getUncertifiedregion();
        }
        label.setText(text);
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);

        label.setTextColor(Color.BLACK);
        DisplayMetrics metrics = parent.getResources().getDisplayMetrics();
        float dp = 40f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        label.setHeight(pixels);

        label.setPadding(25,0,0,0);
        String text = "";
        if (obj instanceof CrreList) {
            CrreList.Expertlist objects = (CrreList.Expertlist) values.get(position);
            text = "   "+objects.getExpertName();
        }else if(obj instanceof CurrencyCode.Currencydetail){
            CurrencyCode.Currencydetail obj = (CurrencyCode.Currencydetail) values.get(position);
            text = obj.getCurrencycode();
        }else if(obj instanceof SuccessCertificate.Uncertifiedregion){
            SuccessCertificate.Uncertifiedregion Obj = (SuccessCertificate.Uncertifiedregion) values.get(position);
            text = Obj.getUncertifiedregion();
        }
        label.setText(text);

        return label;
    }
}
