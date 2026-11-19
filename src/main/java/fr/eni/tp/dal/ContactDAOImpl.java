package fr.eni.tp.dal;

import fr.eni.tp.bo.Contact;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ContactDAOImpl implements ContactDAO {


    private final String INSERT = "INSERT INTO contacts(email, nom, prenom,telephone) VALUES (:email, :nom, :prenom, :telephone)";
    private final String FIND_BY_EMAIL = "SELECT email, nom, prenom,telephone FROM contacts WHERE email = :email";
    private final String FIND_ALL = "SELECT email, nom, prenom,telephone FROM contacts";

    // Injection du template JDBC par constructeur
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public ContactDAOImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Contact contact) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", contact.getEmail());
        params.addValue("nom", contact.getNom());
        params.addValue("prenom", contact.getPrenom());
        params.addValue("telephone", contact.getTelephone());
        jdbcTemplate.update(INSERT, params);
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        try{
            Contact contact = jdbcTemplate.queryForObject(FIND_BY_EMAIL, params, new BeanPropertyRowMapper<>(Contact.class));
            return Optional.ofNullable(contact);
        } catch(EmptyResultDataAccessException e ){
            // Aucun contact trouvé cas acceptable et retour d'un empty
            return Optional.empty();
        }

        // email doit être unique
        // Pas besoin de catch IncorrectResultSizeDataAccessException pour un nombre de Contact > 1
        // si ça arrive, c'est un bug de données => mieux vaut laisser remonter l'exception vers la BLL
    }

    @Override
    public List<Contact> findAll() {
        // la méthode query de NamedParameterJdbcTemplate fonctionne aussi bien avec ou sans paramètre
        return jdbcTemplate.query(FIND_ALL , new BeanPropertyRowMapper<>(Contact.class));
    }
}
