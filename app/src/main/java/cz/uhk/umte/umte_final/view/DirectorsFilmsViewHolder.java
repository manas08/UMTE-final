package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;

public class DirectorsFilmsViewHolder extends RecyclerView.ViewHolder {
    private TextView issueFilmName;
    private LinearLayout linearLayout;

    public DirectorsFilmsViewHolder(@NonNull View castView) {
        super(castView);

        issueFilmName = castView.findViewById(R.id.issueFilmName);
        linearLayout = castView.findViewById(R.id.oneDirectorsFilm);
    }

    public void setFilm(Film film){
        issueFilmName.setText(film.getNazev());
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }
}
