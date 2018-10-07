package anonymous.frontend.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import anonymous.base.Contact;
import anonymous.base.ContactService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class ContactServlet extends HttpServlet {
    private final ContactService contactService;

    public ContactServlet(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameFilter = request.getParameter("nameFilter");
        if (nameFilter != null) {
            Collection<Contact> contacts = contactService.getFilteredContacts(nameFilter);
            if (contacts != null && !contacts.isEmpty()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JsonObject json = new JsonObject();
                json.add("contacts", new Gson().toJsonTree(contacts));
                response.getWriter().write(json.toString());
                response.getWriter().flush();
                response.setStatus(HttpServletResponse.SC_OK);
            } else
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}