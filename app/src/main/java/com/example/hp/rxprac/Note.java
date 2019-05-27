package com.example.hp.rxprac;

import io.reactivex.schedulers.Schedulers;

public class Note {
    int id;
    String note;

    public Note(int id, String note){
        this.id  = id;
        this.note = note;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
