package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.Top10FilmsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Film;
import cz.uhk.umte.umte_final.view.Top10FilmsViewHolder;

public class Top10FilmsFeedAdapter extends RecyclerView.Adapter<Top10FilmsViewHolder> {

    private List<Film> items;
    private List<Integer> values;
    private List<Float> ranks;
    private Top10FilmsViewListener listener;

    public Top10FilmsFeedAdapter(List<Film> items, Top10FilmsViewListener listener, List<Float> value) {
        this.items = items;
        this.listener = listener;
        this.ranks = value;
        int v = 1;
        values = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            values.add(v);
            v++;
        }
    }

    @NonNull
    @Override
    public Top10FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top10films_issue, viewGroup, false);
        Top10FilmsViewHolder itemViewHolder = new Top10FilmsViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Top10FilmsViewHolder itemViewHolder, int i) {
        final Film f = items.get(i);
        int v = values.get(i);
        float r = ranks.get(i);
        itemViewHolder.setFilm(f, v, r);

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
