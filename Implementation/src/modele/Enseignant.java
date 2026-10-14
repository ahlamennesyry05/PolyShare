package modele;

public class Enseignant extends Utilisateur {

    private String departement;

    public Enseignant(int idUser, String prenom, String nom, String email,
                      String motDePasse, String departement) {
        super(idUser, prenom, nom, email, motDePasse, "ENSEIGNANT");
        this.departement = departement;
    }

    public String getDepartement() { return departement; }
}
