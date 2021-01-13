package com.example.mobile.ui.slideshow;



import android.app.Dialog;

import android.graphics.Bitmap;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.activity.HomeActivity;
import com.example.mobile.model.DateStringConverter;
import com.example.mobile.model.Package;
import com.example.mobile.R;
import com.example.mobile.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class PackageItemAdapter extends BaseAdapter {

    private final List<Package> _items;
    HomeActivity activity;
    SlideshowFragment fragment;
    ConnectionDatabaseLocalMobile c;
    public PackageItemAdapter(Fragment fragment, List<Package> aPackages){
        c= new ConnectionDatabaseLocalMobile(fragment.getActivity());
        this._items = new ArrayList<>();
        Package p=new Package();
        p.setName("Create Note");
        p.setLastEdit(new Date());
        p.setColor("create_package");
        this._items.add(p);
        this._items.addAll(aPackages);

        if(fragment instanceof  SlideshowFragment){
            this.fragment =(SlideshowFragment) fragment;
        }else{
            Log.e("Error", "slideShow Frament");
        }
        if(fragment.getActivity() instanceof  AppCompatActivity){
            this.activity=(HomeActivity) fragment.getActivity();
        }else{
            Log.e("Error", "Appcompat");
        }


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
        ViewHolder viewHolder;

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




        switch (book.getColor()){
            case "create_package":
                top = this.activity.getResources().getDrawable(R.drawable.ic_add_note);
                viewHolder.textView.setCompoundDrawablePadding(5);
                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(fragment.getActivity(), R.style.CustomDialogTheme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.create_packed);
                        dialog.show();
                        Bitmap takeScreenShot= Blur.takeScreenShot(fragment.getActivity());
                        new Thread(()->{

//                            Bitmap bitmap= Blur.canvasBlur(takeScreenShot,100000000);
//                            Bitmap bitmap= Blur.fastBlur(takeScreenShot, 1, 10);
                            Bitmap bitmap= Blur.rsBlur(fragment.getActivity(),takeScreenShot,15, 1);
                            final Drawable draw=new BitmapDrawable(fragment.getActivity().getResources(),bitmap);
                            fragment.getActivity().runOnUiThread(()->{
                                dialog.getWindow().setBackgroundDrawable(draw);
                            });
                        }).start();



                        LinearLayout color_pick =dialog.findViewById(R.id.color_pick);
                        ImageView picker = dialog.findViewById(R.id.picker);
                        picker.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(color_pick.getVisibility()==View.VISIBLE){
                                    color_pick.setVisibility(View.INVISIBLE);

                                }else
                                    color_pick.setVisibility(View.VISIBLE);
                            }
                        });

                        ImageView drawablePackage= dialog.findViewById(R.id.drawable_package);
                        EditText titlePackage= dialog.findViewById(R.id.titlePackage);


                        dialog.findViewById(R.id.color_blue).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_blue));
                                drawablePackage.setContentDescription("color_blue");
                            }
                        });
                        dialog.findViewById(R.id.color_green).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_green));
                                drawablePackage.setContentDescription("color_green");
                            }
                        });
                        dialog.findViewById(R.id.color_blue_deep).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_blue_deep));
                                drawablePackage.setContentDescription("color_blue_deep");
                            }
                        });
                        dialog.findViewById(R.id.color_blue_green).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_blue_green));
                                drawablePackage.setContentDescription("color_blue_green");
                            }
                        });

                        dialog.findViewById(R.id.color_orange).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_orange));
                                drawablePackage.setContentDescription("color_orange");
                            }
                        });

                        dialog.findViewById(R.id.color_yellow).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_yellow));
                                drawablePackage.setContentDescription("color_yellow");
                            }
                        });

                        dialog.findViewById(R.id.color_red).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_red));
                                drawablePackage.setContentDescription("color_red");
                            }
                        });

                        dialog.findViewById(R.id.color_red_2).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_red_2));
                                drawablePackage.setContentDescription("color_red_2");
                            }
                        });
                        dialog.findViewById(R.id.color_red_origin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_red_origin));
                                drawablePackage.setContentDescription("color_red_origin");
                            }
                        });
                        dialog.findViewById(R.id.color_purple).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_purple));
                                drawablePackage.setContentDescription("color_purple");
                            }
                        });

                        dialog.findViewById(R.id.color_purple_2).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_purple_2));
                                drawablePackage.setContentDescription("color_purple_2");
                            }
                        });
                        dialog.findViewById(R.id.color_purple_3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawablePackage.
                                setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_package_purple_3));
                                drawablePackage.setContentDescription("color_purple_3");
                            }
                        });

                        dialog.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Package p= new Package();
                                p.setColor(drawablePackage.getContentDescription().toString());
                                p.setName(titlePackage.getText().toString());
                                p.setLastEdit(new Date());

                                c.insert_package(p);
                                _items.add(c.getLastPackage());
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });






                    }
                });
                break;
            case "color_green":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_green);
                break;
            case "color_blue_deep":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_blue_deep);
                break;
             case "color_blue_green":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_blue_green);
                break;
            case "color_blue":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_blue);
                break;
            case "color_red":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_red);
                break;
            case "color_red_origin":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_red_origin);
                break;
            case "color_red_2":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_red_2);
                break;
            case "color_orange":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_orange);
                break;
            case "color_yellow":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_yellow);
                break;
            case "color_purple":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_purple);
                break;
            case "color_purple_2":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_purple_2);
                break;
            case "color_purple_3":
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_purple_3);
                break;





            default:
                top = this.activity.getResources().getDrawable(R.drawable.ic_package_blue_green);
                break;
        }
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
        viewHolder.textView.setCompoundDrawablePadding(0);
        viewHolder.textView.setText(book.getName());
        if(book.getColor()!="create_package"){
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NavController navController=NavHostFragment.findNavController(PackageItemAdapter.this.fragment);
                    Bundle bundle= new Bundle();
                    bundle.putParcelable("currentPackage", book);
                    navController.navigate(R.id.action_package_to_notes,bundle);




                }
            });

        }
        return convertView;
    }
    private class  ViewHolder{
        private TextView textView;
    }




}
