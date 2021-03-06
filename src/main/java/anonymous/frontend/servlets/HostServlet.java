package anonymous.frontend.servlets;

import anonymous.base.Host;
import anonymous.serialization.AnnotationExclusionStrategy;
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

public class HostServlet extends HttpServlet {
    private final HostService hosttService;

    public HostServlet(HostService contactService) {
        this.hosttService = contactService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String identityFilter = request.getParameter("identityFilter");
        if (identityFilter != null) {
            Collection<Host> hosts = hosttService.getFilteredHosts(identityFilter);
            if (hosts != null && !hosts.isEmpty()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JsonObject json = new JsonObject();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                gsonBuilder.setExclusionStrategies(new AnnotationExclusionStrategy());
                Gson gson = gsonBuilder.create();
                json.add("host", gson.toJsonTree(hosts));
                response.getWriter().write(json.toString());
                response.getWriter().flush();
                response.setStatus(HttpServletResponse.SC_OK);
            } else
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}