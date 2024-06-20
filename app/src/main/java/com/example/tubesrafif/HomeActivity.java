package com.example.tubesrafif;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private MyAdapter myAdapter, myAdapter2, myAdapter3;
    private List<DataModel> dataList, dataList2, dataList3;
    private ImageButton setTanggalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.view_deck1);
        recyclerView2 = findViewById(R.id.view_deck2);
        recyclerView3 = findViewById(R.id.view_deck3);
        setTanggalButton = findViewById(R.id.setTanggal);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        dataList = new ArrayList<>();
        dataList2 = new ArrayList<>();
        dataList3 = new ArrayList<>();

        myAdapter = new MyAdapter(dataList, this);
        myAdapter2 = new MyAdapter(dataList2, this);
        myAdapter3 = new MyAdapter(dataList3, this);

        recyclerView.setAdapter(myAdapter);
        recyclerView2.setAdapter(myAdapter2);
        recyclerView3.setAdapter(myAdapter3);

        new FetchDataTask("http://192.168.146.1/ta_projek/tubesrapip/fetch_data.php?lokasi=1", dataList, myAdapter).execute();
        new FetchDataTask("http://192.168.146.1/ta_projek/tubesrapip/fetch_data.php?lokasi=2", dataList2, myAdapter2).execute();
        new FetchDataTask("http://192.168.146.1/ta_projek/tubesrapip/fetch_data.php?lokasi=3", dataList3, myAdapter3).execute();

        setTanggalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan tanggal saat ini
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Membuat dan menampilkan DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Tindakan yang diambil saat tanggal dipilih
                                String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                                // Lakukan sesuatu dengan tanggal yang dipilih
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

    }

    private class FetchDataTask extends AsyncTask<Void, Void, String> {
        private String url;
        private List<DataModel> dataList;
        private MyAdapter adapter;

        public FetchDataTask(String url, List<DataModel> dataList, MyAdapter adapter) {
            this.url = url;
            this.dataList = dataList;
            this.adapter = adapter;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String namaDeck = jsonObject.getString("nama_deck");
                        String harga = jsonObject.getString("harga");
                        String urlFoto = jsonObject.optString("url_foto", ""); // menggunakan optString untuk menghindari NPE
                        dataList.add(new DataModel(id, namaDeck, harga, urlFoto));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
