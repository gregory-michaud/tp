package fr.eni.tp.dal;

import fr.eni.tp.bo.RendezVous;

import java.util.List;
import java.util.Optional;

public interface RendezVousDAO {

    void insert(RendezVous rdv);

    Optional<RendezVous> findById(long id);

    List<RendezVous> findByPatient(String emailPatient);

    List<RendezVous> findByMedecin(String rpps);
}
