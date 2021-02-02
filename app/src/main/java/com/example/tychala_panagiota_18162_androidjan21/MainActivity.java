package com.example.tychala_panagiota_18162_androidjan21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CovidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.covidlist);
        adapter = new CovidAdapter(this, R.layout.covid_list_item, new ArrayList<CovidItem>());
        listView.setAdapter(adapter);

        FetchCovidInfo task = new FetchCovidInfo(adapter);
        task.execute();
    }
}