package pm.iut.fr.tvday.chaines;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.masterDetail.Item;
import pm.iut.fr.tvday.masterDetail.ViewHolderChaines;
import pm.iut.fr.tvday.programmeDUneChaine.ProgrammeChaineActivity;

/**
 * Created by annam on 10/02/2018.
 * RecyclerViewAdapter pour afficher toutes les chaines
 */

public class SimpleItemRecyclerViewAdapterChaines extends RecyclerView.Adapter<ViewHolderChaines> {


    private final List<Item> mValues;

    /**
     * Quand clic sur recyclerview, affiche les émissions d'une chaine avec l'heure correspondante
     */
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Item item = (Item) view.getTag();
            Context c = view.getContext();
            Intent intent = new Intent(c, ProgrammeChaineActivity.class);
            intent.putExtra(ProgrammeChaineActivity.CHAINE, item.details.chaine);
            c.startActivity(intent);
        }
    };

    SimpleItemRecyclerViewAdapterChaines(List<Item> items){
        mValues = items;
    }

    @Override
    public ViewHolderChaines onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chaines_list_content, parent, false);
        return new ViewHolderChaines(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderChaines holder, int position) {
        switch (mValues.get(position).details.chaine.trim()) {
            case "TF1" :
                holder.mIdView.setImageResource(R.drawable.tf1);
                break;
            case "France 2" :
                holder.mIdView.setImageResource(R.drawable.france2);
                break;
            case "France 3" :
                holder.mIdView.setImageResource(R.drawable.france_3);
                break;
            case "Canal+" :
                holder.mIdView.setImageResource(R.drawable.canal);
                break;
            case "France 5" :
                holder.mIdView.setImageResource(R.drawable.france5);
                break;
            case "M6" :
                holder.mIdView.setImageResource(R.drawable.m6);
                break;
            case "Arte" :
                holder.mIdView.setImageResource(R.drawable.arte);
                break;
            case "C8" :
                holder.mIdView.setImageResource(R.drawable.c8);
                break;
            case "W9" :
                holder.mIdView.setImageResource(R.drawable.w9);
                break;
            case "TMC" :
                holder.mIdView.setImageResource(R.drawable.tmc);
                break;
            case "TF1 Séries Films et TFX" :
                holder.mIdView.setImageResource(R.drawable.tfx_tf1);
                break;
            case "NRJ 12" :
                holder.mIdView.setImageResource(R.drawable.nrj12);
                break;
            case "Public Sénat - LCP AN" :
                holder.mIdView.setImageResource(R.drawable.lcp);
                break;
            case "France 4" :
                holder.mIdView.setImageResource(R.drawable.france4);
                break;
            case "BFMTV" :
                holder.mIdView.setImageResource(R.drawable.bfmtv);
                break;
            case "CNews" :
                holder.mIdView.setImageResource(R.drawable.cnews);
                break;
            case "CStar" :
                holder.mIdView.setImageResource(R.drawable.cstar);
                break;
            case "Gulli" :
                holder.mIdView.setImageResource(R.drawable.gulli);
                break;
            case "France Ô" :
                holder.mIdView.setImageResource(R.drawable.franceo);
                break;
            case "L'Équipe" :
                holder.mIdView.setImageResource(R.drawable.lequipe);
                break;
            case "6ter" :
                holder.mIdView.setImageResource(R.drawable.sister);
                break;
            case "Numéro 23" :
                holder.mIdView.setImageResource(R.drawable.n23);
                break;
            case "RMC Découverte" :
                holder.mIdView.setImageResource(R.drawable.rmc);
                break;
            case "Chérie 25" :
                holder.mIdView.setImageResource(R.drawable.cherie25);
                break;
            default:
                holder.mIdView.setImageResource(R.drawable.tele);
        }

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
