package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.*;
import org.junit.Assert;
import org.junit.Test;



public class SupportedCargoTypesDaoTest extends AbstractDaoTest {
    @Test
    public void insertNewSupportedCargoTypes() throws DaoException {
        GenericDao<User,Integer> userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User user = new User();
        user.setLocation("trash");
        user.setLogin("crocen98");
        user.setLastName("Zinkov");
        user.setFirstName("Victor");
        user.setEmail("s@icloud.com");
        user.setPhone("+35291052630");
        user.setUserStatus(1);
        user.setUserRole(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<TransportType,Integer> transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        transportTypeDao.persist(transportType);

        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapability = new CurrierCapability();
        currierCapability.setWork(true);
        currierCapability.setCurrierId(user.getId());
        currierCapability.setTransportId(transportType.getId());
        Assert.assertEquals(null,currierCapability.getId());
        currierCapabilityDao.persist(currierCapability);

        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoType = new CargoType();
        cargoType.setType("trash");
        Assert.assertEquals(null,cargoType.getId());
        cargoTypeDao.persist(cargoType);

        GenericDao<SupportedCargoTypes,Integer> supportedCargoTypeDao = JdbcDaoFactory.getInstance().getDao(SupportedCargoTypes.class);
        SupportedCargoTypes supportedCargoType = new SupportedCargoTypes();
        supportedCargoType.setCurrierCapabilityId(currierCapability.getId());
        supportedCargoType.setTypeId(cargoType.getId());
        Assert.assertEquals(null,supportedCargoType.getId());
        supportedCargoTypeDao.persist(supportedCargoType);
        supportedCargoType.setId(null);
        supportedCargoTypeDao.persist(supportedCargoType);
        Assert.assertEquals((Integer)1,supportedCargoType.getId());
        supportedCargoType.setId(null);
        supportedCargoTypeDao.persist(supportedCargoType);
        Assert.assertEquals((Integer)2,supportedCargoType.getId());
    }


    @Test(expected = DaoException.class)
    public void insertExistingSupportedCargoTypesAndThrowException() throws DaoException {
        GenericDao<SupportedCargoTypes,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(SupportedCargoTypes.class);
        SupportedCargoTypes cargoType = new SupportedCargoTypes();
        cargoType.setCurrierCapabilityId(1);
        cargoType.setTypeId(1);
        Assert.assertEquals(null,cargoType.getId());
        cargoTypeDao.persist(cargoType);
        Assert.assertEquals((Integer) 0,cargoType.getId());
        cargoTypeDao.persist(cargoType);
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
        user.setUserRole(2);
        user.setPassword("122234234512223423451222342345122234234545555");
        userDao.persist(user);

        GenericDao<TransportType,Integer> transportTypeDao = JdbcDaoFactory.getInstance().getDao(TransportType.class);
        TransportType transportType = new TransportType();
        transportType.setTransportType("trash");
        transportTypeDao.persist(transportType);

        GenericDao<CurrierCapability,Integer>  currierCapabilityDao = JdbcDaoFactory.getInstance().getDao(CurrierCapability.class);
        CurrierCapability currierCapability = new CurrierCapability();
        currierCapability.setWork(true);
        currierCapability.setCurrierId(user.getId());
        currierCapability.setTransportId(transportType.getId());
        Assert.assertEquals(null,currierCapability.getId());
        currierCapabilityDao.persist(currierCapability);

        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoType = new CargoType();
        cargoType.setType("trash");
        Assert.assertEquals(null,cargoType.getId());
        cargoTypeDao.persist(cargoType);






        GenericDao<SupportedCargoTypes,Integer>  supportedCargoTypeDao = JdbcDaoFactory.getInstance().getDao(SupportedCargoTypes.class);
        SupportedCargoTypes cargoTypeOne = new SupportedCargoTypes();
        cargoTypeOne.setCurrierCapabilityId(currierCapability.getId());
        cargoTypeOne.setTypeId(cargoType.getId());

        supportedCargoTypeDao.persist(cargoTypeOne);

        SupportedCargoTypes cargoTypeTest = supportedCargoTypeDao.getByPK(0);
        Assert.assertEquals(cargoTypeOne.getId(),cargoTypeTest.getId());
        Assert.assertEquals(cargoTypeOne,cargoTypeTest);
    }
}