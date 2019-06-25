package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Actor;
import cz.uhk.umte.umte_final.model.Film;

public class ActorViewHolder extends RecyclerView.ViewHolder {
    private TextView issueActorFirst, issueActorLast;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private View filmView;

    public ActorViewHolder(@NonNull View filmView) {
        super(filmView);
        this.filmView=filmView;

        issueActorFirst = filmView.findViewById(R.id.issueActorFirst);
        issueActorLast = filmView.findViewById(R.id.issueActorLast);
        linearLayout = filmView.findViewById(R.id.oneActor);
        imageView = filmView.findViewById(R.id.actorListImage);
    }

    public void setActor(Actor actor){
        issueActorFirst.setText(actor.getJmeno());
        issueActorLast.setText(actor.getPrijmeni());

        if (!actor.getAdresar().equals(""))
            imageView.setImageResource(filmView.getResources().getIdentifier(actor.getAdresar(), "drawable", filmView.getContext().getPackageName()));
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }

}