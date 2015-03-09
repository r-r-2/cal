package com.example.athirasurendran.calandarexample;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    CalendarView calendar;
    Context context;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            Long date = calendar.getDate();

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (calendar.getDate() != date) {




                    date = calendar.getDate(); //new current date
                    //date is changed on real click
                    Toast.makeText(getApplicationContext(),
                            dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), event.class);
                    i.putExtra("Month", month);
                    i.putExtra("Day", dayOfMonth);
                    i.putExtra("Year", year);

                    startActivity(i);


                }
            }
        });

      /*  DatabaseHandler db = new DatabaseHandler(this);


        Log.d("Insert: ", "Inserting ..");
        db.addlocal(new local("Ravi", "9100000000"));
        db.addlocal(new local("Srinivas", "9199999999"));
        db.addlocal(new local("Tommy", "9522222222"));
        db.addlocal(new local("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<local> contacts = db.getAlllocals();

        for (local cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing locals to log
            Log.d("Name: ", log);


        }*/
        new read().execute();
    }


    class read extends AsyncTask<Void,Void,Void>
    {        DatabaseHandler db = new DatabaseHandler(context);
        String e,d;


        @Override
        protected void onPreExecute()
        {
            if(isOnline()) {
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.delete("contacts", null, null);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            if(isOnline()) {
                localparse a = new localparse();
                try {
                    JSONArray j = a.getJSONfromURL("http://notification.host56.com/local.php");
                    for (int i = 0; i < j.length(); i++) {
                        e = j.getJSONObject(i).getString("event");
                        d = j.getJSONObject(i).getString("date");
                        Log.d("Insert: ", "Inserting .." + d + " " + e);
                        db.addlocal(new local(d, e));


                        // Reading all contacts

                    }
                    Log.d("Reading: ", "Reading databse..");
                    List<local> contacts = db.getAlllocals();

                    for (local cn : contacts) {
                        String log = "Date: " + cn.getName() + " ,Event: " + cn.getPhoneNumber();
                        // Writing locals to log
                        Log.d("Calender Log", log);


                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return null;
        }
    }

    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i=new Intent(getApplicationContext(),settings.class);
            startActivity(i);
            return true;
        }
        else if(id==R.id.action_search){
            Intent i=new Intent(getApplicationContext(),NotificationAlert.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

