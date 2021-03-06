package com.color_wave;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThemeAdapter extends ArrayAdapter {
    private Context themeContext;
    private List<ColorTheme> themeList;
    private int selectedPosition;
    Drawable image;

    ThemeAdapter(Context context, ArrayList<ColorTheme> list){
        super(context, R.layout.button_theme_selector, list);
        themeContext = context;
        themeList = list;
    }

    private class ViewHolder {
        private String queryStr;
        private String labelStr;
        private RadioButton radioButton;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(themeContext).inflate(R.layout.button_theme_selector, parent, false);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.theme_button);
            viewHolder.queryStr = themeList.get(position).getQuery();
            viewHolder.labelStr = themeList.get(position).getLabel();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(viewHolder.labelStr.equals("Shoes")) {
            image = ResourcesCompat.getDrawable(themeContext.getResources(), R.drawable.shoes, null);
        } else if (viewHolder.labelStr.equals("Handbags")){
            image = ResourcesCompat.getDrawable(themeContext.getResources(), R.drawable.handbags, null);
        } else if (viewHolder.labelStr.equals("Bracelets")){
            image = ResourcesCompat.getDrawable(themeContext.getResources(), R.drawable.bracelets, null);
        } else if (viewHolder.labelStr.equals("Dresses")){
            image = ResourcesCompat.getDrawable(themeContext.getResources(), R.drawable.dresses, null);
        } else if (viewHolder.labelStr.equals("Watches")){
            image = ResourcesCompat.getDrawable(themeContext.getResources(), R.drawable.watches, null);
        } else {
            image = ResourcesCompat.getDrawable(themeContext.getResources(), R.drawable.question_icon, null);
        }

        viewHolder.radioButton.setCompoundDrawablesWithIntrinsicBounds(null, image, null, null);
        viewHolder.radioButton.setText(viewHolder.labelStr);
        viewHolder.radioButton.setChecked(position == selectedPosition);
        viewHolder.radioButton.setMinHeight(image.getIntrinsicHeight());
        viewHolder.radioButton.refreshDrawableState();
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
        return selectedPosition != -1 ? themeList.get(selectedPosition).getQuery() : "";
    }
}

