package main;

import controleur.GestionnaireAuthentification;
import dao.Depot;
import modele.Admin;
import modele.Annonce;
import modele.Demande;
import modele.Utilisateur;
import patrons.PolyShareSubject;

public class Main {

    private static void titre(String t) {
        System.out.println();
        System.out.println("=== " + t + " ===");
    }

    public static void main(String[] args) {
        Depot depot = new Depot();
        Admin admin = new Admin(1, "AIT TARGA", "Salma", "admin@polyshare.fr", depot);
        GestionnaireAuthentification auth = new GestionnaireAuthentification(depot);
        PolyShareSubject polyShare = new PolyShareSubject(depot);


        titre("1. L'administrateur crée les comptes");
        Utilisateur sara = admin.creerCompte("Bouarafa", "Sara", "sara.bouarafa@polyshare.fr", "ETUDIANT");
        Utilisateur hiba = admin.creerCompte("Sebban", "Hiba", "hiba.sebban@polyshare.fr", "ETUDIANT");
        Utilisateur ahlam = admin.creerCompte("En-nesyry", "Ahlam", "ahlam.ennesyry@polyshare.fr", "ENSEIGNANT");
        for (Utilisateur u : depot.getUtilisateurs()) {
            System.out.println(" Compte créé : " + u);
        }


        titre("2. Se connecter");
        auth.seConnecter("sara.bouarafa@polyshare.fr", Admin.MOT_DE_PASSE_PAR_DEFAUT);
        auth.seConnecter("sara.bouarafa@polyshare.fr", "mauvais");
        auth.seConnecter("inconnu@polyshare.fr", "x");


        titre("3. Créer et publier des annonces");
        System.out.println("- Un enseignant propose un service :");
        polyShare.proposerService(ahlam, "Tutorat en Java", "Aide sur la POO",
                "Tutorat", "Mercredi 14h-16h");
        System.out.println("- Un étudiant demande un service :");
        polyShare.demanderService(hiba, "Aide au déménagement", "Besoin d'aide samedi",
                "Services de dépannage", "Ce week-end");
        System.out.println("- Un étudiant publie un bien :");
        Annonce livre = polyShare.publierAnnonce("Bien", sara, "Livre Conception objet",
                "Prêt pour un semestre", "Livres et cours", "Prêt");
        for (Annonce a : depot.getAnnonces()) {
            System.out.println("   " + a);
        }

        titre("4. Demande, acceptation et évaluation");
        Demande d = new Demande(1, livre, hiba); // le constructeur rattache deja la demande a l'annonce
        depot.ajouterDemande(d);
        System.out.println("   " + d);
        d.accepter();
        System.out.println("   " + d + " | annonce : " + livre.getStatut());

        System.out.println("- Évaluation avant la fin de l'échange :");
        System.out.println("   " + d.evaluer(hiba, 5, "Super"));

        d.terminer();
        System.out.println("   " + d);

        System.out.println("- Note invalide :");
        System.out.println("   " + d.evaluer(hiba, 7, "Trop haut"));

        System.out.println("- Évaluation valide :");
        System.out.println("   " + d.evaluer(hiba, 5, "Très sympa et ponctuelle"));

        System.out.println("- Seconde évaluation sur le même échange :");
        System.out.println("   " + d.evaluer(sara, 4, "Merci"));

        System.out.println("   Note moyenne de " + sara.getPrenom() + " : " + sara.getNoteMoyenne());

        titre("5. Modération par l'administrateur");
        Annonce aSupprimer = depot.getAnnonces().get(1);
        System.out.println("   Suppression de : " + aSupprimer);
        admin.supprimerAnnonce(aSupprimer);
        System.out.println("   Annonces restantes : " + depot.getAnnonces().size());
        admin.gererCompte(hiba);
        System.out.println("   Mot de passe de " + hiba.getPrenom() + " réinitialisé.");
    }
}
