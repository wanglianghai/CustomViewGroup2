package com.bignerdranch.android.scrollerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View view = findViewById(R.id.parent_view);
        findViewById(R.id.scroll_by).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.scrollBy((int) getResources().getDimension(R.dimen.move_space), 0);
            }
        });

        findViewById(R.id.scroll_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.scrollTo(0, 0);
            }
        });
    }
}
