package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.ActorsFilmsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;
import cz.uhk.umte.umte_final.view.ActorsFilmsViewHolder;

public class ActorsFilmsFeedAdapter extends RecyclerView.Adapter<ActorsFilmsViewHolder> {

    private List<Film> items;

    private ActorsFilmsViewListener listener;

    public ActorsFilmsFeedAdapter(List<Film> items, ActorsFilmsViewListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActorsFilmsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.actors_films_issue, viewGroup, false);
        ActorsFilmsViewHolder itemViewHolder = new ActorsFilmsViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorsFilmsViewHolder itemViewHolder, int i) {
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
        if (items != null)
            return items.size();
        else
            return 0;
    }
}
