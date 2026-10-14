package modele;

public class Etudiant extends Utilisateur {

    private String filiere;

    public Etudiant(int idUser, String prenom, String nom, String email,
                    String motDePasse, String filiere) {
        super(idUser, prenom, nom, email, motDePasse, "ETUDIANT");
        this.filiere = filiere;
    }

    public String getFiliere() { return filiere; }
}
