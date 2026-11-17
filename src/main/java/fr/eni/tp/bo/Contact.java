package fr.eni.tp.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Contact {

    @NotBlank
    @Size(min = 3, max = 50)
    private String nom;

    @NotBlank
    @Size(min = 3, max = 100)
    private String prenom;

    @NotBlank
    @Pattern(
    regexp = "^(?=.{1,150}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.(fr|com)$",
    message = "L'adresse email doit être valide, faire moins de 150 caractères et se terminer par .fr ou .com")
    private String email;

    @Size(max = 12)
    private String telephone;

    public Contact() {
    }

    public Contact(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
