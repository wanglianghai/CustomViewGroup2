package com.bignerdranch.android.scrollerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView mListViewI;
    private ListView mListViewL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PageView
        /*String[] str1 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        mListViewI = (ListView) findViewById(R.id.integer);
        ArrayAdapter<String> adapterI = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str1);
        mListViewI.setAdapter(adapterI);

        String[] str2 = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
        mListViewL = (ListView) findViewById(R.id.letter);
        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str2);
        mListViewL.setAdapter(adapterL);*/
        //下面的是第一部分custom1
        /*final View view = findViewById(R.id.parent_view);
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
        });*/
    }
}
