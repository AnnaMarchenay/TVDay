package pm.iut.fr.tvday.database;

/**
 * Created by Wandy on 13/02/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseSQLlite extends SQLiteOpenHelper {

    private static final String TABLE_FAV = "table_fav";
    private static final String COL_ID = "ID";
    private static final String COL_CHAINE = "chaine";
    private static final String COL_EMISSION = "emission";


    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_FAV + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_CHAINE + " TEXT NOT NULL, "
            + COL_EMISSION + " TEXT NOT NULL);";

    public BaseSQLlite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE_FAV + ";");
        onCreate(db);
    }

}
