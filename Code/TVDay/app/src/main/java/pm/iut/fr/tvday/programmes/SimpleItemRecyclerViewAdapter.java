package pm.iut.fr.tvday.programmes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.masterDetail.Item;

/**
 * Created by annam on 09/02/2018.
 * RecycleViewAdapter pour afficher les programmes du moment
 */

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final AppCompatActivity mParentActivity;

    private final List<Item> mValues;
    private final boolean mTwoPane;

    /**
     * quand clic, affiche les détails du programme
     */
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Item item = (Item) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(ProgrammeDetailFragment.ARG_ITEM_ID, item.id);
                ProgrammeDetailFragment fragment = new ProgrammeDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.programme_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ProgrammeDetailActivity.class);
                intent.putExtra(ProgrammeDetailFragment.ARG_ITEM_ID, item.id);

                context.startActivity(intent);
            }
        }
    };

    SimpleItemRecyclerViewAdapter(AppCompatActivity parent,List<Item> items,boolean twoPane){
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.programme_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).details.chaine);
        holder.mContentView.setText(mValues.get(position).content);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView =  view.findViewById(R.id.id_text);
            mContentView = view.findViewById(R.id.content);
        }
    }


}
