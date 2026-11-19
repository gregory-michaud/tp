package fr.eni.tp.bo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class RendezVous {
    private Long id;
    private LocalDate jour;
    private LocalTime debut;
    private Medecin medecin;
    private Contact patient;

    public RendezVous() {
    }

    public RendezVous(LocalDate jour, LocalTime debut, Medecin medecin, Contact patient) {
        this.jour = jour;
        this.debut = debut;
        this.medecin = medecin;
        this.patient = patient;
    }

    public RendezVous(Long id, LocalDate jour, LocalTime debut, Medecin medecin, Contact patient) {
        this.id = id;
        this.jour = jour;
        this.debut = debut;
        this.medecin = medecin;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate jour) {
        this.jour = jour;
    }

    public LocalTime getDebut() {
        return debut;
    }

    public void setDebut(LocalTime debut) {
        this.debut = debut;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Contact getPatient() {
        return patient;
    }

    public void setPatient(Contact patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RendezVous that = (RendezVous) o;
        return Objects.equals(id, that.id) && Objects.equals(jour, that.jour) && Objects.equals(debut, that.debut) && Objects.equals(medecin, that.medecin) && Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jour, debut, medecin, patient);
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", jour=" + jour +
                ", debut=" + debut +
                ", medecin=" + medecin +
                ", patient=" + patient +
                '}';
    }
}
