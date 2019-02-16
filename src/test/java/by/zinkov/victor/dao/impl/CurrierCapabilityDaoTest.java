package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.impl.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CurrierCapability;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class CurrierCapabilityDaoTest extends AbstractDaoTest {
    @Test
    public void insertNewCurrierCapability() throws DaoException {
        GenericDao<CurrierCapability,Integer> CurrierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapability = new CurrierCapability();
        currierCapability.setWork(true);
        currierCapability.setTransportId(1);
        currierCapability.setCurrierId(1);
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
    public void insertTwoCurrierCapabilityAndReadTwoObject() throws DaoException {
        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapabilityOne = new CurrierCapability();
        currierCapabilityOne.setWork(true);

        CurrierCapability currierCapabilityTwo = new CurrierCapability();
        currierCapabilityTwo.setWork(false);

        currierCapabilityDao.persist(currierCapabilityOne);
        currierCapabilityDao.persist(currierCapabilityTwo);

        List<CurrierCapability> currierCapabilities = currierCapabilityDao.getAll();
        Assert.assertEquals(2,currierCapabilities.size());
        Assert.assertEquals(currierCapabilityOne , currierCapabilities.get(0));
        Assert.assertEquals(currierCapabilityTwo , currierCapabilities.get(1));
    }

    @Test
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapabilityOne = new CurrierCapability();
        currierCapabilityOne.setWork(true);

        CurrierCapability currierCapabilityTwo = new CurrierCapability();
        currierCapabilityTwo.setWork(false);

        currierCapabilityDao.persist(currierCapabilityOne);
        currierCapabilityDao.persist(currierCapabilityTwo);


        List<CurrierCapability> currierCapabilitis = currierCapabilityDao.getAll();
        Assert.assertEquals(2,currierCapabilitis.size());
        currierCapabilityDao.delete(currierCapabilityOne);
        currierCapabilitis = currierCapabilityDao.getAll();
        Assert.assertEquals(1,currierCapabilitis.size());
    }

    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapabilityOne = new CurrierCapability();
        currierCapabilityOne.setWork(true);
        CurrierCapability currierCapabilityTwo = new CurrierCapability();
        currierCapabilityTwo.setWork(false);
        currierCapabilityDao.persist(currierCapabilityOne);
        currierCapabilityDao.persist(currierCapabilityTwo);
        CurrierCapability currierCapabilityTest = currierCapabilityDao.getByPK(0);
        Assert.assertEquals(currierCapabilityOne.getId(),currierCapabilityTest.getId());
        Assert.assertEquals(currierCapabilityOne,currierCapabilityTest);
    }

}