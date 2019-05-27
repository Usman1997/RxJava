package com.example.hp.rxprac.Obseravbles;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.schedulers.Schedulers;

public class SingleObserverActivity extends AppCompatActivity {
    private static final String TAG = SingleObserverActivity.class.getSimpleName();
    Disposable disposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<Note> noteObservable = getNotesObservable();
        Observer<Note> noteObserver = getNotesObserver();



    }


    private List<Note> prepareNotes(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1,"Note 1"));
        notes.add(new Note(2,"Note 2"));
        notes.add(new Note(3,"Note 3"));
        notes.add(new Note(4,"Note 4"));
        notes.add(new Note(5,"Note 5"));

        return notes;
    }


    private Observer<Note> getNotesObserver(){
         return new Observer<Note>() {
             @Override
             public void onSubscribe(Disposable d) {
                 disposable = d;
             }

             @Override
             public void onNext(Note note) {
                 Log.d(TAG, "onNext: " + note.getNote());

             }

             @Override
             public void onError(Throwable e) {
                 Log.e(TAG, "onError: " + e.getMessage());

             }

             @Override
             public void onComplete() {
                 Log.d(TAG, "onComplete");

             }
         };
    }

    private Observable<Note> getNotesObservable(){
        final List<Note> notes = prepareNotes();

        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(ObservableEmitter<Note> emitter) throws Exception {
                for(Note note:notes){
                    if(!emitter.isDisposed()){
                        emitter.onNext(note);
                    }
                }

                if(!emitter.isDisposed()){
                    emitter.onComplete();
                }

            }
        });
    }
}
