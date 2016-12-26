package com.cdrussell.caster.rx.casterrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button naiveButton;
    private Button cleverButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindViews();
    }

    private void bindViews() {
        naiveButton = (Button) findViewById(R.id.readValueButton);
        cleverButton = (Button) findViewById(R.id.readValueFromCallable);
        resultTextView = (TextView) findViewById(R.id.result);

        naiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = Database.readValue();
                resultTextView.setText(result);
            }
        });

        cleverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.fromCallable(Database::readValue)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> resultTextView.setText(result));
            }
        });
    }
}
