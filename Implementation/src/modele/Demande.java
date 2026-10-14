package modele;

// Demande envoyee par un utilisateur sur une annonce.

public class Demande {

    private int idDemande;
    private StatutDemande statut;
    private Annonce annonce;
    private Utilisateur demandeur;
    private Evaluation evaluation;

    public Demande(int idDemande, Annonce annonce, Utilisateur demandeur) {
        this.idDemande = idDemande;
        this.annonce = annonce;
        this.demandeur = demandeur;
        this.statut = StatutDemande.EN_ATTENTE;
        annonce.ajouterDemande(this);
        demandeur.ajouterDemande(this);
    }

    public void accepter() {
        if (statut != StatutDemande.EN_ATTENTE) {
            System.out.println(" Remarque (!) : Seule une demande EN_ATTENTE peut etre acceptee.");
            return;
        }
        statut = StatutDemande.ACCEPTEE;
        annonce.changerStatut(StatutAnnonce.RESERVEE);
        demandeur.update(annonce, "Votre demande a ete acceptee");
    }

    public void refuser() {
        if (statut != StatutDemande.EN_ATTENTE) {
            System.out.println("Remarque (!) : Seule une demande EN_ATTENTE peut etre refusee.");
            return;
        }
        statut = StatutDemande.REFUSEE;
        demandeur.update(annonce, "Votre demande a ete refusee");
    }

    public void terminer() {
        if (statut != StatutDemande.ACCEPTEE) {
            System.out.println("Remarque (!) : Seule une demande ACCEPTEE peut etre terminee.");
            return;
        }
        statut = StatutDemande.TERMINEE;
    }

    public String evaluer(Utilisateur evaluateur, int note, String commentaire) {
        // L'echange doit etre TERMINEE
        if (statut != StatutDemande.TERMINEE) {
            return "Evaluation impossible : l'echange n'est pas termine.";
        }
        // Une seule evaluation par echange
        if (evaluation != null) {
            return "Une seule evaluation par echange est autorisee.";
        }
        // L'evalue est l'autre participant de l'echange
        Utilisateur evalue;
        if (evaluateur == demandeur) {
            evalue = annonce.getAuteur();
        } else if (evaluateur == annonce.getAuteur()) {
            evalue = demandeur;
        } else {
            return "Evaluation impossible : vous ne participez pas a cet echange.";
        }
        //La note doit etre valide (1 a 5)
        if (note < 1 || note > 5) {
            return "Note invalide : elle doit etre comprise entre 1 et 5.";
        }
        // Enregistrement, mise a jour de la moyenne, et la derniere etape notification
        evaluation = new Evaluation(note, commentaire, evaluateur, evalue);
        evaluateur.ajouterEvaluationRedigee(evaluation);
        evalue.ajouterEvaluationRecue(evaluation);
        evalue.update(annonce, "Vous avez recu une nouvelle evaluation : " + note + "/5");
        return "Evaluation enregistree.";
    }

    public int getIdDemande() { return idDemande; }
    public StatutDemande getStatut() { return statut; }
    public Annonce getAnnonce() { return annonce; }
    public Utilisateur getDemandeur() { return demandeur; }
    public Evaluation getEvaluation() { return evaluation; }

    @Override
    public String toString() {
        return "Demande #" + idDemande + " (" + statut + ") sur " + annonce.getTitre();
    }
}
