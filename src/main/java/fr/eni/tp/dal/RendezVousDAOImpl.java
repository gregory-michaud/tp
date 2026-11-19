package fr.eni.tp.dal;

import fr.eni.tp.bo.Contact;
import fr.eni.tp.bo.Medecin;
import fr.eni.tp.bo.RendezVous;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RendezVousDAOImpl implements RendezVousDAO{

    private final String INSERT = "INSERT INTO rendez_vous(jour, debut, id_medecin, id_patient) VALUES (:jour, :debut, :rpps, :email)";
    private final String FIND_BY_ID = "SELECT id, jour, debut, id_medecin, id_patient FROM rendez_vous WHERE id = :id";
    private final String FIND_BY_MEDECIN = "SELECT id, jour, debut, id_medecin, id_patient FROM rendez_vous WHERE id_medecin = :rpps";
    private final String FIND_BY_PATIENT = "SELECT id, jour, debut, id_medecin, id_patient FROM rendez_vous WHERE id_patient = :email";

    // Injection du template JDBC par constructeur
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RendezVousDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void insert(RendezVous rdv) {
        // La clef primaire est auto-générée par la base - il faut donc la récupérer
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(
                        "jour", rdv.getJour())
                .addValue("debut", rdv.getDebut())
                .addValue("rpps", rdv.getMedecin().getRpps())
                .addValue("email", rdv.getPatient().getEmail());

        jdbcTemplate.update(INSERT, params, keyHolder);

        if (keyHolder != null && keyHolder.getKey() != null) {
            // Mise à jour de l'identifiant auto-généré par la base
            rdv.setId(keyHolder.getKey().longValue());
        }


    }

    @Override
    public Optional<RendezVous> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            RendezVous rdv = jdbcTemplate.queryForObject(
                    FIND_BY_ID,
                    params,
                    new RendezVousRowMapper()
            );
            return Optional.ofNullable(rdv);
        } catch (EmptyResultDataAccessException e) {
            // Aucun rendez-vous trouvé - cas acceptable et retour d'un empty
            return Optional.empty();
        }
        // rpps doit être unique
        // Pas besoin de catch IncorrectResultSizeDataAccessException - pour un nombre de RendezVous > 1
        // si ça arrive, c'est un bug de données => mieux vaut laisser remonter l'exception vers la BLL

    }

    @Override
    public List<RendezVous> findByPatient(String emailPatient) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", emailPatient);
        return jdbcTemplate.query(FIND_BY_PATIENT, params, new RendezVousRowMapper());

    }

    @Override
    public List<RendezVous> findByMedecin(String rpps) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rpps", rpps);
        return jdbcTemplate.query(FIND_BY_MEDECIN, params, new RendezVousRowMapper());

    }

    // Il y a des associations entre RendezVous, Patient et Medecin
    // Il faut donc une classe qui implémente RowMapper
    class RendezVousRowMapper implements RowMapper<RendezVous> {
        @Override
        public RendezVous mapRow(ResultSet rs, int rowNum) throws SQLException {
            RendezVous rendezVous = new RendezVous();
            rendezVous.setId(rs.getLong("id"));
            rendezVous.setJour(rs.getDate("jour").toLocalDate());
            rendezVous.setDebut(rs.getTime("debut").toLocalTime());

            // Association avec Medecin
            Medecin medecin = new Medecin();
            medecin.setRpps(rs.getString("id_medecin"));
            rendezVous.setMedecin(medecin);

            // Association avec Contact pour le patient
            Contact patient = new Contact();
            patient.setEmail(rs.getString("id_patient"));
            rendezVous.setPatient(patient);

            return rendezVous;
        }
    }

}
