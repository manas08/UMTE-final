package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Actor;

public class Top10ActorsViewHolder extends RecyclerView.ViewHolder {
    private TextView issueFirstName, issueLastName, actorRank, valueActorRank;
    private LinearLayout linearLayout;

    public Top10ActorsViewHolder(@NonNull View filmView) {
        super(filmView);

        actorRank = filmView.findViewById(R.id.actorRank);
        valueActorRank = filmView.findViewById(R.id.valueActorRank);
        issueFirstName = filmView.findViewById(R.id.issueTop10ActorFirstName);
        issueLastName = filmView.findViewById(R.id.issueTop10ActorsLastName);
        linearLayout = filmView.findViewById(R.id.oneTop10Actor);
    }

    public void setActor(Actor actor, int rank, float value){
        actorRank.setText(rank+".");
        issueFirstName.setText(actor.getJmeno());
        issueLastName.setText(actor.getPrijmeni());
        float c = 5;
        float pom = (float) ((value/c)*100);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        valueActorRank.setText(df.format(pom) + "%");
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }
}
