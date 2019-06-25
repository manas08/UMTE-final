package cz.uhk.umte.umte_final.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;

public class FilmView extends View {
    private View root;

    private TextView issueFilmName, issueOriginalFilmName;

    public FilmView(Context context) {
        super(context);

        root = inflate(context, R.layout.film_issue, null);

        issueFilmName = root.findViewById(R.id.issueFilmName);
        issueOriginalFilmName = root.findViewById(R.id.issueOriginalFilmName);

    }

    public void setFilm(Film film){
        issueFilmName.setText(film.getNazev());
        issueOriginalFilmName.setText(film.getAnglickyNazev());
    }

    public View getRoot(){
        return root;
    }

    public void setClickListener(View.OnClickListener listener){
        root.setOnClickListener(listener);
    }

}
