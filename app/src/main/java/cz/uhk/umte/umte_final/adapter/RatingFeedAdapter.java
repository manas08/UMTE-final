package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Ratings;
import cz.uhk.umte.umte_final.view.FilmRatingViewHolder;

public class RatingFeedAdapter extends RecyclerView.Adapter<FilmRatingViewHolder> {

    private List<Ratings> items;

    public RatingFeedAdapter(List<Ratings> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmRatingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.film_rating_issue, viewGroup, false);
        FilmRatingViewHolder itemViewHolder = new FilmRatingViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmRatingViewHolder filmRatingViewHolder, int i) {
        final Ratings f = items.get(i);
        filmRatingViewHolder.setFilm(f);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
