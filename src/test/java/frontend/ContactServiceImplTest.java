package frontend;

import anonymous.base.Contact;
import anonymous.base.DBService;
import anonymous.frontend.ContactServiceImpl;
import db.DBServiceDummy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ContactServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(ContactServiceImplTest.class.getName());


    @Test
    public void testGetFilteredContacts_All() {
        LOGGER.info("testGetFilteredContacts_All()");
        DBService dbService = new DBServiceDummy();
        ContactServiceImpl contactService = new ContactServiceImpl(dbService);
        Collection<Contact> filteredContacts = contactService.getFilteredContacts("");
        assertEquals(filteredContacts, dbService.getAllContacts());
    }

    @Test
    //  ^.*[lkn].*$ - возвращает контакты, которые НЕ содержат букв l, k, n
    public void testGetFilteredContacts_NOT_lkn() {
        LOGGER.info("testGetFilteredContacts_NOT_lkn()");
        DBService dbService = new DBServiceDummy();
        ContactServiceImpl contactService = new ContactServiceImpl(dbService);
        Collection<Contact> filteredContacts = contactService.getFilteredContacts("^.*[lkn].*$");
        Collection<Contact> expectedFilteredContacts = new ArrayList<Contact>();
        expectedFilteredContacts.add(new Contact(1, "Ted Mosby"));
        assertEquals(filteredContacts, expectedFilteredContacts);
    }

    @Test
    //  ^T.*$ - возвращает контакты, которые НЕ начинаются с T
    public void testGetFilteredContacts_NOT_startWith_T() {
        LOGGER.info("testGetFilteredContacts_NOT_startWith_T()");
        DBService dbService = new DBServiceDummy();
        ContactServiceImpl contactService = new ContactServiceImpl(dbService);
        Collection<Contact> filteredContacts = contactService.getFilteredContacts("^T.*$");
        Collection<Contact> expectedFilteredContacts = new ArrayList<Contact>();
        expectedFilteredContacts.add(new Contact(2, "Barney Stinson"));
        expectedFilteredContacts.add(new Contact(3, "Robin Scherbatsky"));
        expectedFilteredContacts.add(new Contact(4, "Marshall Eriksen"));
        expectedFilteredContacts.add(new Contact(5, "Lily Aldrin"));
        assertEquals(filteredContacts, expectedFilteredContacts);
    }

}