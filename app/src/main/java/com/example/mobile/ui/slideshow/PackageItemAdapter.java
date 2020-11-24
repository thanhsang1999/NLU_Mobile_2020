package com.example.mobile.ui.slideshow;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobile.model.Package;
import com.example.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class PackageItemAdapter extends BaseAdapter {

    private List<Package> _items;
    Activity activity;
    Fragment fragment;
    public PackageItemAdapter(Fragment fragment, List<Package> aPackages){
        this._items = new ArrayList<>();
        this._items.add(new Package(0, "Create Note"));
        this._items.addAll(aPackages);
        this.fragment = fragment;
        this.activity=fragment.getActivity();

    }
    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int position) {
        return _items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Package book = _items.get(position);
        ViewHolder viewHolder = null;

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(activity);
            convertView = layoutInflater.inflate(R.layout.item_gridview_note, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }




            Drawable top = this.activity.getResources().getDrawable(R.drawable.ic_add_note);




        switch (book.getLevel()){
            case 0:
                top = this.activity.getResources().getDrawable(R.drawable.ic_add_note);
                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyModal myModal = new MyModal();
                        
                        myModal.show(fragment.getParentFragmentManager(), "bottom");

                    }
                });
                break;
            case 1:
                top = this.activity.getResources().getDrawable(R.drawable.ic_green);
                break;
            case 2:
                top = this.activity.getResources().getDrawable(R.drawable.ic_blue);
                break;

            default:
                break;
        }
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
        viewHolder.textView.setText(book.getName());
        return convertView;
    }
    private class  ViewHolder{
        private TextView textView;
    }
}
