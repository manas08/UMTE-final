package cz.uhk.umte.umte_final.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.uhk.umte.umte_final.R;
import cz.uhk.umte.umte_final.model.Director;

public class DirectorViewHolder extends RecyclerView.ViewHolder {
    private TextView issueDirectorFirst, issueDirectorLast;
    private LinearLayout linearLayout;

    public DirectorViewHolder(@NonNull View filmView) {
        super(filmView);

        issueDirectorFirst = filmView.findViewById(R.id.issueDirectorFirst);
        issueDirectorLast = filmView.findViewById(R.id.issueDirectorLast);
        linearLayout = filmView.findViewById(R.id.oneDirector);
    }

    public void setDirector(Director director){
        issueDirectorFirst.setText(director.getJmeno());
        issueDirectorLast.setText(director.getPrijmeni());
    }

    public void setClickListener(View.OnClickListener listener) {
        linearLayout.setOnClickListener(listener);
    }
}
