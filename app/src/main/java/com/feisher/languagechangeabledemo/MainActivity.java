package com.feisher.languagechangeabledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.feisher.langlib.CLang;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLang.swithLang(MainActivity.this);
//                CLang.swithLang(MainActivity.this.getApplicationContext());
                MainActivity.this.recreate();
            }
        });
    }

}
