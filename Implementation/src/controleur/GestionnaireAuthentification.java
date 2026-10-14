package controleur;

import dao.Depot;
import modele.Utilisateur;

public class GestionnaireAuthentification {

    private Depot depot;

    public GestionnaireAuthentification(Depot depot) {
        this.depot = depot;
    }

    public Utilisateur seConnecter(String email, String mdp) {
        Utilisateur u = depot.chercherParEmail(email);
        if (verifierMdp(u, mdp)) {
            String role = determinerRole(u);
            System.out.println(" Connexion réussie : " + u + " => rôle " + role);
            return u;
        }
        System.out.println(" Échec de connexion pour : " + email);
        return null;
    }

    public String determinerRole(Utilisateur u) {
        return u.getRole();
    }

    public boolean verifierMdp(Utilisateur u, String mdp) {
        return u != null && u.seConnecter(u.getEmail(), mdp);
    }
}
