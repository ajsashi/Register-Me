package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.register.me.R;

/**
 * Created by Jennifer - AIT on 20-02-2020PM 01:33.
 */
public class MySpinnerAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] listState;
    private MySpinnerAdapter myAdapter;
    private boolean isFromView = false;

    public MySpinnerAdapter(Context context, int resource, String[] array, int position) {
        super(context, resource, array);
        this.mContext = context;
        this.listState = array;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator;
            layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.simple_spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(listState[position]);

       /* // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;*/

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.GONE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int getPosition = (Integer) buttonView.getTag();

            Toast.makeText(mContext,holder.mTextView.getText(), Toast.LENGTH_SHORT).show();

           /* if (!isFromView) {
                listState.get(position).setSelected(isChecked);
            }*/
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    public interface SpinnerListener{
        void onTextSelected();
        void onTextUnselected();
    }
}
