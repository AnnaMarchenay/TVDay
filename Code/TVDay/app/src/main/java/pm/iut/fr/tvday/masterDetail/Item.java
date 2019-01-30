package pm.iut.fr.tvday.masterDetail;


/**
 * Created by annam on 08/02/2018.
 * Un item contient son id, son contenut et ses détails
 */

import pm.iut.fr.tvday.programmeDUneChaine.Program;

public class Item {
    public final String id;
    public final String content;
    public final Program details;

    Item(Program details, String id) {
        this.id = id;
        this.content = details.nom;
        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }

}
