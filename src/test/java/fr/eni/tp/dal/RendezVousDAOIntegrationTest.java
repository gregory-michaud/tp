package fr.eni.tp.dal;

import fr.eni.tp.bo.Contact;
import fr.eni.tp.bo.Medecin;
import fr.eni.tp.bo.RendezVous;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({RendezVousDAOImpl.class, ContactDAOImpl.class, MedecinDAOImpl.class})
@TestMethodOrder(MethodOrderer.MethodName.class)
class RendezVousDAOIntegrationTest {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private RendezVousDAO dao;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private MedecinDAO medecinDAO;

    private List<Contact> patients;
    private List<Medecin> medecins;

    // Les médecins et patients sont des Mocks pour les tests
    @BeforeEach
    void setUp() {
        patients = new ArrayList<>();
        patients.add(new Contact("Tique", "Emma", "emmatique@contacts.fr", "+33777887788"));
        patients.add(new Contact("Bon", "Jean", "jenbon@contacts.fr", null));

        medecins = new ArrayList<>();
        medecins.add(new Medecin("12121212121", "Alzheimer", "Aloïs", "Psychanalyse"));
        medecins.add(new Medecin("33321212121", "Apgar", "Virginia", "Pédiatrie / Obstétrique"));

        // Persist dans H2
        patients.forEach(contactDAO::insert);
        medecins.forEach(medecinDAO::insert);
    }

    @Test
    void test01_InsertAndFindById() {
        LocalDate jour = LocalDate.of(2025, 10, 5);
        LocalTime debut = LocalTime.of(14, 30);

        Contact patient = patients.get(0);
        Medecin medecin = medecins.get(0);

        RendezVous rdv = new RendezVous(jour, debut, medecin, patient);
        dao.insert(rdv);
        Long id = rdv.getId();
        assertNotNull(id);
        assertTrue(id > 0);

        Optional<RendezVous> result = dao.findById(rdv.getId());

        assertThat(result).isPresent();
        RendezVous rdvDB = result.get();
        assertThat(rdvDB.getJour()).isEqualTo(jour);
        assertThat(rdvDB.getDebut()).isEqualTo(debut);
        assertNotNull(rdvDB.getPatient());
        assertThat(rdvDB.getPatient().getEmail()).isEqualTo(patient.getEmail());
        assertNotNull(rdvDB.getMedecin());
        assertThat(rdvDB.getMedecin().getRpps()).isEqualTo(medecin.getRpps());

        logger.info("test01_InsertAndFindById");
        logger.info(rdv);
    }

    @Test
    void test02_FindAll_Patient() {
        LocalDate jour1 = LocalDate.of(2025, 10, 5);
        LocalDate jour2 = LocalDate.of(2026, 01, 20);
        LocalTime debut = LocalTime.now();

        Contact patient = patients.get(0);

        RendezVous rdv1 = new RendezVous(jour1, debut, medecins.get(0), patient);
        dao.insert(rdv1);
        Long id1 = rdv1.getId();
        assertNotNull(id1);
        assertTrue(id1 > 0);

        RendezVous rdv2 = new RendezVous(jour2, debut, medecins.get(1), patient);
        dao.insert(rdv2);
        Long id2 = rdv2.getId();
        assertNotNull(id2);
        assertTrue(id2 > 0);

        List<RendezVous> results = dao.findByPatient(patient.getEmail());

        // THEN
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(RendezVous::getId)
                .containsExactlyInAnyOrder(id1, id2);

        logger.info("test02_FindAll_Patient");
        logger.info(results);
    }

    @Test
    void test03_FindAll_Medecin() {
        LocalDate jour1 = LocalDate.of(2025, 10, 5);
        LocalDate jour2 = LocalDate.of(2026, 01, 20);
        LocalTime debut = LocalTime.now();

        Medecin medecin = medecins.get(0);

        RendezVous rdv1 = new RendezVous(jour1, debut, medecin, patients.get(0));
        dao.insert(rdv1);
        Long id1 = rdv1.getId();
        assertNotNull(id1);
        assertTrue(id1 > 0);

        RendezVous rdv2 = new RendezVous(jour2, debut, medecin, patients.get(1));
        dao.insert(rdv2);
        Long id2 = rdv2.getId();
        assertNotNull(id2);
        assertTrue(id2 > 0);

        List<RendezVous> results = dao.findByMedecin(medecin.getRpps());

        // THEN
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(RendezVous::getId)
                .containsExactlyInAnyOrder(id1, id2);

        logger.info("test03_FindAll_Medecin");
        logger.info(results);
    }
}
