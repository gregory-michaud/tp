package fr.eni.tp.bll;

import fr.eni.tp.bo.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> getContacts();

    Contact getContact(String email);

    void addContact(Contact contact);
}
