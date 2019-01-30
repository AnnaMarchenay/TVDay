package pm.iut.fr.tvday.programmeDUneChaine;

/**
 * Created by annam on 30/01/2018.
 * Classe Program composé d'un lien hypertexte, d'une catégorie,
 * d'une description, d'une note, d'une chaine, d'une heure de début et d'un nom
 */

public class Program {
    public final String link;
    public final String category;
    public final String description;
    public final String comments;
    public final String chaine;
    public final String heureDebut;
    public final String nom;


    public Program(String link, String category, String description, String comments, String chaine, String heureDebut, String nom) {
        this.link = link;
        this.category = category;
        this.description = description;
        this.comments = comments;
        this.chaine=chaine;
        this.heureDebut=heureDebut;
        this.nom=nom;
    }

    @Override
    public String toString() {
        return chaine+heureDebut+nom+link+category+description+comments;
    }
}