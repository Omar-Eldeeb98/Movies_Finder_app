package com.example.filmfind;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> movieArrayList;

    public CustomAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( context ).inflate( R.layout.movie_row , parent , false );
        return new ViewHolder(v , context);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        Movie currentMovie = movieArrayList.get(position);
        String imgUrl_ = currentMovie.getImageUrl();
        String title_ = currentMovie.getTitle();
        String year_ = currentMovie.getYear();
        String type_ = currentMovie.getType();


        Picasso.get().load( imgUrl_ ).fit().centerInside().into( holder.movieImageView );
        holder.titleTextView.setText( title_ );
        holder.yearTextView.setText( "Released In: " + year_ );
        holder.typeTextView.setText("Category: " +  type_ );

    }

    @Override
    public int getItemCount() {

        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView movieImageView;
        public TextView titleTextView;
        public TextView yearTextView;
        public TextView typeTextView;



        public ViewHolder(@NonNull View itemView , final Context ctx) {
            super( itemView );
            context = ctx;
            movieImageView = (ImageView) itemView.findViewById( R.id.movieImageView );
            titleTextView = (TextView) itemView.findViewById( R.id.titleTextView );
            yearTextView = (TextView) itemView.findViewById( R.id.yearTextView );
            typeTextView = (TextView) itemView.findViewById( R.id.typeTextView );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = movieArrayList.get( getAdapterPosition() ); //------------------------------------
                    Intent intent = new Intent( context , DetailsAvtivity.class );
                    intent.putExtra( "movie" ,  movie );
                    ctx.startActivity( intent );

                   //Toast.makeText( context , "row tapped!!" , Toast.LENGTH_SHORT).show();

                }
            } );

        }

        @Override
        public void onClick(View v) {

        }
    }
}
