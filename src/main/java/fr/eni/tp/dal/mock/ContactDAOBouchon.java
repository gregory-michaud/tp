package fr.eni.tp.dal.mock;

import fr.eni.tp.bo.Contact;
import fr.eni.tp.dal.ContactDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactDAOBouchon implements ContactDAO {

    // DB in memory - gestion d'une liste de contacts locale
    private static List<Contact> lstContacts;

    public ContactDAOBouchon() {
        // Listes de formateurs différente entre les bouchons des couches DAL et BLL
        // Pour permettre de différencier les instances des beans
        lstContacts = new ArrayList<>();
        lstContacts.add(new Contact("Tique", "Emma", "emmatique@contacts.fr", "+33777887788"));
        lstContacts.add(new Contact("Voyant", "Claire", "clairvoyant@mescontacts.fr", "+33677667766"));
        lstContacts.add(new Contact("Conda", "Anna", "annaconda@touscontacts.fr", "+33655665566"));
        lstContacts.add(new Contact("Bon", "Jean", "jenbon@contacts.fr", "+33799889988"));
        lstContacts.add(new Contact("Térieur", "Alain", "alainterieur@touscontacts.fr", "+33799999999"));
        lstContacts.add(new Contact("Hédonde", "Marc", "marchedonde@mescontacts.fr", "+33677775566"));
    }

    @Override
    public void insert(Contact contact) {
        lstContacts.add(contact);
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return lstContacts.stream().filter(contact -> contact.getEmail().equals(email)).findFirst();
    }

    @Override
    public List<Contact> findAll() {
        return lstContacts;
    }
}
