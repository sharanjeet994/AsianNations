package com.example.android.asiannations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.asiannations.Adapters.ItemsAdapter;
import com.example.android.asiannations.Adapters.NationsAdapter;
import com.example.android.asiannations.databinding.ActivityMainBinding;
import com.example.android.asiannations.room.Database;
import com.example.android.asiannations.room.Nations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private NationsAdapter nationsAdapter;
    private ArrayList<Items> itemsArrayList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Connectivity(); // method to check internet connectivity of the user

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemsArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.deleteDatabase:
                Database database = Room.databaseBuilder(this, Database.class, "NationsDb").allowMainThreadQueries().build();
                database.dao().nukeTable();
                Toast.makeText(this, "Database Deleted....", Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void parseJSON() {

        String url = "https://restcountries.eu/rest/v2/region/asia";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i=0;i<response.length();i++){

                        JSONObject object = response.getJSONObject(i);

                        String flag = object.getString("flag");
                        String nation = object.getString("name");
                        String capital = object.getString("capital");
                        String region = object.getString("region");
                        String subRegion = object.getString("subregion");
                        long population = object.getLong("population");

                        String borders = object.getString("borders");
                        JSONArray languages = object.getJSONArray("languages");
                        String lang = " ";

                        for (int n = 0;n<languages.length();n++){
                            JSONObject object1 =languages.getJSONObject(n);
                              String lang2 = object1.getString("name");

                            Log.d("language : ","onResponse "+lang.concat(lang2));

                            lang = lang+" "+lang2;
                        }
                        itemsArrayList.add(new Items(flag,nation,capital,region,subRegion,population,borders,lang));

                    }

                    adapter = new ItemsAdapter(MainActivity.this,itemsArrayList);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }

    public void Connectivity(){

        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo !=null){
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                // when network is accessed through wifi
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                // when network is accessed through mobile data
            }
            else {
                // when there is no internet connectivity
                Database database = Room.databaseBuilder(this, Database.class, "NationsDb").allowMainThreadQueries().build();
                List<Nations> nationsArrayList = database.dao().getNations();
                    if (nationsArrayList.isEmpty()){

                        Toast.makeText(this, "First Time Internet Connectivity is required !!", Toast.LENGTH_SHORT).show();

                }
                    else {
                       nationsAdapter = new NationsAdapter(MainActivity.this,nationsArrayList);
                       recyclerView.setAdapter(nationsAdapter);
                    }
            }
        }
    }
}