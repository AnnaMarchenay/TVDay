package pm.iut.fr.tvday.programmes;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pm.iut.fr.tvday.programmeDUneChaine.Program;
import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.masterDetail.Content;


public class ProgrammeDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Program mItem;

    public ProgrammeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = Content.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.chaine);
            }
        }
    }

    @Override
    public void onResume() {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.chaine);
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.programme_detail, container, false);

        if (mItem != null) {
            String titre = mItem.nom + " (" + mItem.category + ")";
            ((TextView) rootView.findViewById(R.id.programme_titre)).setText(titre);
            ((TextView) rootView.findViewById(R.id.programme_description)).setText(mItem.description);
            ((TextView) rootView.findViewById(R.id.programme_lien)).setText(mItem.link);
            ((TextView) rootView.findViewById(R.id.programme_note)).setText(mItem.comments);
        }

        return rootView;
    }
}
