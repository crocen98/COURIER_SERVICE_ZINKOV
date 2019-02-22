package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CurrierCapability;
import by.zinkov.victor.domain.TransportType;
import by.zinkov.victor.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class CurrierCapabilityDaoTest extends AbstractDaoTest {
    @Test
    public void insertNewCurrierCapability() throws DaoException {
        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRoleId(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<TransportType,Integer> transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        transportTypeDao.persist(transportType);




        GenericDao<CurrierCapability,Integer> CurrierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapability = new CurrierCapability();
        currierCapability.setWork(true);
        currierCapability.setTransportId(transportType.getId());
        currierCapability.setCurrierId(user.getId());
        Assert.assertEquals(null,currierCapability.getId());
        CurrierCapabilityDao.persist(currierCapability);
        currierCapability.setId(null);
        CurrierCapabilityDao.persist(currierCapability);
        Assert.assertEquals((Integer)1,currierCapability.getId());
        currierCapability.setId(null);
        CurrierCapabilityDao.persist(currierCapability);
        Assert.assertEquals((Integer)2,currierCapability.getId());
    }


    @Test(expected = DaoException.class)
    public void insertExistingCurrierCapabilityAndThrowException() throws DaoException {
        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability CurrierCapability = new CurrierCapability();
        CurrierCapability.setWork(true);
        Assert.assertEquals(null,CurrierCapability.getId());
        currierCapabilityDao.persist(CurrierCapability);
        Assert.assertEquals((Integer) 0,CurrierCapability.getId());
        currierCapabilityDao.persist(CurrierCapability);
    }

    @Test
    public void insertOneCurrierCapabilityAndReadOneObject() throws DaoException {

        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRoleId(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<TransportType,Integer> transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        transportTypeDao.persist(transportType);

        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapabilityOne = new CurrierCapability();
        currierCapabilityOne.setWork(true);
        currierCapabilityOne.setTransportId(transportType.getId());
        currierCapabilityOne.setCurrierId(user.getId());


        currierCapabilityDao.persist(currierCapabilityOne);

        List<CurrierCapability> currierCapabilities = currierCapabilityDao.getAll();
        Assert.assertEquals(1,currierCapabilities.size());
        Assert.assertEquals(currierCapabilityOne , currierCapabilities.get(0));

    }



    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRoleId(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<TransportType,Integer> transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        transportTypeDao.persist(transportType);

        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapabilityOne = new CurrierCapability();
        currierCapabilityOne.setWork(true);
        currierCapabilityOne.setTransportId(transportType.getId());
        currierCapabilityDao.persist(currierCapabilityOne);
        CurrierCapability currierCapabilityTest = currierCapabilityDao.getByPK(0);
        Assert.assertEquals(currierCapabilityOne.getId(),currierCapabilityTest.getId());
        Assert.assertEquals(currierCapabilityOne,currierCapabilityTest);
    }

}