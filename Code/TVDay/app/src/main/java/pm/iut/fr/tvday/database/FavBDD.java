package pm.iut.fr.tvday.database;

/**
 * Created by Wandy on 13/02/2018.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pm.iut.fr.tvday.chaines.ChainesDetailActivity;

public class FavBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "fav.db";

    private static final String TABLE_FAV = "table_fav";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_CHAINE = "chaine";
    private static final int NUM_COL_CHAINE = 1;
    private static final String COL_EMISSION = "emission";
    private static final int NUM_COL_EMISSION = 2;

    private SQLiteDatabase bdd;

    private BaseSQLlite maBaseSQLite;

    public FavBDD(ChainesDetailActivity context){
        //On créer la BDD et sa table
        maBaseSQLite = new BaseSQLlite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertFav(CharSequence chaine, CharSequence emission, Integer etat){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_CHAINE, (String) chaine);
        values.put(COL_EMISSION, (String) emission);
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_FAV, null, values);
    }

    public int updateFav(int id, Integer etat){

        ContentValues values = new ContentValues();
        //values.put(String.valueOf(COL_ETAT), etat);

        return bdd.update(TABLE_FAV, values, COL_ID + " = " +id, null);
    }

    public int removeFavWithID(int id){

        return bdd.delete(TABLE_FAV, COL_ID + " = " +id, null);
    }

    public boolean getLivreWithTitre(String titre){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_FAV, new String[] {COL_ID, COL_CHAINE, COL_EMISSION}, COL_EMISSION + " LIKE \"" + titre +"\"", null, null, null, null);

        if (c!= null)
            return true;
        else
            return false;

    }



}
