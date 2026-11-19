package fr.eni.tp.bo;

import java.util.Objects;

public class Medecin {
    private String rpps;
    private String nom;
    private String prenom;
    private String specialite;

    public Medecin() {
    }

    public Medecin(String rpps, String nom, String prenom, String specialite) {
        this.rpps = rpps;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
    }

    public String getRpps() {
        return rpps;
    }

    public void setRpps(String rpps) {
        this.rpps = rpps;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Medecin medecin = (Medecin) o;
        return Objects.equals(rpps, medecin.rpps);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rpps);
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "rpps='" + rpps + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", specialite='" + specialite + '\'' +
                '}';
    }
}
