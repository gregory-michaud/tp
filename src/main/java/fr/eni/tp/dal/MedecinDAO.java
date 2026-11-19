package fr.eni.tp.dal;

import fr.eni.tp.bo.Medecin;

import java.util.Optional;

public interface MedecinDAO {

    void insert(Medecin medecin);
    Optional<Medecin> findByRpps(String rpps);
}
