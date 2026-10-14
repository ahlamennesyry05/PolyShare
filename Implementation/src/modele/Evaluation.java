package modele;

// Evaluation (note + commentaire) laissee apres un echange termine. */
public class Evaluation {

    private int note;
    private String commentaire;
    private Utilisateur auteur;   // celui qui redige (Utilisateur "redige" Evaluation)
    private Utilisateur evalue;   // celui qui recoit (Utilisateur "recoit" Evaluation)

    public Evaluation(int note, String commentaire, Utilisateur auteur, Utilisateur evalue) {
        this.note = note;
        this.commentaire = commentaire;
        this.auteur = auteur;
        this.evalue = evalue;
    }

    public int getNote() { return note; }
    public String getCommentaire() { return commentaire; }
    public Utilisateur getAuteur() { return auteur; }
    public Utilisateur getEvalue() { return evalue; }

    @Override
    public String toString() {
        return note + "/5 - " + commentaire;
    }
}
