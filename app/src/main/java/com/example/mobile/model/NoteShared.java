package com.example.mobile.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NoteShared implements Parcelable {
    private int id;
    private String title;
    private String content;
    private Date dateEdit;
    private Date remind;
    private boolean checked;
    private Account account;
    List<byte[]> images;


    public NoteShared() {

        this.checked=false;
        this.images= new ArrayList<>();
    }





    public static final Creator<NoteShared> CREATOR = new Creator<NoteShared>() {
        @Override
        public NoteShared createFromParcel(Parcel in) {
            return new NoteShared(in);
        }

        @Override
        public NoteShared[] newArray(int size) {
            return new NoteShared[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        if(title==null)return "";
        return title;
    }

    public void setTitle(String title) {

        if(title==null) {
            this.title = "";
        }else
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        if(content==null) {
            this.content = "";
        }else
        this.content = content;
    }



    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Date getRemind() {
        return remind;
    }

    public void setRemind(Date remind) {

        this.remind = remind;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeByte((byte) ((checked) ? 1: 0));
        dest.writeLong(dateEdit.getTime());
        dest.writeByte((byte) ((remind == null) ? 0 : 1));
        if(remind!=null)
        dest.writeLong(remind.getTime());




    }
    protected NoteShared(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        checked= in.readByte()==1;
        dateEdit=new Date(in.readLong());
        byte checkCalendar = in.readByte();
        if(checkCalendar==(byte)1){

            remind=new Date(in.readLong());
        }

        images= new ArrayList<>();




    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
