package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.TransportType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;


public class TransportTypeDaoTest extends AbstractDaoTest {

   

    @Test
    public void insertNewTransportType() throws DaoException {
        GenericDao<TransportType,Integer> transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        transportType.setCoefficient(new BigDecimal(23));
        Assert.assertEquals(null,transportType.getId());
        transportTypeDao.persist(transportType);

        transportType.setId(null);
        transportType.setTransportType("trash_two");

        transportTypeDao.persist(transportType);
        Assert.assertEquals((Integer)1,transportType.getId());
        transportType.setId(null);
        transportType.setTransportType("tree");

        transportTypeDao.persist(transportType);
        Assert.assertEquals((Integer)2,transportType.getId());
    }


    @Test(expected = DaoException.class)
    public void insertExistingTransportTypeAndThrowException() throws DaoException {
        GenericDao<TransportType,Integer>  transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        Assert.assertEquals(null,transportType.getId());
        transportTypeDao.persist(transportType);
        Assert.assertEquals((Integer) 0,transportType.getId());
        transportTypeDao.persist(transportType);
    }

    @Test
    public void insertTwoTransportTypeAndReadTwoObject() throws DaoException {
        GenericDao<TransportType,Integer>  transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportTypeOne = new TransportType();
        transportTypeOne.setTransportType("trash");
        TransportType transportTypeTwo = new TransportType();
        transportTypeTwo.setTransportType("trash two");

        transportTypeDao.persist(transportTypeOne);
        transportTypeDao.persist(transportTypeTwo);

        List<TransportType> transportTypes = transportTypeDao.getAll();
        Assert.assertEquals(2,transportTypes.size());
        Assert.assertEquals(transportTypeOne , transportTypes.get(0));
        Assert.assertEquals(transportTypeTwo , transportTypes.get(1));
    }



    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<TransportType,Integer>  transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportTypeOne = new TransportType();
        transportTypeOne.setTransportType("trash");

        TransportType transportTypeTwo = new TransportType();
        transportTypeTwo.setTransportType("trash two");

        transportTypeDao.persist(transportTypeOne);
        transportTypeDao.persist(transportTypeTwo);

        TransportType transportTypeTest = transportTypeDao.getByPK(0);
        Assert.assertEquals(transportTypeOne.getId(),transportTypeTest.getId());
        Assert.assertEquals(transportTypeOne,transportTypeTest);
    }
}