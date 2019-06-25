package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;

public class Top10FilmsViewHolder extends RecyclerView.ViewHolder {
    private TextView issueFilmName, issueOriginalFilmName, filmRank, valueFilmRank;
    private LinearLayout linearLayout;

    public Top10FilmsViewHolder(@NonNull View filmView) {
        super(filmView);

        filmRank = filmView.findViewById(R.id.filmRank);
        issueFilmName = filmView.findViewById(R.id.issueTop10FilmName);
        issueOriginalFilmName = filmView.findViewById(R.id.issueTop10OriginalFilmName);
        valueFilmRank = filmView.findViewById(R.id.valueFilmRank);
        linearLayout = filmView.findViewById(R.id.oneTop10Film);
    }

    public void setFilm(Film film, int rank, float value){
        filmRank.setText(rank+".");
        issueFilmName.setText(film.getNazev());
        issueOriginalFilmName.setText(film.getAnglickyNazev());
        float c = 5;
        float pom = (float) ((value/c)*100);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        valueFilmRank.setText(df.format(pom) + "%");
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }
}
