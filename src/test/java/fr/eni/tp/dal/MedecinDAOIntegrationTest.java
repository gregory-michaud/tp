package fr.eni.tp.dal;

import fr.eni.tp.bo.Medecin;
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
@Import(MedecinDAOImpl.class)
class MedecinDAOIntegrationTest {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MedecinDAO dao;

    @Test
    void test_InsertAndFindByRpps() {
        String rpps = "12121212121";
        String nom = "Alzheimer";
        String prenom = "Aloïs";
        String specialite = "Psychanalyse";

        Medecin f = new Medecin(rpps,nom,prenom,specialite);
        dao.insert(f);

        Optional<Medecin> result = dao.findByRpps(rpps);

        assertThat(result).isPresent();
		assertThat(result.get().getRpps()).isEqualTo(rpps);
        assertThat(result.get().getNom()).isEqualTo(nom);
        assertThat(result.get().getPrenom()).isEqualTo(prenom);
        assertThat(result.get().getSpecialite()).isEqualTo(specialite);

        logger.info("test01_InsertAndFindByRpps");
        logger.info(f);
    }
}
