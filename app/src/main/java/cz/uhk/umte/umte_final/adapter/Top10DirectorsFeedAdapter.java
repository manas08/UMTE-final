package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.Top10DirectorsViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Director;
import cz.uhk.umte.umte_final.view.Top10DirectorsViewHolder;

public class Top10DirectorsFeedAdapter extends RecyclerView.Adapter<Top10DirectorsViewHolder> {

    private List<Director> items;
    private List<Float> ranks;
    private List<Integer> values;

    private Top10DirectorsViewListener listener;

    public Top10DirectorsFeedAdapter(List<Director> items, Top10DirectorsViewListener listener, List<Float> value) {
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
    public Top10DirectorsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top10directors_issue, viewGroup, false);
        Top10DirectorsViewHolder itemViewHolder = new Top10DirectorsViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Top10DirectorsViewHolder itemViewHolder, int i) {
        final Director f = items.get(i);
        int v = values.get(i);
        float r = ranks.get(i);
        itemViewHolder.setDirector(f, v, r);

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
