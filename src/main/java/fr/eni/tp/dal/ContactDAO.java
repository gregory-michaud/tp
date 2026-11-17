package fr.eni.tp.dal;

import fr.eni.tp.bo.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactDAO {
    void insert(Contact contact);

    Optional<Contact> findByEmail(String email);

    List<Contact> findAll();
}
