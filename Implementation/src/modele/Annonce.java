package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Classe abstraite Annonce : specialisee en Bien et Service.
public abstract class Annonce {

    protected int idAnnonce;
    protected String titre;
    protected String description;
    protected String categorie;
    protected StatutAnnonce statut;

    private Utilisateur auteur;
    private final List<Demande> demandes = new ArrayList<>();

    public Annonce(int idAnnonce, String titre, String description,
                   String categorie, Utilisateur auteur) {
        this.idAnnonce = idAnnonce;
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.auteur = auteur;
        this.statut = StatutAnnonce.INDISPONIBLE; // tant qu'elle n'est pas publiee
    }

    // Rend l'annonce visible et disponible.
    public void publierAnnonce() {
        this.statut = StatutAnnonce.DISPONIBLE;
    }

    public void changerStatut(StatutAnnonce s) {
        this.statut = s;
    }

    public void ajouterDemande(Demande d) {
        demandes.add(d);
    }

    public int getIdAnnonce() { return idAnnonce; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getCategorie() { return categorie; }
    public StatutAnnonce getStatut() { return statut; }
    public Utilisateur getAuteur() { return auteur; }
    public List<Demande> getDemandes() { return Collections.unmodifiableList(demandes); }

    @Override
    public String toString() {
        return "[#" + idAnnonce + "] " + titre + " (" + statut + ")";
    }
}
