package com.example.mobile.ui.slideshow;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;

import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.mobile.ConnectionDatabaseLocalMobile;
import com.example.mobile.model.Package;
import com.example.mobile.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class PackageItemAdapter extends BaseAdapter {

    private List<Package> _items;
    Activity activity;
    Fragment fragment;
    ConnectionDatabaseLocalMobile c;
    public PackageItemAdapter(Fragment fragment, List<Package> aPackages){
        c= new ConnectionDatabaseLocalMobile(fragment.getActivity());
        this._items = new ArrayList<>();
        this._items.add(new Package("create_package", "Create Note", new Date(), new Date()));
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




        switch (book.getColor()){
            case "create_package":
                top = this.activity.getResources().getDrawable(R.drawable.ic_add_note);
                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        MyModal myModal = new MyModal();
//                        CreatePackageDialogFragment myModal = new CreatePackageDialogFragment();
//                        final Dialog dialog = new Dialog(fragment.getActivity(), R.style.Theme);
//                        final Dialog dialog = new Dialog(fragment.getActivity());
                        final Dialog dialog = new Dialog(fragment.getActivity(), R.style.CustomDialogTheme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.create_packed);



//                        View beneathView = fragment.getActivity().getWindow().getDecorView();
//
//
//                                BlurDrawable blurDrawable = new BlurDrawable(beneathView, 100);
//
//                        dialog.getWindow().setBackgroundDrawable(blurDrawable);


                        Bitmap map=takeScreenShot(fragment.getActivity());


                        Bitmap fast= fastblur(map,100);

                        final Drawable draw=new BitmapDrawable(fragment.getActivity().getResources(),fast);
                        dialog.getWindow().setBackgroundDrawable(draw);


                        dialog.show();



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
                                Package p= new Package( drawablePackage.getContentDescription().toString(), titlePackage.getText().toString(), new Date(), new Date());
                                _items.add(p);
                                c.insert_package(p);
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
        viewHolder.textView.setText(book.getName());
        return convertView;
    }
    private class  ViewHolder{
        private TextView textView;
    }

    private Bitmap blur(Bitmap originalBitmap) {
        BlurMaskFilter blurFilter = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
        Paint shadowPaint = new Paint();
        shadowPaint.setMaskFilter(blurFilter);
        Bitmap bitmapResult = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config. ARGB_8888);
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setShader(new BitmapShader(originalBitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT));
        paint.setMaskFilter(blurFilter);
        Canvas canvasResult = new Canvas(bitmapResult);
        canvasResult.drawBitmap(bitmapResult, 0, 0, paint);
        return bitmapResult;
    }

    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();


        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
    public Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }
        yw = yi = 0;
        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;
        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

}
