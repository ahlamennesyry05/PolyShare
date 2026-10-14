package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import patrons.Observateur;

// Classe abstraite Utilisateur (Etudiant ou Enseignant).
// Elle joue le role d'Observateur dans le patron Observateur.

public abstract class Utilisateur implements Observateur {

    protected int idUser;
    protected String prenom;
    protected String nom;
    protected String email;
    protected String motDePasse;
    protected String role;
    protected double noteMoyenne;

    private final List<Annonce> annonces = new ArrayList<>();                 // "publie"
    private final List<Demande> demandes = new ArrayList<>();                 // "envoie"
    private final List<Evaluation> evaluationsRecues = new ArrayList<>();     // "recoit"
    private final List<Evaluation> evaluationsRedigees = new ArrayList<>();   // "redige"

    public Utilisateur(int idUser, String prenom, String nom, String email,
                       String motDePasse, String role) {
        this.idUser = idUser;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    // Verifie les identifiants saisis.
    public boolean seConnecter(String email, String mdp) {
        return this.email.equalsIgnoreCase(email) && this.motDePasse.equals(mdp);
    }

    // Retourne les annonces publiees par cet utilisateur.
    public List<Annonce> consulterAnnonce() {
        return Collections.unmodifiableList(annonces); // unmodifiableList : toute autre classe qui appelle consulterAnnonce() peut lire le contenu de la liste (parcourir les annonces, les afficher), mais ne peut pas la modifier
    }

    // Reception d'une notification (patron Observateur).
    @Override
    public void update(Annonce annonce, String details) {
        System.out.println(" Notification pour " + prenom + " " + nom + " : " + details + " [" + annonce.getTitre() + "]");
    }

    public void ajouterAnnonce(Annonce a) { annonces.add(a); }
    public void ajouterDemande(Demande d) { demandes.add(d); }
    public void ajouterEvaluationRedigee(Evaluation e) { evaluationsRedigees.add(e); }

    // Enregistre l'evaluation et met a jour la note moyenne du profil.
    public void ajouterEvaluationRecue(Evaluation e) {
        evaluationsRecues.add(e);
        double somme = 0;
        for (Evaluation ev : evaluationsRecues) {
            somme += ev.getNote();
        }
        noteMoyenne = somme / evaluationsRecues.size();
    }

    public int getIdUser() { return idUser; }
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public double getNoteMoyenne() { return noteMoyenne; }
    public List<Demande> getDemandes() { return Collections.unmodifiableList(demandes); }
    public List<Evaluation> getEvaluationsRecues() { return Collections.unmodifiableList(evaluationsRecues); }
    public List<Evaluation> getEvaluationsRedigees() { return Collections.unmodifiableList(evaluationsRedigees); }

    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + role + ", " + email + ")";
    }
}