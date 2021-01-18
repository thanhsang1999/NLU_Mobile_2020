package com.example.mobile.ui.important;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.R;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;

import java.util.ArrayList;

public class AdapterImportant extends RecyclerView.Adapter<AdapterImportant.ViewHolder> {


    ArrayList<Notebook> notebooks;
    ImportantFragment context;
    public Boolean multiSelect = false;

    public AdapterImportant( ArrayList<Notebook> notebooks, ImportantFragment context) {

        this.notebooks = notebooks;
        this.context = context;
    }


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_view_home,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterImportant.ViewHolder holder, int position) {
        Notebook notebook = notebooks.get(position);
        String stringColorPackage = notebook.getColorPackage();
        if (notebook.getChecked()&&multiSelect){
            holder.linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
            holder.imageViewCheck.setImageResource(R.drawable.ic_checked_list);
        }else {
            holder.imageViewCheck.setImageResource(Tool.getDrawableByName(context.getContext(),"ic_"+stringColorPackage));
            holder.linearLayoutMain.setBackgroundResource(R.drawable.background_list_item_linear);
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_list_item_linear);
        }
        holder.textViewTitle.setText(notebook.getTitle());
        holder.textViewContent.setText(notebook.getContent());
        holder.textViewDateEdit.setText(Tool.DateToStringPrint(notebook.getDateEdit()));
        holder.textViewTitle.setText(notebook.getTitle());
    }

    @Override
    public int getItemCount() {
        return notebooks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCheck;
        TextView textViewTitle,textViewContent,textViewDateEdit;
        LinearLayout linearLayoutMain,linearLayoutContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCheck = itemView.findViewById(R.id.imageViewChecked);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewDateEdit = itemView.findViewById(R.id.textViewDateEdit);
            linearLayoutMain = itemView.findViewById(R.id.linearLayoutMain);
            linearLayoutContent = itemView.findViewById(R.id.linearLayoutContent);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Notebook notebook = notebooks.get(getAdapterPosition());
                    if (!notebook.getChecked()){
                        multiSelect=true;
                        notebook.setChecked(true);
                        // thay doi ngoai hinh
                        linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                        linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                        imageViewCheck.setImageResource(R.drawable.ic_checked_list);

                        //Show activity
                        Activity activity= context.getActivity();
                        if(activity instanceof  HomeActivity){
                            HomeActivity homeActivity =  (HomeActivity) activity;
                            homeActivity.showActionModeImportant();
                        }else{
                            Log.e("adapterhome","not acepti homeac");
                        }
                    }

                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notebook notebook = notebooks.get(getAdapterPosition());
                    if (Tool.FindCheckedInArrayList(notebooks)){
                        if (multiSelect){
                            if (notebook.getChecked()){
                                // trả về chưa check
                                notebook.setChecked(false);
                                // thay đổi ngoại hình
                                String stringColorPackage = notebook.getColorPackage();
                                linearLayoutMain.setBackgroundResource(R.drawable.background_list_item_linear);
                                linearLayoutContent.setBackgroundResource(R.drawable.background_list_item_linear);
                                imageViewCheck.setImageResource(Tool.getDrawableByName(context.getContext(),"ic_"+stringColorPackage));
                                if (!Tool.FindCheckedInArrayList(notebooks)){
                                    Activity activity= context.getActivity();
                                    if(activity instanceof  HomeActivity){
                                        HomeActivity homeActivity =  (HomeActivity) activity;
                                        homeActivity.actionMode.finish();
                                        multiSelect=!multiSelect;
                                    }else{
                                        Log.e("adapterhome","destroyActionMode");
                                    }
                                }
                            }else{
                                notebook.setChecked(true);
                                linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                                linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                                imageViewCheck.setImageResource(R.drawable.ic_checked_list);
                            }
                        }
                    }else {
                        multiSelect = false;
                        // qua chinh sua new note
                        AdapterImportant.this.context.startActivityNewNote(AdapterImportant.this.context.getActivity(),notebook.getId(), getAdapterPosition());

                        Toast.makeText(context.getContext(), "select "+getAdapterPosition()+"id"+notebook.getId(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context.getContext(), "select "+Tool.DateToStringHCM(notebook.getDateEdit()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
