package fr.eni.tp.controller;

import fr.eni.tp.bll.ContactService;
import fr.eni.tp.bo.Contact;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    @ResponseBody
    public List<Contact> afficherContacts (){
        List <Contact> lstContacts = contactService.getContacts();
        return lstContacts;
    }

    @GetMapping("/{email}")
    @ResponseBody
    public Contact detailContact(@PathVariable(name = "email", required = true) String emailContact) {
        final Contact Contact = contactService.getContact(emailContact);
        return Contact;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity creerContact(@Valid Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // des données ne sont pas validées - retourner les erreurs
            // Construire une liste des erreurs avec nom du champ et message
            String erreurs = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            return ResponseEntity.badRequest().body("Erreurs :<br>" + erreurs);
        } else {
            contactService.addContact(contact);
            return ResponseEntity.ok(contact);
        }
    }

}
