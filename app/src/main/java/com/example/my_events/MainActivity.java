package com.example.my_events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFERENCES_KEY = "shared_preferences";
    private static final String EVENT_LIST_JSON = "event_list_key";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //events = new ArrayList<>();
        //getEvents();
        loadEvents();

        recyclerView = (RecyclerView) findViewById(R.id.event_listRV);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EventListAdaptor(events);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }

    private void loadEvents() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String eventListJson = sharedPreferences.getString(EVENT_LIST_JSON, null);
        Type type = new TypeToken<ArrayList<Event>>(){}.getType();
        Gson gson = new Gson();
        events = gson.fromJson(eventListJson, type);
        if(events == null) events = new ArrayList<Event>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveEvents();
    }

    public void addEvent(View view){
        EditText dateEditText = (EditText) findViewById(R.id.date_inputEditText);
        EditText nameEditText = (EditText) findViewById(R.id.name_inputEditText);
        String date = dateEditText.getText().toString();
        String name = nameEditText.getText().toString();
        events.add(new Event(date, name));
        adapter.notifyDataSetChanged();
    }
    private void saveEvents(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String eventListJson = gson.toJson(events);
        editor.putString(EVENT_LIST_JSON, eventListJson);
        editor.apply();
    }

    public void getEvents(){
        for(int i = 0; i < EventDB.dates.length; i++){
            events.add(new Event(EventDB.dates[i], EventDB.descriptions[i]));
        }
    }
}