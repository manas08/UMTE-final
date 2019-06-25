package cz.uhk.umte.umte_final.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cz.uhk.umte.umte_final.Listeners.DirectorViewListener;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Director;
import cz.uhk.umte.umte_final.view.DirectorViewHolder;

public class DirectorFeedAdapter extends RecyclerView.Adapter<DirectorViewHolder> {

    private List<Director> items;

    private DirectorViewListener listener;

    public DirectorFeedAdapter(List<Director> items, DirectorViewListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DirectorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.director_issue, viewGroup, false);
        DirectorViewHolder itemViewHolder = new DirectorViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorViewHolder itemViewHolder, int i) {
        final Director f = items.get(i);
        itemViewHolder.setDirector(f);

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
