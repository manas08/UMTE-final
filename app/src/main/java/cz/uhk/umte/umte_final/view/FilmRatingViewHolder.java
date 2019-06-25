package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Ratings;

public class FilmRatingViewHolder extends RecyclerView.ViewHolder {
    private TextView issueFilmRatingLogin, issueFilmRatingText;
    private RatingBar issueFilmRating;

    public FilmRatingViewHolder(@NonNull View filmView) {
        super(filmView);

        issueFilmRatingLogin = filmView.findViewById(R.id.issueFilmRatingLogin);
        issueFilmRating = filmView.findViewById(R.id.issueFilmRating);
        issueFilmRatingText = filmView.findViewById(R.id.issueFilmRatingText);
    }

    public void setFilm(Ratings film){
        issueFilmRatingLogin.setText(film.getUser());
        issueFilmRatingText.setText(film.getRatingText());
        issueFilmRating.setRating(film.getStars());
    }
}