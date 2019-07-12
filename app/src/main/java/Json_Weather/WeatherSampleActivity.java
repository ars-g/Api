package Json_Weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uidesigna1.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class WeatherSampleActivity extends AppCompatActivity implements ApiService.OnWeatherInfoRecieved {

    private static final String TAG = "WeatherSampleActivity";
    private ApiService apiService;
    ProgressBar progressBar;

    private TextView txtWeatherName, txtWeatherDescription, txtTemp, txtHumidity,
            txtPressure, txtMinTemp, txtMaxTemp, txtWindSpeed, txtWindDegree;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_sample);
        btn = (Button) findViewById(R.id.btnsendrequest);

        apiService = new ApiService(this);

        initViews();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiService.getCurrentWeather(WeatherSampleActivity.this, "Tehran");
                progressBar.setVisibility(View.VISIBLE);
                // apiService.getCurrentWeather(WeatherSampleActivity.this,"Ahvaz");
                //  apiService.getCurrentWeather(WeatherSampleActivity.this,"Ardabil");
                //  apiService.getCurrentWeather(WeatherSampleActivity.this,"Shiraz");
                // apiService.getCurrentWeather(WeatherSampleActivity.this,"Mashhad");
                // apiService.getCurrentWeather(WeatherSampleActivity.this,"Arak");

            }
        });
    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        txtWeatherName = (TextView) findViewById(R.id.txt_weather_name);
        txtWeatherDescription = (TextView) findViewById(R.id.txt_weather_description);
        txtTemp = (TextView) findViewById(R.id.txt_weather_temprature);
        txtHumidity = (TextView) findViewById(R.id.txt_weather_humidity);
        txtPressure = (TextView) findViewById(R.id.txt_weather_pressure);
        txtMinTemp = (TextView) findViewById(R.id.txt_weather_min_temp);
        txtMaxTemp = (TextView) findViewById(R.id.txt_weather_max_temp);
        txtWindSpeed = (TextView) findViewById(R.id.txt_weather_wind_speed);
        txtWindDegree = (TextView) findViewById(R.id.txt_weather_wind_degree);
    }

    @Override
    public void onRecieved(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
            //show information
            txtWeatherName.setText("آب و هوای فعلی:" + weatherInfo.getWeatherName());
            txtWeatherDescription.setText("توضیحات:" + weatherInfo.getWeatherDesciption());
            txtTemp.setText("دمای فعلی:" + String.valueOf(weatherInfo.getWeatherTemprature()));
            txtHumidity.setText("رطوبت هوا:" + String.valueOf(weatherInfo.getHumidity()));
            txtPressure.setText("میزان فشار هوا:" + String.valueOf(weatherInfo.getPressure()));
            txtMinTemp.setText("کم ترین دما:" + String.valueOf(weatherInfo.getMinTemprature()));
            txtMaxTemp.setText("بیشترین دما:" + String.valueOf(weatherInfo.getMaxTemprature()));
            txtWindSpeed.setText("سرعت باد:" + String.valueOf(weatherInfo.getWindSpeed()));
            txtWindDegree.setText("درجه ی باد:" + String.valueOf(weatherInfo.getWindDegree()));

        } else {
            Toast.makeText(getApplicationContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}
// "http://api.openweathermap.org/data/2.5/weather?q=London&apikey=12c34ed0e5949150423cc2d4e1a4ee69