package com.semihoksuzoglu.currency_convert;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView jpyText;
    TextView chfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryTextView);
        cadText = findViewById(R.id.cadTextView);
        usdText = findViewById(R.id.usdTextView);
        jpyText = findViewById(R.id.jypTextView);
        chfText = findViewById(R.id.chfTextView);
    }

    public void getRates(View view) {

        DownloadData downloadData = new DownloadData();

        try {
            String url = "http://data.fixer.io/api/" +
                    "latest?access_key=67106552b148b4b016b4618e46a650a3&format=1";
            downloadData.execute(url);

        } catch (Exception e) {

        }

    }


    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {
                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }
                return result;

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);

                String turkishlira = jsonObject1.getString("TRY");
                tryText.setText("TRY: " + turkishlira);
                String CHF = jsonObject1.getString("CHF");
                chfText.setText("CHF: " + CHF);
                String USD = jsonObject1.getString("USD");
                usdText.setText("USD: " + USD);
                String JYP = jsonObject1.getString("JPY");
                jpyText.setText("JPY: " + JYP);
                String CAD = jsonObject1.getString("CAD");
                cadText.setText("CAD: " + CAD);

            } catch (Exception e) {

            }
        }
    }


}
