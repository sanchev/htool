package anonymous.base;

import java.util.Collection;

public interface ContactService {
    Collection<Contact> getFilteredContacts(String regex);
}