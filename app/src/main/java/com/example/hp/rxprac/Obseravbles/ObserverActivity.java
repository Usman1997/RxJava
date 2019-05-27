package com.example.hp.rxprac.Obseravbles;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hp.rxprac.Note;
import com.example.hp.rxprac.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObserverActivity extends AppCompatActivity {

    private static final String TAG = ObserverActivity.class.getSimpleName();
    private Disposable disposable;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Single<Note> noteObservable = getNoteObservable();
        SingleObserver<Note> noteObserver = getNoteObserver();


        noteObservable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(noteObserver);
    }


    private SingleObserver<Note> getNoteObserver(){
        return new SingleObserver<Note>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(Note note) {
                Log.e(TAG, "onSuccess: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        };
    }

    private Single<Note> getNoteObservable(){

         return Single.create(new SingleOnSubscribe<Note>() {
             @Override
             public void subscribe(SingleEmitter<Note> emitter) throws Exception {
                 Note note = new Note(1, "Buy milk!");
                 emitter.onSuccess(note);
             }
         });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
