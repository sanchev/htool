package anonymous.frontend.servlets;

import anonymous.base.Host;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import anonymous.base.HostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class HosttServlet extends HttpServlet {
    private final HostService hosttService;

    public HosttServlet(HostService contactService) {
        this.hosttService = contactService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identityFilter = request.getParameter("identityFilter");
        if (identityFilter != null) {
            Collection<Host> hosts = hosttService.getFilteredHosts(identityFilter);
            if (hosts != null && !hosts.isEmpty()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JsonObject json = new JsonObject();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                json.add("hosts", gson.toJsonTree(hosts));
                response.getWriter().write(json.toString());
                response.getWriter().flush();
                response.setStatus(HttpServletResponse.SC_OK);
            } else
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}