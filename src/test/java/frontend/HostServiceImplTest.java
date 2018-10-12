package frontend;

import anonymous.base.DBService;
import anonymous.base.Host;
import anonymous.frontend.HostServiceImpl;
import db.DBServiceDummy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class HostServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(HostServiceImplTest.class.getName());


    @Test
    public void testGetFilteredHosts_All() {
        LOGGER.info("testGetFilteredHosts_All()");
        DBService dbService = new DBServiceDummy();
        HostServiceImpl hostService = new HostServiceImpl(dbService);
        Collection<Host> filteredHosts = hostService.getFilteredHosts("");
        assertEquals(filteredHosts, dbService.getAllHosts());
    }

    @Test
    //  ^.*[rk].*$ - возвращает контакты, которые НЕ содержат букв r, k
    public void testGetFilteredHosts_NOT_rk() {
        LOGGER.info("testGetFilteredHosts_NOT_rk()");
        DBService dbService = new DBServiceDummy();
        HostServiceImpl hostService = new HostServiceImpl(dbService);
        Collection<Host> filteredHosts = hostService.getFilteredHosts("^.*[rk].*$");
        Collection<Host> expectedFilteredHosts = new ArrayList<>();
        expectedFilteredHosts.add(new Host(1, "10.0.7.183/32", "shved_nataljya", "", "admin", "120960"));
        assertEquals(filteredHosts, expectedFilteredHosts);
    }


    @Test
    //  ^s.*$ - возвращает контакты, которые НЕ начинаются с s
    public void testGetFilteredHosts_NOT_startWith_s() {
        LOGGER.info("testGetFilteredHosts_NOT_startWith_s()");
        DBService dbService = new DBServiceDummy();
        HostServiceImpl contactService = new HostServiceImpl(dbService);
        Collection<Host> filteredHosts = contactService.getFilteredHosts("^s.*$");
        Collection<Host> expectedFilteredHosts = new ArrayList<>();
        expectedFilteredHosts.add(new  Host(2, "10.0.7.186/32", "kurta_ivan", "", "admin", "120960"));
        assertEquals(filteredHosts, expectedFilteredHosts);
    }

}