package pm.iut.fr.tvday.chaines;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pm.iut.fr.tvday.programmeDUneChaine.Program;
import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.masterDetail.Content;


public class ChainesDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Program mItem;

    public ChainesDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            String s= getArguments().getString(ARG_ITEM_ID);
            mItem = Content.ITEM_MAP.get(s);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout_ch);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.chaine);
            }
        }
    }

    @Override
    public void onResume() {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout_ch);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.chaine);
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chaines_detail, container, false);

        if (mItem != null) {
            String titre = mItem.heureDebut + " " + mItem.nom + " (" + mItem.category + ")";
            ((TextView) rootView.findViewById(R.id.programme_titre_ch)).setText(titre);
            ((TextView) rootView.findViewById(R.id.programme_description_ch)).setText(mItem.description);
            ((TextView) rootView.findViewById(R.id.programme_lien_ch)).setText(mItem.link);
            ((TextView) rootView.findViewById(R.id.chaine_note)).setText(mItem.comments);

        }

        return rootView;
    }
}
