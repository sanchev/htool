package frontend.servlets;

import anonymous.frontend.HostServiceImpl;
import anonymous.frontend.servlets.HosttServlet;
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

        Mockito.when(request.getParameter("identityFilter")).thenReturn("^.*[rk].*$");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        HosttServlet hosttServlet = new HosttServlet(new HostServiceImpl(new DBServiceDummy()));
        hosttServlet.doPost(request, response);

        verify(response).setContentType("application/json");

        String result = stringWriter.getBuffer().toString().trim();
        LOGGER.info(String.format("JSON: %s", result));
        String expectedResult = "{\"hosts\":[{\"id\":1,\"ip\":\"10.0.7.183/32\",\"identity\":\"shved_nataljya\",\"device\":null,\"login\":\"admin\",\"password\":\"120960\"}]}";
        LOGGER.info(String.format("JSON: %s", expectedResult));
        assertEquals(result, expectedResult);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoPost_SC_BAD_REQUEST() throws IOException, ServletException {
        LOGGER.info("testDoPost_SC_BAD_REQUEST()");

        HosttServlet hosttServlet = new HosttServlet(new HostServiceImpl(new DBServiceDummy()));
        hosttServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testDoPost_SC_NO_CONTENT() throws IOException, ServletException {
        LOGGER.info("testDoPost_SC_NO_CONTENT()");

        Mockito.when(request.getParameter("identityFilter")).thenReturn("^.*$");

        HosttServlet hosttServlet = new HosttServlet(new HostServiceImpl(new DBServiceDummy()));
        hosttServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}