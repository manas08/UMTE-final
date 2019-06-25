package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Actor;
import cz.uhk.umte.umte_final.model.Film;

public class CastViewHolder extends RecyclerView.ViewHolder {
    private TextView issueActorFirstName, issueActorLastName;
    private LinearLayout linearLayout;

    public CastViewHolder(@NonNull View castView) {
        super(castView);

        issueActorFirstName = castView.findViewById(R.id.issueActorFirstName);
        issueActorLastName = castView.findViewById(R.id.issueActorLastName);
        linearLayout = castView.findViewById(R.id.oneCast);
    }

    public void setActor(Actor actor){
        issueActorFirstName.setText(actor.getJmeno());
        issueActorLastName.setText(actor.getPrijmeni());
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }
}
