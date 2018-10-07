package db;

import anonymous.base.Contact;
import anonymous.base.DBService;

import java.util.ArrayList;
import java.util.Collection;

public final class DBServiceDummy implements DBService {

    private static Collection<Contact> allContacts = new ArrayList<Contact>();

    static {
        allContacts.add(new Contact(1, "Ted Mosby"));
        allContacts.add(new Contact(2, "Barney Stinson"));
        allContacts.add(new Contact(3, "Robin Scherbatsky"));
        allContacts.add(new Contact(4, "Marshall Eriksen"));
        allContacts.add(new Contact(5, "Lily Aldrin"));
    }

    public Collection<Contact> getAllContacts() {
        return allContacts;
    }
}