package com.example.mobile.ui.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.R;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Package;
import com.example.mobile.model.Tool;
import java.util.List;

public class AdapterHomeRecyclerView extends RecyclerView.Adapter<AdapterHomeRecyclerView.ViewHolder> {


    List<Notebook> notebooks;
    Context context;
    Boolean multiSelect = false;
    Package currentPackage;
    public AdapterHomeRecyclerView(Package p, Context context) {
        currentPackage= p;
        this.notebooks = p.getNotebooks();
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
    public void onBindViewHolder(@NonNull  AdapterHomeRecyclerView.ViewHolder holder, int position) {
        Notebook notebook = notebooks.get(position);
        String stringColorPackage = currentPackage.getColor();
        if (notebook.getChecked()&&multiSelect){
            holder.linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
            holder.imageViewCheck.setImageResource(R.drawable.ic_checked_list);
        }else {
            holder.imageViewCheck.setImageResource(Tool.getDrawableByName(context,"ic_"+stringColorPackage));
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
                        linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                        linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                        imageViewCheck.setImageResource(R.drawable.ic_checked_list);
                        HomeActivity homeActivity =  (HomeActivity) context;
                        homeActivity.showActionMode();
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
                                imageViewCheck.setImageResource(Tool.getDrawableByName(context,"ic_"+stringColorPackage));
                            }else{
                                notebook.setChecked(true);
                                linearLayoutMain.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                                linearLayoutContent.setBackgroundResource(R.drawable.background_checked_list_item_linear);
                                imageViewCheck.setImageResource(R.drawable.ic_checked_list);
                            }
                        }
                    }else {
                        multiSelect = false;
                            Toast.makeText(context, "select "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
