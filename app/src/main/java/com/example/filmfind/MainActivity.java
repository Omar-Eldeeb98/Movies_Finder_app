package com.example.filmfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue queue;

    private AlertDialog.Builder alertDialog; //_____________________alert dialog to exit app
    private AlertDialog dialog;
    private EditText searchEditText;
    private Button confirmButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        getSupportActionBar().setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar().setCustomView( R.layout.action_bar_custom );


        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        Prefs prefs = new Prefs( MainActivity.this );  //---------------------------  object from class Prefs
        String search = prefs.getSearch(); //---------------------------------------------  get the default search value from the class

        movies = new ArrayList<>();
        queue = Volley.newRequestQueue( this );

        parseJson( search );//-------------------------------------------------------------- put the search in method


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //__________ start of option menu method 1 _______________//
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.option_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //__________  option menu method 2 _______________//
        int id = item.getItemId();
        if (id == R.id.item_1)
        {
            exitApp();
            // Toast.makeText( this , "item 1 selected" , Toast.LENGTH_SHORT ).show();

        }
        if (id==R.id.item_2)
        {
            showSearchDialog();
            //Toast.makeText( this, "item 2 selected", Toast.LENGTH_SHORT ).show();
        }



        return super.onOptionsItemSelected( item );
    }


    private void exitApp()
    {   //____________________________ for alert dialog


        // show the actual dialog (alert dialog)
        alertDialog = new AlertDialog.Builder( MainActivity.this );

        //set things up  - set title
        alertDialog.setTitle( R.string.title );
        alertDialog.setIcon( android.R.drawable.ic_delete );

        //  - set message
        alertDialog.setMessage( R.string.message );

        // -  set Cancel label
        alertDialog.setCancelable( false );

        //   - set positive button
        alertDialog.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit our window activity
                MainActivity.this.finish();
            }
        } );

        //  - set negative button
        alertDialog.setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // stay in our window activity
                dialog.cancel();
            }
        } );


        // create the actual dialog
        AlertDialog d = alertDialog.create();

        //show the dialog
        d.show();


    }


    private void showSearchDialog() {  //_______________________________search films Custom Dialog

        alertDialog = new AlertDialog.Builder( MainActivity.this );
        View view = getLayoutInflater().inflate( R.layout.dialog_view_search, null );
        searchEditText = (EditText) view.findViewById( R.id.edit_text_search );
        confirmButton = (Button) view.findViewById( R.id.confirm_search_button );
        alertDialog.setView( view );
        dialog = alertDialog.create();
        dialog.show();

        confirmButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Prefs prefs = new Prefs( MainActivity.this );
                if (!searchEditText.getText().toString().isEmpty()) {
                    String search = searchEditText.getText().toString();
                    prefs.setSearch( search );
                    movies.clear();
                    parseJson( search );
                    customAdapter.notifyDataSetChanged(); // ................ very important...................

                }
                dialog.dismiss();
            }
        } );

    }


    private void parseJson(String searchTerm) {

        String url = "http://www.omdbapi.com/?apikey=d521cddf&page=1&s=" + searchTerm;
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray( "Search" );
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                String image__ = jsonObject.getString( "Poster" );
                                String title__ = jsonObject.getString( "Title" );
                                String year__ = jsonObject.getString( "Year" );
                                String type__ = jsonObject.getString( "Type" );
                                String imdbID__ = jsonObject.getString( "imdbID" );


                                //  Log.d( "mesg" , title__ );

                                Movie m = new Movie( image__, title__, year__, type__, imdbID__ );
                                movies.add( m );

                            }
                            customAdapter = new CustomAdapter( MainActivity.this, movies );
                            recyclerView.setAdapter( customAdapter );
                            customAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } );

        queue.add( request );

    }

}
