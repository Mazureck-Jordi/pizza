package com.example.demoGit.bo;

public class Role {

    private Long id_role;
    private String libelle;

    public Role() {
    }

    public Role(String libelle) {
        this.libelle = libelle;
    }

    public Role(Long id_role, String libelle) {
        this.id_role = id_role;
        this.libelle = libelle;
    }

    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id_role=" + id_role +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
