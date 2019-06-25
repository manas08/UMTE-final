package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.ActorViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Actor;
import cz.uhk.umte.umte_final.view.ActorViewHolder;

public class ActorFeedAdapter extends RecyclerView.Adapter<ActorViewHolder> {

    private List<Actor> items;

    private ActorViewListener listener;

    public ActorFeedAdapter(List<Actor> items, ActorViewListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.actor_issue, viewGroup, false);
        ActorViewHolder itemViewHolder = new ActorViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder itemViewHolder, int i) {
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
        return items.size();
    }


}
