package modele;

import dao.Depot;

// Administrateur : cree les comptes (pas d'auto-inscription, emails institutionnels) et modere les annonces.

public class Admin {

    public static final String MOT_DE_PASSE_PAR_DEFAUT = "Polyshare123";
    public static final String FILIERE_PAR_DEFAUT = "Non renseignee";
    public static final String DEP_PAR_DEFAUT = "Non renseignee";

    private int idAdmin;
    private String nom;
    private String prenom;
    private String email;
    private Depot depot;

    public Admin(int idAdmin, String nom, String prenom, String email, Depot depot) {
        this.idAdmin = idAdmin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.depot = depot;
    }

    // Cree un compte ETUDIANT ou ENSEIGNANT et l'enregistre dans le depot.
    public Utilisateur creerCompte(String nom, String prenom, String email, String role) {
        int id = depot.getUtilisateurs().size() + 1;
        Utilisateur u;
        if ("ENSEIGNANT".equalsIgnoreCase(role)) {
            u = new Enseignant(id, prenom, nom, email, MOT_DE_PASSE_PAR_DEFAUT, FILIERE_PAR_DEFAUT);
        } else if ("ETUDIANT".equalsIgnoreCase(role)) {
            u = new Etudiant(id, prenom, nom, email, MOT_DE_PASSE_PAR_DEFAUT, DEP_PAR_DEFAUT);
        } else {
            throw new IllegalArgumentException("Role inconnu : " + role);
        }
        depot.ajouterUtilisateur(u);
        return u;
    }

    // Valide une annonce : elle devient visible et disponible.
    public void validerAnnonce(Annonce a) {
        a.publierAnnonce();
    }

    // Supprime une annonce de la plateforme.
    public void supprimerAnnonce(Annonce a) {
        depot.supprimerAnnonce(a);
    }

    // Gestion de compte : reinitialise le mot de passe de l'utilisateur.
    public void gererCompte(Utilisateur u) {
        u.setMotDePasse(MOT_DE_PASSE_PAR_DEFAUT);
    }

    public int getIdAdmin() { return idAdmin; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
}
