package fr.eni.pizza.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Utilisateur {

    private Long id_utilisateur;
    @NotBlank(message="Le nom doit être renseigné")
    private String nom;
    @NotBlank(message="le prenom doit être renseigné")
    private String prenom;
    @NotBlank(message="l''email doit être renseigné")
    @Email(message= "l''email doit être valide")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,}$",
            message = "Le mot de passe doit contenir au moins un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial"
    )
    @Size(min = 8, max = 20, message = "Le mot de passe doit contenir entre 8 et 20 caractères")
    private String mot_de_passe;
    private Commande id_commande;
    private List<Role> roles;

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

    public Utilisateur(Long id_utilisateur, String nom, String prenom, String email, String mot_de_passe, List<Role> roles) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.roles = roles;
    }

    public Utilisateur(Long id_utilisateur, String nom, String prenom, String email, String mot_de_passe, Commande id_commande, List<Role> roles) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.id_commande = id_commande;
        this.roles = roles;
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

    public String getMot_de_passe() {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", id_commande=" + id_commande +
                ", role=" + roles +
                '}';
    }
}
