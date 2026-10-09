import java.util.ArrayList;
import java.util.List;

public abstract class Utilisateur {
    protected Integer idUser;
    protected String prenom;
    protected String nom;
    protected String role;
    protected String email;
    protected String motDePasse;


    //contructeurs
    public Utilisateur(){
    }
    public Utilisateur(String prenom,String nom, String email, String motDePasse){
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }
    public Utilisateur(String prenom,String nom, String role, String email, String motDePasse){
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    //getters
    public Integer getIdUser() {
        return idUser;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }


    //setters

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    //methods
    public Boolean seConnecter(String email , String motDePasse) {
        //implementation
        return true;
    }
    public void consulterAnnonces(){
        //implementation "return ArrayList<Annonce>"
    }
}
