package com.example.athirasurendran.calandarexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class event extends ActionBarActivity {
    TextView date,events,v;
    String str;
    String month[] = {"Jan","Feb"," Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov"," Dec"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        date = (TextView)findViewById(R.id.date);
        events = (TextView)findViewById(R.id.events);
        date.setText("" +getIntent().getIntExtra("Day",0)+" " + month[getIntent().getIntExtra("Month",0)]+" " + getIntent().getIntExtra("Year",0));

        str="" +getIntent().getIntExtra("Year",0)+"-" + ((getIntent().getIntExtra("Month",0))+1)+"-" + getIntent().getIntExtra("Day",0);
        v=(TextView)findViewById(R.id.events1);
        new read().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public class read extends AsyncTask<JSONArray,String,String>
    {
        String s;
        private ProgressDialog pdia;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(event.this);
            pdia.setMessage("Loading Data...");
            pdia.show();

        }

        @Override
        protected void onPostExecute(String s) {
           super.onPostExecute(s);
            pdia.dismiss();
            if(s==null)
                s="<No Events>";

            v.setText(s);
        }

        @Override
        protected String doInBackground(JSONArray... params) {

            database a=new database();
            try {
                JSONArray j = a.getJSONfromURL("http://notification.host56.com/connect.php",str);
                 s=j.getJSONObject(0).getString("event");

                return s;
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
    }




}
