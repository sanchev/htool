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
import java.util.List;

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
    //  ^.*[1].*$ - возвращает хосты, идентификаторы которых НЕ содержат цифру 1
    public void testGetFilteredHosts_NOT_1() {
        LOGGER.info("testGetFilteredHosts_NOT_1()");
        DBService dbService = new DBServiceDummy();
        HostServiceImpl hostService = new HostServiceImpl(dbService);
        Collection<Host> filteredHosts = hostService.getFilteredHosts("^.*[1].*$");
        Collection<Host> expectedFilteredHosts = new ArrayList<>();
        expectedFilteredHosts.add(((List<Host>) dbService.getAllHosts()).get(1));
        assertEquals(filteredHosts, expectedFilteredHosts);
    }
}