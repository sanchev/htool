package db;

import anonymous.base.Host;
import anonymous.db.DBServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class DBServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(DBServiceImplTest.class.getName());

    @Test
    public void testGetAllHosts() {
        LOGGER.info("testGetAllHosts()");

        DBServiceImpl dbService = new DBServiceImpl(DBServiceImpl.DBType.H2, "test", "", "./h2db_test");
        Collection<Host> hosts = dbService.getAllHosts();
        LOGGER.info(String.format("All hosts from db: %s", hosts));

        DBServiceDummy dbServiceDummy = new DBServiceDummy();
        Collection<Host> expectedHosts = dbServiceDummy.getAllHosts();
        LOGGER.info(String.format("Expected all hosts: %s", hosts));

        assertEquals(hosts, expectedHosts);
    }

    @Test(expected = HibernateException.class)
    public void testGetAllHosts_EXCEPTION() {
        LOGGER.info("testGetAllHosts_EXCEPTION()");

        //no url
        DBServiceImpl dbService = new DBServiceImpl(DBServiceImpl.DBType.H2, "test", "", "");
        dbService.getAllHosts();
    }
}