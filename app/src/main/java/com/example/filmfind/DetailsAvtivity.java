package com.example.filmfind;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsAvtivity extends AppCompatActivity {

    private Movie movie;
    private ImageView moviePoster;
    private TextView Title;
    private TextView year;
    private TextView director;
    private TextView actors;
    private TextView category;
    private TextView rating;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private TextView runTime;

    private RequestQueue requestQueue;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_details_avtivity );
        requestQueue = Volley.newRequestQueue( this );


       // getSupportActionBar().setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM );
      //  getSupportActionBar().setCustomView( R.layout.action_bar_custom );

        movie = (Movie) getIntent().getSerializableExtra( "movie" );
        movieId = movie.getImdbID();
        setupUi();
        getMovieDetails(movieId);








    }

    private void getMovieDetails(String id) {
        String url = "http://www.omdbapi.com/?apikey=d521cddf&i=" + id +"&page=1";
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has( "Ratings" ))
                    {
                        JSONArray ratings = response.getJSONArray( "Ratings" );
                        String source = null;
                        String value = null;
                        if (ratings.length()>0)
                        {
                            JSONObject mRatings  = ratings.getJSONObject( ratings.length()-1 );
                            source = mRatings.getString( "Source" );
                            value = mRatings.getString( "Value" );
                            rating.setText( source + ":" + value );

                        }
                        else
                        {
                            rating.setText( "Rating: N/A " );
                        }

                        Title.setText( response.getString( "Title" ) );
                        year.setText( "Released in: "  + response.getString( "Year" ) );
                        director.setText("Director: " +   response.getString( "Director" ) );
                        writers.setText("-------------"+ "\n" +  "Writers:"  + "\n" + "-------------"+ "\n" + response.getString( "Writer" ) );
                        plot.setText( "-------------"+ "\n" + "Plots:"  + "\n" + "-------------"+ "\n" +  response.getString( "Plot" ) );
                        runTime.setText("Runtime:"  + response.getString( "Runtime" ) );

                        actors.setText("-------------"+ "\n" + "Actors:"  + "\n" + "-------------"+ "\n" +   response.getString( "Actors" ) );
                        category.setText( "Category:"  + response.getString( "Type" ) );
                        boxOffice.setText("-------------"+ "\n" + "Box Office:"  + "\n" + "-------------"+ "\n" +  response.getString( "BoxOffice" ) );

                        Picasso.get().load( response.getString( "Poster" ) ).fit().centerInside().into( moviePoster );

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } );


        requestQueue.add( request );

    }

    private void setupUi() {
        moviePoster = (ImageView) findViewById( R.id.image_det );
        Title = findViewById( R.id.title_det );
        year = findViewById( R.id.released_det );
        director = findViewById( R.id.directedBy_det );
        actors = findViewById( R.id.actors_det );
        category = findViewById( R.id.category_det );
        rating = findViewById( R.id.rating_det );
        writers = findViewById( R.id.writers_det );
        plot = findViewById( R.id.plot_det );
        boxOffice = findViewById( R.id.boxOffice_det );
        runTime = findViewById( R.id.runTime_det );

    }
}
