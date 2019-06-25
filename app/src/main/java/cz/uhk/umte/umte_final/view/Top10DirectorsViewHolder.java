package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Director;

public class Top10DirectorsViewHolder extends RecyclerView.ViewHolder {
    private TextView issueFirstName, issueLastName, filmRank, valueDirectorRank;
    private LinearLayout linearLayout;

    public Top10DirectorsViewHolder(@NonNull View filmView) {
        super(filmView);

        filmRank = filmView.findViewById(R.id.directorRank);
        valueDirectorRank = filmView.findViewById(R.id.valueDirectorRank);
        issueFirstName = filmView.findViewById(R.id.issueTop10DirectorFirstName);
        issueLastName = filmView.findViewById(R.id.issueTop10DirectorsLastName);
        linearLayout = filmView.findViewById(R.id.oneTop10Director);
    }

    public void setDirector(Director director, int rank, float value){
        filmRank.setText(rank+".");
        issueFirstName.setText(director.getJmeno());
        issueLastName.setText(director.getPrijmeni());
        float c = 5;
        float pom = (float) ((value/c)*100);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        valueDirectorRank.setText(df.format(pom) + "%");
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }
}
