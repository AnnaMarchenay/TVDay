package pm.iut.fr.tvday.masterDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import pm.iut.fr.tvday.R;

/**
 * Created by annam on 18/02/2018.
 * ViewHolder pour une chaine
 */

public class ViewHolderChaines extends RecyclerView.ViewHolder {
    public ImageView mIdView;

    public ViewHolderChaines(View view) {
        super(view);
        mIdView = view.findViewById(R.id.content_ch);
    }
}
