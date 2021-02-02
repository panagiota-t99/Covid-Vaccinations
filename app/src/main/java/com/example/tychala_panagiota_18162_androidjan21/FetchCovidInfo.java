package com.example.tychala_panagiota_18162_androidjan21;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchCovidInfo extends AsyncTask<String, Void, List<CovidItem>> {

    private String LOG_TAG = "dai18162";
    public static final String REMOTE_API = "https://data.gov.gr/api/v1/query/mdg_emvolio?date_from=2021-01-21&date_to=2021-01-31";
    public static final String API_KEY = "e625c5c9c2b0718ff74f33150d1782c5edc072d1";

    private CovidAdapter adapter;

    public FetchCovidInfo(CovidAdapter covidAdapter) {
        adapter = covidAdapter;
    }

    @Override
    protected List<CovidItem> doInBackground(String... strings) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(REMOTE_API);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty ("Authorization", "Token " + API_KEY);
            urlConnection.connect();
            StringBuilder buffer = new StringBuilder();
            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null) {
                    return new ArrayList<>();
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
            }
            if (buffer.length() == 0) {
                return new ArrayList<>();
            }
            String newsJSONString = buffer.toString();
            Log.v(LOG_TAG, "News JSON String: " + newsJSONString);

            return getInfoFromJason(newsJSONString);

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error downloading data", e);
            return new ArrayList<>();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

    }

    private List<CovidItem> getInfoFromJason (String JSONString) throws JSONException {
        final String area_literal = "area";
        final String daydiff_literal = "daydiff";
        final String daytotal_literal = "daytotal";
        final String totalvaccinations_literal = "totalvaccinations";

        List<CovidItem> list = new ArrayList<>();


        try{
            JSONArray jsonArray = new JSONArray(JSONString);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String area = jsonObject.getString(area_literal);
                int daydiff = jsonObject.getInt(daydiff_literal);
                int daytotal = jsonObject.getInt(daytotal_literal);
                int totalvaccinations = jsonObject.getInt(totalvaccinations_literal);

                CovidItem item = new CovidItem();
                item.setArea(area);
                item.setDaydiff(daydiff);
                item.setDaytotal(daytotal);
                item.setTotalvaccinations(totalvaccinations);

                list.add(item);
            }
        }catch(Exception e){
            Log.e(LOG_TAG, "Error happened when parsing data!",e);
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<CovidItem> list) {
        adapter.setCovidEntries(list);
    }
}
