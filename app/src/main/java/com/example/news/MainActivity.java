package com.example.news;

import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NewsItemClicked {
    NewsViewAdapter adapter;
    RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new NewsViewAdapter(this);
        fetch();
        this.recyclerView.setAdapter(this.adapter);
    }

    private void fetch() {
        MySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v2/top-headlines?country=in&apiKey=13f0e594241746f2b5209873b8b5d39d", null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                JSONArray newsJsonArray = null;
                try {
                    newsJsonArray = response.getJSONArray("articles");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList<NewsInfo> newsInfoArray = new ArrayList<>();
                for (int i = 0; i < newsJsonArray.length(); i++) {
                    JSONObject newsJsonObject = null;
                    NewsInfo ob = null;
                    try {
                        newsJsonObject = newsJsonArray.getJSONObject(i);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    try {
                        ob = new NewsInfo(newsJsonObject.getString("title"), newsJsonObject.getString("author"), newsJsonObject.getString("url"), newsJsonObject.getString("urlToImage"));
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                    newsInfoArray.add(ob);
                }
                MainActivity.this.adapter.updateNews(newsInfoArray);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this.recyclerView.getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        });
    }

    public void onItemClicked(NewsInfo item) {
        new CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(item.url));
    }
}
