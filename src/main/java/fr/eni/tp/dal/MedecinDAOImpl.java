package fr.eni.tp.dal;

import fr.eni.tp.bo.Medecin;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MedecinDAOImpl implements MedecinDAO {

    private final String INSERT = "INSERT INTO medecins(id, nom, prenom,specialite) VALUES (:rpps, :nom, :prenom, :specialite)";
    private final String FIND_BY_RPPS = "SELECT id, nom, prenom,specialite FROM medecins WHERE id = :rpps";

    // Injection du template JDBC par constructeur
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MedecinDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Medecin medecin) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rpps", medecin.getRpps());
        params.addValue("nom", medecin.getNom());
        params.addValue("prenom", medecin.getPrenom());
        params.addValue("specialite", medecin.getSpecialite());

        jdbcTemplate.update(INSERT, params);

    }

    @Override
    public Optional<Medecin> findByRpps(String rpps) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("rpps", rpps);

        try {
            Medecin medecin = jdbcTemplate.queryForObject(
                    FIND_BY_RPPS,
                    params,
                    new MedecinRowMapper()
            );
            return Optional.ofNullable(medecin);
        } catch (EmptyResultDataAccessException e) {
            // Aucun médecin trouvé - cas acceptable et retour d'un empty
            return Optional.empty();
        }
        // rpps doit être unique
        // Pas besoin de catch IncorrectResultSizeDataAccessException - pour un nombre de Medecin > 1
        // si ça arrive, c'est un bug de données => mieux vaut laisser remonter l'exception vers la BLL

    }

    // la colonne id correspond à l’attribut rpps du Medecin
    // Il faut donc une classe qui implémente RowMapper
    class MedecinRowMapper implements RowMapper<Medecin> {
        @Override
        public Medecin mapRow(ResultSet rs, int rowNum) throws SQLException {
            Medecin medecin = new Medecin();
            medecin.setRpps(rs.getString("id"));
            medecin.setNom(rs.getString("nom"));
            medecin.setPrenom(rs.getString("prenom"));
            medecin.setSpecialite(rs.getString("specialite"));
            return medecin;
        }
    }

}
