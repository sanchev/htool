package db;

import anonymous.base.Host;
import anonymous.db.DBServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(DBServiceImplTest.class.getName());

    private DBServiceImpl dbService;

    @Before
    public void before() {
        String dbiniPath = "classpath:dbinit.sql";
        String url = String.format("mem:test;INIT=RUNSCRIPT FROM '%s'", dbiniPath);

        dbService = new DBServiceImpl(DBServiceImpl.DBType.H2, "test", "test", url);
    }

    @Test
    public void testGetAllHosts() {
        LOGGER.info("testGetAllHosts()");

        Collection<Host> hosts = dbService.getAllHosts();
        LOGGER.info(String.format("All hosts from db: %s", hosts));

        DBServiceDummy dbServiceDummy = new DBServiceDummy();
        Collection<Host> expectedHosts = dbServiceDummy.getAllHosts();
        LOGGER.info(String.format("Expected all hosts: %s", expectedHosts));

        assertEquals(expectedHosts, hosts);
    }

    /*
    @Test(expected = HibernateException.class)
    public void testGetAllHosts_EXCEPTION() {
        LOGGER.info("testGetAllHosts_EXCEPTION()");

        //no url
        dbService = new DBServiceImpl(DBServiceImpl.DBType.H2, "test", "", "");
    }
    */

    @Test
    public void testAddHost() {
        LOGGER.info("testAddHost()");

        Host host = new Host();
        dbService.addHost(host);

        assertEquals(3, host.getId());
    }

    @Test
    public void testGetById() {
        LOGGER.info("testGetById");

        long id = 2;

        Host host = dbService.getById(id);
        LOGGER.info(String.format("Host from db: %s", host));

        DBServiceDummy dbServiceDummy = new DBServiceDummy();
        Host expectedHost = dbServiceDummy.getById(id);
        LOGGER.info(String.format("Expected host: %s", expectedHost));

        assertEquals(expectedHost, host);
    }

    @Test
    public void testUpdateHost() {
        LOGGER.info("testUpdateHost");

        Host host = dbService.getById(2);
        host.getDeviceList().clear();
        boolean result = dbService.updateHost(host);

        assertTrue(result);
        assertEquals(host, dbService.getById(host.getId()));
    }

    @Test
    public void testDeleteHost() {
        LOGGER.info("testDeleteHost");

        Host host = new Host(2, "", "", "", "");
        boolean result = dbService.deleteHost(host);

        assertTrue(result);
        assertEquals(1, dbService.getAllHosts().size());
    }

}