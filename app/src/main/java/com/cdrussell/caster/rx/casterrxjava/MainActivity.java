package com.cdrussell.caster.rx.casterrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button naiveButton;
    private Button cleverButton;
    private TextView resultTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindViews();
    }

    private void bindViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        naiveButton = (Button) findViewById(R.id.readValueButton);
        cleverButton = (Button) findViewById(R.id.readValueDefer);
        resultTextView = (TextView) findViewById(R.id.result);

        naiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                try {
                    resultTextView.setText(Database.readValue());
                } catch (IOException e) {
                    resultTextView.setText(e.getMessage());
                }

                hideProgress();
            }
        });

        cleverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();

                Observable<String> deferred = Observable.defer(() -> {
                    try {
                        return Observable.just(Database.readValue());
                    } catch (IOException e) {
                        return Observable.error(e);
                    }
                });

                deferred.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> {
                            resultTextView.setText(value);
                            hideProgress();
                        });
            }
        });

    }


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
