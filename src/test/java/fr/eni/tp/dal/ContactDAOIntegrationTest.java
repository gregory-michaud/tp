package fr.eni.tp.dal;

import fr.eni.tp.bo.Contact;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ContactDAOImpl.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ContactDAOIntegrationTest {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ContactDAO dao;

    @Test
    void test01_InsertAndFindByEmail() {
        String email = "emmatique@contacts.fr";
        String nom = "Tique";
        String prenom = "Emma";

        Contact f = new Contact(nom, prenom, email, "+33777887788");
        dao.insert(f);

        Optional<Contact> result = dao.findByEmail(email);

        assertThat(result).isPresent();
        assertThat(result.get().getNom()).isEqualTo(nom);
        assertThat(result.get().getPrenom()).isEqualTo(prenom);

        logger.info("test01_InsertAndFindByEmail");
        logger.info(f);
    }

    @Test
    void test02_FindAllEmpty() {
        List<Contact> results = dao.findAll();
        assertThat(results).isEmpty();

        logger.info("test02_FindAllEmpty");
        logger.info(results);
    }

    @Test
    void test03_FindAll_returnsAtLeastOne() {
        // GIVEN
        dao.insert(new Contact("Voyant", "Claire", "clairvoyant@mescontacts.fr", "+33677667766"));
        dao.insert(new Contact("Bon", "Jean", "jenbon@contacts.fr", null));// Téléphone est optionnel

        // WHEN
        List<Contact> results = dao.findAll();

        // THEN
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(Contact::getEmail)
                .containsExactlyInAnyOrder("clairvoyant@mescontacts.fr", "jenbon@contacts.fr");

        logger.info("test03_FindAll_returnsAtLeastOne");
        logger.info(results);
    }
}
