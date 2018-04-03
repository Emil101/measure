package com.xorek.measurementbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MeasurementBook extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private MeasureAdapter adapter;
    private ArrayList<Client> row = new ArrayList<Client>();
    DbHandler dbHandler;
//    Cursor cursor;


    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
            context = getApplicationContext();


            recyclerView = (RecyclerView)findViewById(R.id.recycler_view);




//        Bundle bundle = getIntent().getExtras();
//         resInfoList = (List<ResolveInfo>) bundle.getSerializable("mylist");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        new add().execute();

    }


    private class add extends AsyncTask<Object, Object, Cursor> {

        @Override
        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(MeasurementBook.this, R.style.MyDialog);
//            progressDialog.setTitle("Client List");
//            progressDialog.setMessage("Loading..");
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            //   Toast.makeText(secure.this, "ScreenBgrd process started", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Cursor doInBackground(Object... voids) {
            dbHandler = new DbHandler(context);
            Cursor cr = dbHandler.getAll(dbHandler);
            return cr;
        }

        @Override
        protected void onPostExecute(Cursor cr) {


            if ((cr != null)&& cr.moveToFirst()){

                do {
                    Client client = new Client();
                    client.setId(cr.getInt(0));
                    client.setName(cr.getString(1));
                    client.setDiliveryDate(cr.getString(2));
                    client.setImage(cr.getString(3));
                    client.setImagepath(cr.getString(4));
                    row.add(client);

//                    Log.d("TAG", String.valueOf(client.getId()));
//                    Toast.makeText(getApplicationContext(),cr.getString(0),Toast.LENGTH_SHORT).show();
                }while (cr.moveToNext());

                recyclerView.setLayoutManager(new LinearLayoutManager(MeasurementBook.this));

                adapter = new MeasureAdapter(row, MeasurementBook.this);

                recyclerView.setAdapter(adapter);
                cr.close();

            }else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "You are yet to have a Client", Snackbar.LENGTH_LONG);
                View sbView = snack.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(MeasurementBook.this, R.color.colorRed));
                TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(MeasurementBook.this, R.color.colorash));
                snack.show();
            }

        }
    }



    public void retrieve(){
        dbHandler = new DbHandler(context);
        Cursor cr = dbHandler.getAll(dbHandler);

        if ((cr != null)&& cr.moveToFirst()){



                do {
                    Client client = new Client();
                    client.setId(cr.getInt(0));
                    client.setName(cr.getString(1));
                    client.setDiliveryDate(cr.getString(2));
                    client.setImage(cr.getString(3));
                    client.setImagepath(cr.getString(4));
                    row.add(client);

//                    Log.d("TAG", String.valueOf(client.getId()));
//                    Toast.makeText(getApplicationContext(),cr.getString(0),Toast.LENGTH_SHORT).show();
                }while (cr.moveToNext());

                recyclerView.setLayoutManager(new LinearLayoutManager(MeasurementBook.this));

                adapter = new MeasureAdapter(row, MeasurementBook.this);

                recyclerView.setAdapter(adapter);
            cr.close();
            }else {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "You are yet to have a Client", Snackbar.LENGTH_LONG);
            View sbView = snack.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(MeasurementBook.this, R.color.colorRed));
            TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(ContextCompat.getColor(MeasurementBook.this, R.color.colorash));
            snack.show();
    }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_measurementbook, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!(newText.isEmpty())) {
            newText = newText.toLowerCase();
            ArrayList<Client> newList = new ArrayList<>();
            for (Client client : row) {

                String name = client.getName().toLowerCase();
                if (name.contains(newText)) {
                    newList.add(client);
                }


            }
            if(newList.isEmpty()){
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Client doesn't exist", Snackbar.LENGTH_LONG);
                View sbView = snack.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(MeasurementBook.this, R.color.colorRed));
                TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(MeasurementBook.this, R.color.colorash));
                snack.show();
                return false;
            }else {
                adapter.setFilter(newList);
                return true;
            }

        }
        return  false;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainMeasure.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        super.onBackPressed();
    }


}
