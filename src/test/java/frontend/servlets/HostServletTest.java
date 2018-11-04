package frontend.servlets;

import anonymous.frontend.HostServiceImpl;
import anonymous.frontend.servlets.HostServlet;
import db.DBServiceDummy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class HostServletTest {

    private static final Logger LOGGER = LogManager.getLogger(HostServletTest.class.getName());

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoPost_SC_OK() throws IOException, ServletException {
        LOGGER.info("testDoPost_SC_OK()");

        Mockito.when(request.getParameter("identityFilter")).thenReturn("^.*[1].*$");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        HostServlet hostServlet = new HostServlet(new HostServiceImpl(new DBServiceDummy()));
        hostServlet.doPost(request, response);

        verify(response).setContentType("application/json");

        String result = stringWriter.getBuffer().toString().trim();
        LOGGER.info(String.format("JSON: %s", result));
        String expectedResult = "{\"host\":[{\"host_id\":2,\"ip\":\"10.0.0.2\",\"identity\":\"host_2\",\"deviceList\":[{\"device_id\":2,\"vendor\":\"vendor_2\",\"hardware\":\"hardware_2\",\"software\":\"software_2\"}],\"login\":\"login_2\",\"password\":\"password_2\"}]}";
        LOGGER.info(String.format("JSON: %s", expectedResult));
        assertEquals(result, expectedResult);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoPost_SC_BAD_REQUEST() throws IOException, ServletException {
        LOGGER.info("testDoPost_SC_BAD_REQUEST()");

        HostServlet hostServlet = new HostServlet(new HostServiceImpl(new DBServiceDummy()));
        hostServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testDoPost_SC_NO_CONTENT() throws IOException, ServletException {
        LOGGER.info("testDoPost_SC_NO_CONTENT()");

        Mockito.when(request.getParameter("identityFilter")).thenReturn("^.*$");

        HostServlet hostServlet = new HostServlet(new HostServiceImpl(new DBServiceDummy()));
        hostServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}