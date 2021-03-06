package com.example.mobile.ui.slideshow;


import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
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
import com.example.mobile.activity.NewNoteActivity;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;

import java.util.List;

public class AdapterNoteFragmentRecyclerView extends RecyclerView.Adapter<AdapterNoteFragmentRecyclerView.ViewHolder> implements Parcelable {


    List<Notebook> notebooks;
    NoteFragment context;
    Boolean multiSelect = false;
    Package currentPackage;
    public AdapterNoteFragmentRecyclerView(Package p, NoteFragment context) {
        currentPackage= p;
        this.notebooks = p.getNotebooks();
        this.context = context;
    }



    protected AdapterNoteFragmentRecyclerView(Parcel in) {
        byte tmpMultiSelect = in.readByte();
        multiSelect = tmpMultiSelect == 0 ? null : tmpMultiSelect == 1;
        currentPackage = in.readParcelable(Package.class.getClassLoader());
    }

    public static final Creator<AdapterNoteFragmentRecyclerView> CREATOR = new Creator<AdapterNoteFragmentRecyclerView>() {
        @Override
        public AdapterNoteFragmentRecyclerView createFromParcel(Parcel in) {
            return new AdapterNoteFragmentRecyclerView(in);
        }

        @Override
        public AdapterNoteFragmentRecyclerView[] newArray(int size) {
            return new AdapterNoteFragmentRecyclerView[size];
        }
    };

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_view_home,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        Notebook notebook = notebooks.get(position);
        String stringColorPackage = currentPackage.getColor();
        if (notebook.getChecked()&&multiSelect){
            holder.linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
            holder.imageViewCheck.setImageResource(R.drawable.ic_checked_list);
        }else {
            holder.imageViewCheck.setImageResource(Tool.getDrawableByName(context.getContext(),"ic_"+stringColorPackage));
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (multiSelect == null ? 0 : multiSelect ? 1 : 2));
        dest.writeParcelable(currentPackage, flags);
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
                        linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                        linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                        imageViewCheck.setImageResource(R.drawable.ic_checked_list);

                        Activity activity= context.getActivity();
                        if(activity instanceof  HomeActivity){
                            HomeActivity homeActivity =  (HomeActivity) activity;
                            homeActivity.showActionMode();
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
                                notebook.setChecked(false);
                                String stringColorPackage = currentPackage.getColor();
                                linearLayoutMain.setBackgroundResource(R.drawable.background_list_item_linear);
                                linearLayoutContent.setBackgroundResource(R.drawable.background_list_item_linear);
                                imageViewCheck.setImageResource(Tool.getDrawableByName(context.getContext(),"ic_"+stringColorPackage));
                            }else{
                                notebook.setChecked(true);
                                linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                                linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                                imageViewCheck.setImageResource(R.drawable.ic_checked_list);
                            }
                        }
                    }else {
                        multiSelect = false;
                        AdapterNoteFragmentRecyclerView.this.context.startActivityNewNote(AdapterNoteFragmentRecyclerView.this.context.getActivity(),notebook.getId(), getAdapterPosition());
                            Toast.makeText(context.getContext(), "select "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
