public class Etudiant extends Utilisateur{
    private String filiere;

    //consturcteur
    public Etudiant(String prenom,String nom, String email, String motDePasse, String filiere){
        super(prenom, nom, email, motDePasse);
        this.filiere = filiere;
    }

    //getters
    public String getFiliere() {
        return filiere;
    }

    //setters
    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
}
