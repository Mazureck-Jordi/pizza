package com.example.demoGit.bo;

public class Etat {

    private Long id_etat;
    private String libelle;

    public Etat() {
    }

    public Etat(String libelle) {
        this.libelle = libelle;
    }

    public Etat(Long id_etat, String libelle) {
        this.id_etat = id_etat;
        this.libelle = libelle;
    }

    public Long getId_etat() {
        return id_etat;
    }

    public void setId_etat(Long id_etat) {
        this.id_etat = id_etat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Etat{" +
                "id_etat=" + id_etat +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
