package tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import controleur.GestionnaireAuthentification;
import dao.Depot;
import modele.Admin;
import modele.Annonce;
import modele.Bien;
import modele.Demande;
import modele.Enseignant;
import modele.Etudiant;
import modele.Service;
import modele.StatutAnnonce;
import modele.StatutDemande;
import modele.Utilisateur;
import patrons.AnnonceFactory;
import patrons.Observateur;
import patrons.PolyShareSubject;

// Tests automatiques de PolyShare, sans bibliotheque externe.
// Lancer : clic droit sur ce fichier > Run 'TestsPolyShare.main()'.
public class TestsPolyShare {

    private static int reussis = 0;
    private static final List<String> echecs = new ArrayList<>();

    // ---------- Outils ----------

    private static void verifier(boolean condition, String nomTest) {
        if (condition) {
            reussis++;
        } else {
            echecs.add(nomTest);
        }
    }

    // Execute une action en masquant ce qu'elle affiche (notifications, messages).
    private static void silencieux(Runnable action) {
        PrintStream original = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
    }

    // Jeu de donnees commun : un depot, un admin, un etudiant et un enseignant.
    private static class Contexte {
        final Depot depot = new Depot();
        final Admin admin = new Admin(1, "Admin", "Test", "admin@polyshare.fr", depot);
        final PolyShareSubject polyShare = new PolyShareSubject(depot);
        final Utilisateur etudiant = admin.creerCompte("Martin", "Lea", "lea@polyshare.fr", "ETUDIANT");
        final Utilisateur enseignant = admin.creerCompte("Durand", "Paul", "paul@polyshare.fr", "ENSEIGNANT");

        Annonce publierBien() {
            Annonce[] a = new Annonce[1];
            silencieux(() -> a[0] = polyShare.publierAnnonce("BIEN", etudiant,
                    "Calculatrice", "Pret pour l'examen", "Materiel", "Pret"));
            return a[0];
        }
    }

    // ---------- Admin ----------

    private static void testsAdmin() {
        Contexte c = new Contexte();
        verifier(c.etudiant instanceof Etudiant, "creerCompte ETUDIANT cree un Etudiant");
        verifier(c.enseignant instanceof Enseignant, "creerCompte ENSEIGNANT cree un Enseignant");
        verifier(c.depot.getUtilisateurs().size() == 2, "les comptes sont enregistres dans le depot");

        boolean refuse = false;
        try {
            c.admin.creerCompte("X", "Y", "lea@polyshare.fr", "ETUDIANT");
        } catch (IllegalArgumentException e) {
            refuse = true;
        }
        verifier(refuse, "un email deja utilise est refuse");

        boolean roleRefuse = false;
        try {
            c.admin.creerCompte("X", "Y", "x@polyshare.fr", "PIRATE");
        } catch (IllegalArgumentException e) {
            roleRefuse = true;
        }
        verifier(roleRefuse, "un role inconnu est refuse");

        Annonce a = c.publierBien();
        c.admin.supprimerAnnonce(a);
        verifier(!c.depot.getAnnonces().contains(a), "supprimerAnnonce retire l'annonce du depot");
        verifier(!c.etudiant.consulterAnnonce().contains(a), "supprimerAnnonce retire l'annonce de son auteur");
    }

    // ---------- Authentification (Controleur) ----------

    private static void testsAuthentification() {
        Contexte c = new Contexte();
        GestionnaireAuthentification auth = new GestionnaireAuthentification(c.depot);
        Utilisateur[] r = new Utilisateur[3];
        silencieux(() -> {
            r[0] = auth.seConnecter("lea@polyshare.fr", Admin.MOT_DE_PASSE_PAR_DEFAUT);
            r[1] = auth.seConnecter("lea@polyshare.fr", "mauvais");
            r[2] = auth.seConnecter("inconnu@polyshare.fr", "x");
        });
        verifier(r[0] == c.etudiant, "connexion reussie avec le bon mot de passe");
        verifier(r[1] == null, "connexion refusee avec un mauvais mot de passe");
        verifier(r[2] == null, "connexion refusee pour un email inconnu");
        verifier("ETUDIANT".equals(auth.determinerRole(c.etudiant)), "le role est bien determine");
    }

    // ---------- Factory ----------

