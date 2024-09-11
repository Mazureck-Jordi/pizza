package fr.eni.pizza.bo;

public class Utilisateur {

    private Long id_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;
    private Commande id_commande;

    public Utilisateur() {
        super();
    }

    public Utilisateur(Long id_utilisateur, String nom, String prenom, String email, String mot_de_passe) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    public Utilisateur(String nom, String prenom, String email, String mot_de_passe, Commande id_commande) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.id_commande = id_commande;
    }

    public Utilisateur(Long id_utilisateur, String nom, String prenom, String email, String mot_de_passe, Commande id_commande) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.id_commande = id_commande;
    }



    public Long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Commande getId_commande() {
        return id_commande;
    }

    public void setId_commande(Commande id_commande) {
        this.id_commande = id_commande;
    }

    @Override
    public String toString() {
        return "com.example.demoGit.bo.Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", id_commande=" + id_commande +
                '}';
    }
}
