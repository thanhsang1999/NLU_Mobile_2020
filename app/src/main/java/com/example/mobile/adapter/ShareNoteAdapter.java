package com.example.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.R;
import com.example.mobile.model.InfoShare;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShareNoteAdapter extends RecyclerView.Adapter<ShareNoteAdapter.ViewHolder> {
    Context context;
    List<InfoShare> infoShares;

    public ShareNoteAdapter(Context context, List<InfoShare> infoShares) {
        this.context = context;
        this.infoShares = infoShares;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_share,parent,false);
        return new ShareNoteAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShareNoteAdapter.ViewHolder holder, int position) {
        InfoShare infoShare = infoShares.get(position);
        if (position==0){
            holder.textViewName.setText(infoShare.getName());
            holder.textViewEmail.setText(infoShare.getEmail());
            holder.imageShareCancel.setImageDrawable(null);
            holder.imageShareCancel.setEnabled(false);
        }else {
            holder.textViewName.setText(infoShare.getName());
            holder.textViewEmail.setText(infoShare.getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return infoShares.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewEmail;
        ImageView imageShareCancel;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            imageShareCancel = itemView.findViewById(R.id.imageShareCancel);
            imageShareCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+textViewName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public List<InfoShare> getInfoShares() {
        return infoShares;
    }
}
