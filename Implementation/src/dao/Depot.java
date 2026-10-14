package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modele.Annonce;
import modele.Utilisateur;
import patrons.Observateur;

// DAO : stockage en memoire des utilisateurs, annonces.
public class Depot {

    private List<Utilisateur> utilisateurs = new ArrayList<>();
    private List<Annonce> annonces = new ArrayList<>();

    public void ajouterUtilisateur(Utilisateur u) {
        if (chercherParEmail(u.getEmail()) != null) {
            throw new IllegalArgumentException("Email deja utilise : " + u.getEmail());
        }
        utilisateurs.add(u);
    }

    public void ajouterAnnonce(Annonce a) {
        annonces.add(a);
    }

    public void supprimerAnnonce(Annonce a) {
        annonces.remove(a);
    }

    // Retourne l'utilisateur ayant cet email, ou null s'il n'existe pas.
    public Utilisateur chercherParEmail(String email) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    // Version simple : tous les utilisateurs sauf l'auteur de l'annonce.
    public List<Observateur> rechercherObservateursConcernes(Annonce annonce) {
        List<Observateur> concernes = new ArrayList<>();
        for (Utilisateur u : utilisateurs) {
            if (u != annonce.getAuteur()) {
                concernes.add(u);
            }
        }
        return concernes;
    }

    public List<Utilisateur> getUtilisateurs() { return Collections.unmodifiableList(utilisateurs); }
    public List<Annonce> getAnnonces() { return Collections.unmodifiableList(annonces); }
}
