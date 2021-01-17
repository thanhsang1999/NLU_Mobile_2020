package com.example.mobile.ui.receive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.R;
import com.example.mobile.model.Notebook;
import com.example.mobile.model.Tool;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterReceive extends RecyclerView.Adapter<AdapterReceive.ViewHolder> {
    ReceiveFragment context;
    List<Notebook> notebooks;

    public AdapterReceive(ReceiveFragment context, List<Notebook> notebooks) {
        this.context = context;
        this.notebooks = notebooks;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_view_home,parent,false);
        return new AdapterReceive.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterReceive.ViewHolder holder, int position) {
        Notebook notebook = notebooks.get(position);
            holder.imageViewCheck.setImageResource(R.drawable.ic_color_blue_deep);
            holder.linearLayoutMain.setBackgroundResource(R.drawable.background_list_item_linear);
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_list_item_linear);
            holder.textViewTitle.setText(notebook.getTitle());
            holder.textViewContent.setText(notebook.getContent());
            holder.textViewDateEdit.setText(Tool.DateToStringPrint(notebook.getDateEdit()));
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // qua chinh sua new note
                    Notebook notebook = notebooks.get(getAdapterPosition());
                    context.startActivitySeeNote(notebook);
                }
            });
        }
    }


    public List<Notebook> getNotebooks() {
        return notebooks;
    }
}
