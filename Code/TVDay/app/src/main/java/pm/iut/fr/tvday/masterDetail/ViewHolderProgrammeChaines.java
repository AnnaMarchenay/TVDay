package pm.iut.fr.tvday.masterDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pm.iut.fr.tvday.R;

/**
 * Created by annam on 18/02/2018.
 * ViewHolder pour le programme d'une chaine
 */

public class ViewHolderProgrammeChaines extends RecyclerView.ViewHolder {
    public TextView mIdView;
    public TextView mContentView;

    public ViewHolderProgrammeChaines(View view) {
        super(view);
        mIdView = view.findViewById(R.id.heure_pch);
        mContentView = view.findViewById(R.id.content_pch);

    }
}