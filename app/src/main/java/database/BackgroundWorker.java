package database;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
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
import java.util.ArrayList;
import java.util.List;

import domain.Activiteit;
import domain.Gebruiker;
import domain.Meeting;
import domain.Shake;

/**
 * Created by Stan Guldemond on 07/10/16.
 */

public class BackgroundWorker extends AsyncTask<Object, Object, Object> {
    AsyncResponce delegate = null;

    Context context;

    String meeting_result;

    List<Activiteit> activiteiten_result;

    String type;

    public BackgroundWorker(Context context, AsyncResponce delegate) {
        this.delegate = delegate;
        this.context = context;
        meeting_result = null;

        activiteiten_result = new ArrayList<>();
    }

    @Override
    protected String doInBackground(Object... params) {

        type = (String)params[0];

        if(type.equals("login")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/login.php";

                String[] gebruiker = (String[]) params[1];

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("gebruikersnaam", "UTF-8")+"="+URLEncoder.encode(gebruiker[0], "UTF-8")+"&"
                        + URLEncoder.encode("wachtwoord", "UTF-8")+"="+URLEncoder.encode(gebruiker[1], "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String result = reader.readLine();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("Register")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/insert_register.php";

                String[] gebruiker = (String[]) params[1];


                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("gebruikersnaam", "UTF-8")+"="+URLEncoder.encode(gebruiker[0], "UTF-8")+"&"
                        + URLEncoder.encode("wachtwoord", "UTF-8")+"="+URLEncoder.encode(gebruiker[1], "UTF-8")+"&"
                        + URLEncoder.encode("datumtijd", "UTF-8")+"="+URLEncoder.encode(gebruiker[2], "UTF-8");

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

        else if(type.equals("shake")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/insert_shake.php";

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
                + URLEncoder.encode("lon", "UTF-8")+"="+URLEncoder.encode(shake.getLongitude().toString(), "UTF-8");

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

        else if(type.equals("meeting")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/insert_meeting.php";

                String[] meeting = (String[]) params[1];

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("eerstegebruikerid", "UTF-8")+"="+URLEncoder.encode(meeting[0], "UTF-8")+"&"
                        + URLEncoder.encode("activiteitid", "UTF-8")+"="+URLEncoder.encode(meeting[1], "UTF-8")+"&"
                        + URLEncoder.encode("datumtijd", "UTF-8")+"="+URLEncoder.encode(meeting[2], "UTF-8");

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

        else if(type.equals("find_meeting")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/find_meeting.php";

                String[] meetingid = (String[]) params[1];


                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("activiteitid", "UTF-8")+"="+URLEncoder.encode(meetingid[0], "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String result = reader.readLine();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("update_meeting")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/update_meeting.php";

                String[] meeting = (String[]) params[1];
                System.out.println(meeting[0]+" gebruiker 5");
                System.out.println(meeting[1]+" Meeting 4");

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("tweedegebruikerid", "UTF-8")+"="+URLEncoder.encode(meeting[0], "UTF-8")+"&"
                        + URLEncoder.encode("meetingid", "UTF-8")+"="+URLEncoder.encode(meeting[1], "UTF-8");

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

        else if(type.equals("find_meeting")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/find_meeting.php";

                String[] meetingid = (String[]) params[1];

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("activiteitid", "UTF-8")+"="+URLEncoder.encode(meetingid[0], "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String result = reader.readLine();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("get_activities")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/get_activities.php/";

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String result = "";

                String line;

                while ((line = reader.readLine()) != null) {
                    result += line;
                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("update_gebruiker")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/update_gebruiker.php";

                String[] input = (String[]) params[1];

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("gebruikerid", "UTF-8")+"="+URLEncoder.encode(input[0], "UTF-8")+"&"
                        + URLEncoder.encode("wilontmoeten", "UTF-8")+"="+URLEncoder.encode(input[1], "UTF-8")+"&"
                        + URLEncoder.encode("kilometerrange", "UTF-8")+"="+URLEncoder.encode(input[2], "UTF-8")+"&"
                        + URLEncoder.encode("budget", "UTF-8")+"="+URLEncoder.encode(input[3], "UTF-8")+"&"
                        + URLEncoder.encode("tijd", "UTF-8")+"="+URLEncoder.encode(input[4], "UTF-8");

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

        else if(type.equals("get_voorkeuren")) {
            try {
                String shake_url = "http://i254083.iris.fhict.nl/sm41/get_voorkeuren.php";

                String gebruikerid = (String) params[1];

                URL url = new URL(shake_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("gebruikerid", "UTF-8")+"="+URLEncoder.encode(gebruikerid, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String result = reader.readLine();

                return result;

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
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        delegate.processFinish(type, result);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }
}
