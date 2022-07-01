package com.example.hashmapdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EntryAdapter adapter;
    ArrayList<HashMap<String, String>> dataArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =(findViewById(R.id.recycler));

        adapter = new EntryAdapter(dataArrayList,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        getData();


    }

    private void getData() {
             final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.publicapis.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        EntryInterface mainInterface = retrofit.create(EntryInterface.class);
        Call<String> stringCall = mainInterface.STRING_CALL();

        stringCall.enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("Response : "+response.body());
                    pd.cancel();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        parseArray(jsonObject);
                    } catch (JSONException e ) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseArray(JSONObject jsonObject) {
        dataArrayList.clear();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("entries");
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                String api = jsonObject.getString("API");
                String auth = jsonObject.getString("Auth");
                String desc = jsonObject.getString("Description");
                dataArrayList.add(getHashMap(api, auth, desc));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    private HashMap<String, String> getHashMap(String api, String auth, String desc){
        HashMap<String, String> map = new HashMap<>();
        map.put("api", api);
        map.put("auth", auth);
        map.put("desc", desc);
        return map;
    }}