package modele;

// Annonce de type service (offre ou demande de tutorat, covoiturage, ...).

public class Service extends Annonce {

    private String disponibilite;

    public Service(int idAnnonce, String titre, String description, String categorie,
                   Utilisateur auteur, String disponibilite) {
        super(idAnnonce, titre, description, categorie, auteur);
        this.disponibilite = disponibilite;
    }

    public String getDisponibilite() { return disponibilite; }

    @Override
    public String toString() {
        return "Service " + super.toString() + " - " + disponibilite;
    }
}
