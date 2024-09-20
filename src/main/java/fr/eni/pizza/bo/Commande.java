package fr.eni.pizza.bo;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Commande {

    private Long id_commande;
    @NotNull(message="La date doit être renseignée")
    @FutureOrPresent(message = "La date doit être dans le futur ou aujourd'hui")
    private LocalDateTime date_heure_livraison;
    @NotNull(message = "La livraison doit être renseignée")
    @Min(value = 0, message = "La livraison être comprise entre 0 et 1")
    @Max(value = 1 , message = "La livraison être comprise entre 0 et 1")
    private int livraison;
    @NotNull(message = "Le prix total doit être renseigné")
    @PositiveOrZero(message = "Le prix total doit être supérieur ou égal à 0.0 €")
    @DecimalMin(value = "0.0", message = "Le prix total doit être supérieur ou égal à 0.0 €")
    private double prix_total;
    @NotNull(message = "L'etat doit être renseigné")
    @Min(value = 0, message = "L'etat être compris entre 0 et 1")
    @Max(value = 1 , message = "L'etat être compris entre 0 et 1")
    private int est_paye;
    private Etat id_etat;
    private Client id_client;
    private Utilisateur id_utilisateur;
    private List<DetailCommande> detail_commandes;

    public Commande() {
    }

    public Commande(Long id_commande, LocalDateTime date_heure_livraison, int livraison, double prix_total, int est_paye) {
        this.id_commande = id_commande;
        this.date_heure_livraison = date_heure_livraison;
        this.livraison = livraison;
        this.prix_total = prix_total;
        this.est_paye = est_paye;
    }

    public Commande(LocalDateTime date_heure_livraison, int livraison, double prix_total, int est_paye, Etat id_etat, Client id_client, Utilisateur id_utilisateur) {
        this.date_heure_livraison = date_heure_livraison;
        this.livraison = livraison;
        this.prix_total = prix_total;
        this.est_paye = est_paye;
        this.id_etat = id_etat;
        this.id_client = id_client;
        this.id_utilisateur = id_utilisateur;
    }

    public Commande(Long id_commande, LocalDateTime date_heure_livraison, int livraison, double prix_total, int est_paye, Etat id_etat, Client id_client, Utilisateur id_utilisateur) {
        this.id_commande = id_commande;
        this.date_heure_livraison = date_heure_livraison;
        this.livraison = livraison;
        this.prix_total = prix_total;
        this.est_paye = est_paye;
        this.id_etat = id_etat;
        this.id_client = id_client;
        this.id_utilisateur = id_utilisateur;
    }

    public Commande(Long id_commande, LocalDateTime date_heure_livraison, int livraison, double prix_total, int est_paye, Etat id_etat, Client id_client, Utilisateur id_utilisateur, List<DetailCommande> detail_commandes) {
        this.id_commande = id_commande;
        this.date_heure_livraison = date_heure_livraison;
        this.livraison = livraison;
        this.prix_total = prix_total;
        this.est_paye = est_paye;
        this.id_etat = id_etat;
        this.id_client = id_client;
        this.id_utilisateur = id_utilisateur;
        this.detail_commandes = detail_commandes;
    }

    public Long getId_commande() {
        return id_commande;
    }

    public void setId_commande(Long id_commande) {
        this.id_commande = id_commande;
    }

    public LocalDateTime getDate_heure_livraison() {
        return date_heure_livraison;
    }

    public void setDate_heure_livraison(LocalDateTime date_heure_livraison) {
        this.date_heure_livraison = date_heure_livraison;
    }

    public int getLivraison() {
        return livraison;
    }

    public void setLivraison(int livraison) {
        this.livraison = livraison;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public int getEst_paye() {
        return est_paye;
    }

    public void setEst_paye(int est_paye) {
        this.est_paye = est_paye;
    }

    public Etat getId_etat() {
        return id_etat;
    }

    public void setId_etat(Etat id_etat) {
        this.id_etat = id_etat;
    }

    public Client getId_client() {
        return id_client;
    }

    public void setId_client(Client id_client) {
        this.id_client = id_client;
    }

    public Utilisateur getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Utilisateur id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public List<DetailCommande> getDetail_commandes() {
        return detail_commandes;
    }

    public void setDetail_commandes(List<DetailCommande> detail_commandes) {
        this.detail_commandes = detail_commandes;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id_commande=" + id_commande +
                ", date_heure_livraison=" + date_heure_livraison +
                ", livraison=" + livraison +
                ", prix_total=" + prix_total +
                ", est_paye=" + est_paye +
                ", id_etat=" + id_etat +
                ", id_client=" + id_client +
                ", id_utilisateur=" + id_utilisateur +
                ", detail_commandes=" + detail_commandes +
                '}';
    }
}
