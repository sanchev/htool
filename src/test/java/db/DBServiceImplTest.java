package db;

import anonymous.base.Contact;
import anonymous.db.DBServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class DBServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(DBServiceImplTest.class.getName());

    @Test
    public void testGetAllContacts() {
        LOGGER.info("testGetAllContacts()");

        DBServiceImpl dbService = new DBServiceImpl(DBServiceImpl.DBType.H2, "test", "test", "./h2db_test");
        Collection<Contact> contacts = dbService.getAllContacts();
        LOGGER.info(String.format("All contacts from db: %s", contacts));

        DBServiceDummy dbServiceDummy = new DBServiceDummy();
        Collection<Contact> expectedContacts = dbServiceDummy.getAllContacts();
        LOGGER.info(String.format("Expected all contacts: %s", contacts));

        assertEquals(contacts, expectedContacts);
    }

    @Test(expected = HibernateException.class)
    public void testGetAllContacts_EXCEPTION() {
        LOGGER.info("testGetAllContacts_EXCEPTION()");

        //no url
        DBServiceImpl dbService = new DBServiceImpl(DBServiceImpl.DBType.H2, "test", "test", "");
        Collection<Contact> contacts = dbService.getAllContacts();
    }
}