package fr.eni.tp.bll;

import fr.eni.tp.bo.Contact;
import fr.eni.tp.dal.ContactDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactDAO contactDAO;

    public ContactServiceImpl(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Override
    public List<Contact> getContacts() {
        return contactDAO.findAll();
    }

    @Override
    public Contact getContact(String email) {
        final Optional<Contact> opt = contactDAO.findByEmail(email);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public void addContact(Contact contact) {
        contactDAO.insert(contact);
    }
}