    private static void testsFactory() {
        Contexte c = new Contexte();
        AnnonceFactory factory = new AnnonceFactory();
        Annonce bien = factory.creerAnnonce("BIEN", 1, "Livre", "d", "Livres", c.etudiant, "Don");
        Annonce service = factory.creerAnnonce("SERVICE", 2, "Cours", "d", "Tutorat", c.enseignant, "Lundi");
        verifier(bien instanceof Bien, "la Factory cree un Bien pour le type BIEN");
        verifier(service instanceof Service, "la Factory cree un Service pour le type SERVICE");

        boolean refuse = false;
        try {
            factory.creerAnnonce("VOITURE", 3, "x", "d", "c", c.etudiant, "x");
        } catch (IllegalArgumentException e) {
            refuse = true;
        }
        verifier(refuse, "la Factory refuse un type inconnu");
    }

    // ---------- Observateur ----------

    private static void testsObservateur() {
        Contexte c = new Contexte();
        List<Observateur> concernes = c.depot.rechercherObservateursConcernes(c.publierBien());
        verifier(concernes.size() == 1 && concernes.contains(c.enseignant),
                "seuls les autres utilisateurs sont notifies, pas l'auteur");

        Annonce a = c.publierBien();
        verifier(a.getStatut() == StatutAnnonce.DISPONIBLE, "une annonce publiee est DISPONIBLE");
        verifier(c.etudiant.consulterAnnonce().contains(a), "l'annonce est rattachee a son auteur");
        verifier(c.depot.getAnnonces().contains(a), "l'annonce est enregistree dans le depot");
    }

    // ---------- Demande et evaluation ----------

    private static void testsDemande() {
        Contexte c = new Contexte();
        Annonce livre = c.publierBien();
        Demande d = new Demande(1, livre, c.enseignant);
        verifier(livre.getDemandes().size() == 1, "une demande n'est rattachee qu'une fois a l'annonce");
        verifier(d.getStatut() == StatutDemande.EN_ATTENTE, "une nouvelle demande est EN_ATTENTE");

        silencieux(d::terminer);
        verifier(d.getStatut() == StatutDemande.EN_ATTENTE, "on ne peut pas terminer une demande non acceptee");

        silencieux(d::accepter);
        verifier(d.getStatut() == StatutDemande.ACCEPTEE, "accepter passe la demande a ACCEPTEE");
        verifier(livre.getStatut() == StatutAnnonce.RESERVEE, "accepter passe l'annonce a RESERVEE");

        String avantFin = d.evaluer(c.enseignant, 5, "Top");
        verifier(avantFin.startsWith("Evaluation impossible"), "pas d'evaluation avant la fin de l'echange");

        silencieux(d::terminer);
        verifier(d.getStatut() == StatutDemande.TERMINEE, "terminer passe la demande a TERMINEE");

        verifier(d.evaluer(c.enseignant, 0, "x").startsWith("Note invalide"), "une note de 0 est refusee");
        verifier(d.evaluer(c.enseignant, 6, "x").startsWith("Note invalide"), "une note de 6 est refusee");

        Utilisateur intrus = c.admin.creerCompte("Z", "Z", "z@polyshare.fr", "ETUDIANT");
        verifier(d.evaluer(intrus, 4, "x").contains("ne participez pas"), "un non-participant ne peut pas evaluer");

        String[] ok = new String[1];
        silencieux(() -> ok[0] = d.evaluer(c.enseignant, 4, "Tres bien"));
        verifier("Evaluation enregistree.".equals(ok[0]), "une evaluation valide est enregistree");
        verifier(c.etudiant.getNoteMoyenne() == 4.0, "la note moyenne de l'evalue est mise a jour");
        verifier(d.evaluer(c.etudiant, 5, "x").startsWith("Une seule evaluation"), "une seule evaluation par echange");

        Demande refusee = new Demande(2, c.publierBien(), c.enseignant);
        silencieux(refusee::refuser);
        verifier(refusee.getStatut() == StatutDemande.REFUSEE, "refuser passe la demande a REFUSEE");
    }

    // ---------- Lancement ----------

    public static void main(String[] args) {
        testsAdmin();
        testsAuthentification();
        testsFactory();
        testsObservateur();
        testsDemande();

        int total = reussis + echecs.size();
        System.out.println("=== Tests PolyShare : " + reussis + "/" + total + " reussis ===");
        for (String e : echecs) {
            System.out.println("  ECHEC : " + e);
        }
        if (!echecs.isEmpty()) {
            System.exit(1);
        }
    }
}
