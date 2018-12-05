package com.color_wave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class ThemeAdapter extends ArrayAdapter {
    Context themeContext;
    List<String> themeList;
    private int selectedPosition;

    public ThemeAdapter(Context context, ArrayList<String> list){
        super(context, R.layout.button_theme_selector, list);
        themeContext = context;
        themeList = list;
    }

    private class ViewHolder {
        private String value;
        private RadioButton radioButton;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(themeContext).inflate(R.layout.button_theme_selector, parent, false);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.theme_button);
            viewHolder.value = themeList.get(position);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String theme = themeList.get(position);
        viewHolder.radioButton.setText(theme);
        viewHolder.radioButton.setButtonDrawable(android.R.color.transparent);

        viewHolder.radioButton.setChecked(position == selectedPosition);
        viewHolder.radioButton.setTag(position);
        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
            }
        });
        return view;
    }

    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }

    public String getSelectedItem() {
        return selectedPosition != -1 ? themeList.get(selectedPosition) : "";
    }
}
