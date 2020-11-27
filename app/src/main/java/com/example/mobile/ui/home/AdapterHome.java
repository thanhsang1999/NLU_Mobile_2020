package com.example.mobile.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile.HomeActivity;
import com.example.mobile.R;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;

import java.util.ArrayList;

import static com.example.mobile.R.*;

public class AdapterHome extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<Notebook> notebooks;

    public AdapterHome(Context context, int layout, ArrayList<Notebook> notebooks) {
        this.context = context;
        this.layout = layout;
        this.notebooks = notebooks;
    }

    @Override
    public int getCount() {
        return notebooks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageViewCheck,imageViewColorPackage;
        TextView textViewTitle,textViewContent,textViewDateEdit;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Notebook notebook = notebooks.get(position);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new ViewHolder();
            //anhxa
            holder.imageViewCheck = convertView.findViewById(id.imageViewChecked);
            holder.imageViewColorPackage = convertView.findViewById(id.imageViewColorPackage);
            holder.textViewTitle = convertView.findViewById(id.textViewTitle);
            holder.textViewContent = convertView.findViewById(id.textViewContent);
            holder.textViewDateEdit = convertView.findViewById(id.textViewDateEdit);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String stringColorPackage = HomeActivity.sqLite.getColorPackage(notebook.getIdPackage());
        holder.imageViewColorPackage.setImageResource(Tool.getDrawableByName(context,"ic_"+stringColorPackage));
        holder.textViewTitle.setText(notebook.getTitle());
        holder.textViewContent.setText(notebook.getContent());
        holder.textViewDateEdit.setText(notebook.getDateEdit().toString());
        holder.textViewTitle.setText(notebook.getTitle());
        return convertView;
    }


}
