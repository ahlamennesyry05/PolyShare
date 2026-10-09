public class Demande {

    private int idDemande;
    private StatutDemande statut;

    public Demande(int idDemande) {
        this.idDemande = idDemande;
        this.statut = StatutDemande.EN_ATTENTE;
    }

    public void accepter() {
        this.statut = StatutDemande.ACCEPTEE;
    }

    public void refuser() {
        this.statut = StatutDemande.REFUSEE;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public StatutDemande getStatut() {
        return statut;
    }
}