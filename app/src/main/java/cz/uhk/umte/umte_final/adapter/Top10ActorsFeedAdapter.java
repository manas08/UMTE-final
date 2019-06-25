package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.Top10ActorsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Actor;
import cz.uhk.umte.umte_final.view.Top10ActorsViewHolder;

public class Top10ActorsFeedAdapter extends RecyclerView.Adapter<Top10ActorsViewHolder> {

    private List<Actor> items;
    private List<Float> ranks;
    private List<Integer> values;

    private Top10ActorsViewListener listener;


    public Top10ActorsFeedAdapter(List<Actor> items, Top10ActorsViewListener listener, List<Float> value) {
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
    public Top10ActorsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top10actors_issue, viewGroup, false);
        Top10ActorsViewHolder itemViewHolder = new Top10ActorsViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Top10ActorsViewHolder itemViewHolder, int i) {
        final Actor f = items.get(i);
        int v = values.get(i);
        float r = ranks.get(i);
        itemViewHolder.setActor(f, v, r);

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
