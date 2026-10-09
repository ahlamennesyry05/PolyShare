public class Enseignant extends Utilisateur{

    private String departement;

    //consturcteur
    public Enseignant(String prenom,String nom, String email, String motDePasse, String departement){
        super(prenom, nom, email, motDePasse);
        this.departement = departement;
    }

    //getters
    public String getDepartement() {
        return departement;
    }

    //setters
    public void setDepartement(String departement) {
        this.departement = departement;
    }
}
