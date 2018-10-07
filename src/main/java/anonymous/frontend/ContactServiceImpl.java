package anonymous.frontend;

import anonymous.base.Contact;
import anonymous.base.ContactService;
import anonymous.base.DBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class ContactServiceImpl implements ContactService {
    private static final Logger LOGGER = LogManager.getLogger(ContactServiceImpl.class.getName());

    private final DBService dbService;

    public ContactServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    public Collection<Contact> getFilteredContacts(String regex) {
        LOGGER.info(String.format("regex: %s", regex));
        Collection<Contact> contacts = new ArrayList<Contact>();
        Pattern pattern = Pattern.compile(regex);
        Collection<Contact> allContacts = dbService.getAllContacts();
        LOGGER.info(String.format("All contacts from db: %s", allContacts));
        if (allContacts == null)
            return null;
        for (Contact contact : allContacts) {
            if (!pattern.matcher(contact.getName()).matches())
                contacts.add(contact);
        }
        LOGGER.info(String.format("Filtered contacts: %s", contacts));
        return contacts;
    }
}