package com.example.tychala_panagiota_18162_androidjan21;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CovidAdapter extends ArrayAdapter<CovidItem> {

    private List<CovidItem> dataset;
    private final LayoutInflater inflater;
    private final int layoutResource;


    public CovidAdapter(@NonNull Context context, int resource, @NonNull List<CovidItem> objects) {
        super(context, resource, objects);
        dataset = objects;
        inflater = LayoutInflater.from(context);
        layoutResource = resource;
    }

    static class ViewHolder {

        public TextView areaTextView;
        public TextView daydiffTextView;
        public TextView daytotalTextView;
        public TextView totalvaccinationsTextView;

        public ViewHolder(View itemView) {
            areaTextView = itemView.findViewById(R.id.area);
            daydiffTextView = itemView.findViewById(R.id.daydiff);
            daytotalTextView = itemView.findViewById(R.id.daytotal);
            totalvaccinationsTextView = itemView.findViewById(R.id.totalvaccinations);
        }
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        CovidItem item = dataset.get(position);
        holder.areaTextView.setText(item.getArea());
        holder.daydiffTextView.setText(item.getDaydiff() + "");
        if (item.getDaydiff()>0)
            holder.daydiffTextView.setTextColor(Color.GREEN);
        else
            holder.daydiffTextView.setTextColor(Color.RED);
        holder.daytotalTextView.setText(item.getDaytotal() + "");
        holder.totalvaccinationsTextView.setText(item.getTotalvaccinations() + "");
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        holder.totalvaccinationsTextView.setTypeface(boldTypeface);
        return convertView;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    public void setCovidEntries(@NonNull List<CovidItem> list) {
        dataset = list;
        notifyDataSetChanged();
    }

}
