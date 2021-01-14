package com.example.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile.R;
import com.example.mobile.model.Tool;

import java.util.List;

public class NewNoteAdapter extends RecyclerView.Adapter<NewNoteAdapter.ViewHolder>{
    List<byte[]> images;
    Context context;

    public NewNoteAdapter(Context context,List<byte[]> images) {
        this.context = context;
        this.images=images;
    }

    @NonNull
    @Override
    public NewNoteAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_image_note,parent,false);
        return new NewNoteAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull  NewNoteAdapter.ViewHolder holder, int position) {
        Bitmap bitmap = Tool.getBitmapFromByte(images.get(position));
        holder.imageViewItem.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            //action
        }
    }
}
