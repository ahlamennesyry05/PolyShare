package patrons;

import java.util.List;

import dao.Depot;
import modele.Annonce;
import modele.Utilisateur;

// Sujet concret du patron Observateur.
// Il cree les annonces via la Factory, les enregistre dans le Depot,
// puis notifie les observateurs concernes.

public class PolyShareSubject extends Sujet {

    private AnnonceFactory annonceFactory = new AnnonceFactory();
    private Depot depot;
    private int prochainId = 1;

    public PolyShareSubject(Depot depot) {
        this.depot = depot;
    }

    // Enseignant (ou etudiant) propose un service / un pret.
    public Annonce proposerService(Utilisateur auteur, String titre, String description,
                                   String categorie, String disponibilite) {
        return creerEtDiffuser("SERVICE", auteur, titre, description, categorie,
                disponibilite, "Nouveau service propose par " + auteur.getPrenom());
    }

    // Utilisateur demande un service.
    public Annonce demanderService(Utilisateur auteur, String titre, String description,
                                   String categorie, String disponibilite) {
        return creerEtDiffuser("SERVICE", auteur, titre, description, categorie,
                disponibilite, "Nouvelle demande de service de " + auteur.getPrenom());
    }

    public Annonce publierAnnonce(String type, Utilisateur auteur, String titre, String description,
                                  String categorie, String typeTransaction) {
        return creerEtDiffuser(type, auteur, titre, description, categorie,
                typeTransaction, "Nouvelle annonce publiee par " + auteur.getPrenom());
    }

    // Observateurs concernes par une annonce (delegue au Depot).
    public List<Observateur> rechercherObservateursConcernes(Annonce annonce) {
        return depot.rechercherObservateursConcernes(annonce);
    }

    private Annonce creerEtDiffuser(String type, Utilisateur auteur, String titre,
                                    String description, String categorie,
                                    String specifique, String details) {
        // Creation via la Simple Factory
        Annonce annonce = annonceFactory.creerAnnonce(type, prochainId++, titre,
                description, categorie, auteur, specifique);
        annonce.publierAnnonce();
        auteur.ajouterAnnonce(annonce);
        depot.ajouterAnnonce(annonce);

        // Recherche des observateurs concernes, puis notification
        observateurs.clear();
        for (Observateur o : rechercherObservateursConcernes(annonce)) {
            enregistrerObservateur(o);
        }
        notifierObservateurs(annonce, details);
        return annonce;
    }
}
