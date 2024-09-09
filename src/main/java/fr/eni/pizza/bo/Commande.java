package com.example.demoGit.bo;

import java.time.LocalDate;

public class Commande {

    private Long id_commande;
    private LocalDate date_heure_livraison;
    private int livraison;
    private double prix_total;
    private int est_paye;
    private Etat id_etat;
    private Client id_client;
    private Utilisateur id_utilisateur;

    public Commande() {
    }

    public Commande(LocalDate date_heure_livraison, int livraison, double prix_total, int est_paye, Etat id_etat, Client id_client, Utilisateur id_utilisateur) {
        this.date_heure_livraison = date_heure_livraison;
        this.livraison = livraison;
        this.prix_total = prix_total;
        this.est_paye = est_paye;
        this.id_etat = id_etat;
        this.id_client = id_client;
        this.id_utilisateur = id_utilisateur;
    }

    public Commande(Long id_commande, LocalDate date_heure_livraison, int livraison, double prix_total, int est_paye, Etat id_etat, Client id_client, Utilisateur id_utilisateur) {
        this.id_commande = id_commande;
        this.date_heure_livraison = date_heure_livraison;
        this.livraison = livraison;
        this.prix_total = prix_total;
        this.est_paye = est_paye;
        this.id_etat = id_etat;
        this.id_client = id_client;
        this.id_utilisateur = id_utilisateur;
    }

    public Long getId_commande() {
        return id_commande;
    }

    public void setId_commande(Long id_commande) {
        this.id_commande = id_commande;
    }

    public LocalDate getDate_heure_livraison() {
        return date_heure_livraison;
    }

    public void setDate_heure_livraison(LocalDate date_heure_livraison) {
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
                '}';
    }
}