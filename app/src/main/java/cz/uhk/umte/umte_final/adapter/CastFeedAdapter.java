package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.uhk.umte.umte_final.Listeners.CastViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Actor;
import cz.uhk.umte.umte_final.view.CastViewHolder;

public class CastFeedAdapter extends RecyclerView.Adapter<CastViewHolder> {

    private List<Actor> items;

    private CastViewListener listener;

    public CastFeedAdapter(List<Actor> items, CastViewListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.cast_issue, viewGroup, false);
        CastViewHolder itemViewHolder = new CastViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder itemViewHolder, int i) {
        final Actor f = items.get(i);
        itemViewHolder.setActor(f);

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
