package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.FilmViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;
import cz.uhk.umte.umte_final.view.FilmViewHolder;

public class FilmFeedAdapter extends RecyclerView.Adapter<FilmViewHolder> {

    private List<Film> items;

    private FilmViewListener listener;

    public FilmFeedAdapter(List<Film> items, FilmViewListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.film_issue, viewGroup, false);
        FilmViewHolder itemViewHolder = new FilmViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder itemViewHolder, int i) {
        final Film f = items.get(i);
        itemViewHolder.setFilm(f);

        itemViewHolder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked(f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
