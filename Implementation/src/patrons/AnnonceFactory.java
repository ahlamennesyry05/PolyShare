package patrons;

import modele.Annonce;
import modele.Bien;
import modele.Service;
import modele.Utilisateur;

// Patron Simple Factory : creation uniforme des annonces (BIEN ou SERVICE).

public class AnnonceFactory {

    public Annonce creerAnnonce(String type, int id, String titre, String description,
                                String categorie, Utilisateur auteur, String specifique) {
        if ("BIEN".equalsIgnoreCase(type)) {
            return new Bien(id, titre, description, categorie, auteur, specifique);
        } else if ("SERVICE".equalsIgnoreCase(type)) {
            return new Service(id, titre, description, categorie, auteur, specifique);
        }
        throw new IllegalArgumentException("Type d'annonce inconnu : " + type);
    }
}
