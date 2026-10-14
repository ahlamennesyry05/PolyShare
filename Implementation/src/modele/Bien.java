package modele;

// Annonce de type bien (pret, don, ...).

public class Bien extends Annonce {

    private String typeTransaction;

    public Bien(int idAnnonce, String titre, String description, String categorie,
                Utilisateur auteur, String typeTransaction) {
        super(idAnnonce, titre, description, categorie, auteur);
        this.typeTransaction = typeTransaction;
    }

    public String getTypeTransaction() { return typeTransaction; }

    @Override
    public String toString() {
        return "Bien " + super.toString() + " - " + typeTransaction;
    }
}
