package Json_Weather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InterfaceAddress;

public class ApiService {

    private static final String TAG = "ApiService";
   // String error = "Sorry We Are Out OF Service";
    private Context context;

    public ApiService(Context context) {
        this.context = context;
    }

    public void getCurrentWeather(final OnWeatherInfoRecieved onWeatherInfoRecieved, String cityName) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://api.openweathermap.org/data/" +
                "2.5/weather?q=London&apik" +
                "ey=12c34ed0e5949150423cc2d4e1a4ee69", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                         onWeatherInfoRecieved.onRecieved(parseResponsetoWeatherInfo(response));

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
                onWeatherInfoRecieved.onRecieved(null);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private WeatherInfo parseResponsetoWeatherInfo(JSONObject response) {
        WeatherInfo weatherInfo = new WeatherInfo();
        try {

            JSONArray weatherJsonArray = response.getJSONArray("weather");
            JSONObject weatherJsonObject = weatherJsonArray.getJSONObject(0);
            weatherInfo.setWeatherName(weatherJsonObject.getString("main"));
            weatherInfo.setWeatherDesciption(weatherJsonObject.getString("description"));
            JSONObject mainJsonObject = response.getJSONObject("main");

            weatherInfo.setHumidity(mainJsonObject.getInt("humidity"));
            weatherInfo.setPressure(mainJsonObject.getInt("pressure"));
            weatherInfo.setMinTemprature((float) mainJsonObject.getDouble("temp_min"));
            weatherInfo.setMaxTemprature((float) mainJsonObject.getDouble("temp_max"));

            JSONObject windJsonObject = response.getJSONObject("wind");
            weatherInfo.setWindSpeed((float) windJsonObject.getDouble("speed"));
            weatherInfo.setWindDegree((float) windJsonObject.getDouble("deg"));

            weatherInfo.setWeatherTemprature((float) mainJsonObject.getDouble("temp"));
            return weatherInfo;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    public interface OnWeatherInfoRecieved {
        void onRecieved(WeatherInfo weatherInfo);
    }

}
