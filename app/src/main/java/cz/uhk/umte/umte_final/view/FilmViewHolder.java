package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;

public class FilmViewHolder extends RecyclerView.ViewHolder {
    private TextView issueFilmName, issueOriginalFilmName;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private View filmView;

    public FilmViewHolder(@NonNull View filmView) {
        super(filmView);
        this.filmView=filmView;

        issueFilmName = filmView.findViewById(R.id.issueFilmName);
        issueOriginalFilmName = filmView.findViewById(R.id.issueOriginalFilmName);
        linearLayout = filmView.findViewById(R.id.oneFilm);
        imageView = filmView.findViewById(R.id.filmListImage);
    }

    public void setFilm(Film film){
        issueFilmName.setText(film.getNazev());
        issueOriginalFilmName.setText(film.getAnglickyNazev());

        if (!film.getAdresar().equals(""))
            imageView.setImageResource(filmView.getResources().getIdentifier(film.getAdresar(), "drawable", filmView.getContext().getPackageName()));
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }

}