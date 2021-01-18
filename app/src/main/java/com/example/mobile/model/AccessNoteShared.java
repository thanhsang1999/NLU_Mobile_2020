package com.example.mobile.model;

public class AccessNoteShared {
    int id;
    Account account;
    NoteShared noteShared;

    public AccessNoteShared(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public NoteShared getNoteShared() {
        return noteShared;
    }

    public void setNoteShared(NoteShared noteShared) {
        this.noteShared = noteShared;
    }

    @Override
    public boolean equals(Object o) {
        AccessNoteShared that= (AccessNoteShared) o;
        if (that == null) return false;

        return this.getAccount().getEmail().equals(that.getAccount().getEmail()) &&
                this.getAccount().getUsername().equals(that.getAccount().getUsername());
    }
}
