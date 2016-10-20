package database;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import domain.Shake;

/**
 * Created by Stan Guldemond on 07/10/16.
 */
public class BackgroundWorker extends AsyncTask<Object, Object, Object> {
    Context context;

    public BackgroundWorker(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {

        String type = (String)params[0];

        String shake_url = "http://i254083.iris.fhict.nl/sm41/insert_shake.php";

        if(type.equals("shake")) {
            try {
                Shake shake = (Shake)params[1];


                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("userid", "UTF-8")+"="+URLEncoder.encode(shake.getUserid().toString(), "UTF-8")+"&"
                + URLEncoder.encode("lat", "UTF-8")+"="+URLEncoder.encode(shake.getLatitude().toString(), "UTF-8")+"&"
                + URLEncoder.encode("lon", "UTF-8")+"="+URLEncoder.encode(shake.getLongitude().toString(), "UTF-8")+"&"
                + URLEncoder.encode("datetime", "UTF-8")+"="+URLEncoder.encode(shake.getDateTime().toString(), "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line=bufferedReader.readLine()) != null) {
                    result += line + "/n";
                }

                System.out.println(result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }
}
