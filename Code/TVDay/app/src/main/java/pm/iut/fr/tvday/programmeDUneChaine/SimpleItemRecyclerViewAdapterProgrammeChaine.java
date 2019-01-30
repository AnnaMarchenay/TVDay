package pm.iut.fr.tvday.programmeDUneChaine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.chaines.ChainesDetailActivity;
import pm.iut.fr.tvday.chaines.ChainesDetailFragment;
import pm.iut.fr.tvday.masterDetail.Item;
import pm.iut.fr.tvday.masterDetail.ViewHolderProgrammeChaines;

/**
 * Created by annam on 13/02/2018.
 * RecyclerView adapter pour le contenu d'une chaine
 */

class SimpleItemRecyclerViewAdapterProgrammeChaine extends RecyclerView.Adapter<ViewHolderProgrammeChaines> {

    private final AppCompatActivity mParentActivity;
    private final List<Item> mValues;
    private final boolean mTwoPane;

    /**
     * quand clic, affiche le détail d'un programme d'une chaine
     */
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Item item = (Item) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(ChainesDetailFragment.ARG_ITEM_ID, item.id);
                ChainesDetailFragment fragment = new ChainesDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.programme_detail_container_ch, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ChainesDetailActivity.class);
                intent.putExtra(ChainesDetailFragment.ARG_ITEM_ID, item.id);
                context.startActivity(intent);

            }
        }

    };

        SimpleItemRecyclerViewAdapterProgrammeChaine(AppCompatActivity parent, List<Item> items, boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolderProgrammeChaines onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.programme_chaine_list_content, parent, false);
            return new ViewHolderProgrammeChaines(view);
        }

        @Override
        public void onBindViewHolder(ViewHolderProgrammeChaines holder, int position) {
            holder.mIdView.setText(mValues.get(position).details.heureDebut);
            holder.mContentView.setText(mValues.get(position).details.nom);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
                return mValues.size();
        }


}


