package com.example.hp.rxprac;

import android.app.NotificationManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        io.reactivex.Observable<Note> notesObeservable =  getNotesObservable();
        DisposableObserver<Note> noteObserver = getNotesObserver();

        compositeDisposable.add(
                 notesObeservable.subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<Note, Note>() {

                            @Override
                            public Note apply(Note note) throws Exception {
                                note.setNote(note.getNote().toUpperCase());
                                return note;
                            }
                        })
                        .subscribeWith(noteObserver));




    }



    private DisposableObserver<Note> getNotesObserver(){
        return new DisposableObserver<Note>() {

            @Override
            public void onNext(Note s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }



    private io.reactivex.Observable<Note> getNotesObservable(){
      final List<Note> notes = prepareNote();

      return io.reactivex.Observable.create(new ObservableOnSubscribe<Note>() {
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

    private List<Note> prepareNote(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1,"Note 1"));
        notes.add(new Note(2,"Note 2"));
        notes.add(new Note(1,"Note 3"));
        notes.add(new Note(1,"Note 4"));
        notes.add(new Note(1,"Note 5"));
        return notes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
