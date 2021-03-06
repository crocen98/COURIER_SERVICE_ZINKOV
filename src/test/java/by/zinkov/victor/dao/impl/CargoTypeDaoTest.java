package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;

import by.zinkov.victor.dao.GenericDao;

import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.CargoType;
import org.junit.Assert;
import org.junit.Test;


import java.util.List;


public class CargoTypeDaoTest extends AbstractDaoTest {

    @Test
    public void insertNewCargoType() throws DaoException {
        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoType = new CargoType();
        cargoType.setType("trash");


        Assert.assertEquals(null,cargoType.getId());
        cargoTypeDao.persist(cargoType);
        cargoType.setId(null);
        cargoType.setType("trash_two");
        cargoTypeDao.persist(cargoType);
        Assert.assertEquals((Integer)1,cargoType.getId());
        cargoType.setId(null);
        cargoType.setType("trash_tree");
        cargoTypeDao.persist(cargoType);
        Assert.assertEquals((Integer)2,cargoType.getId());
    }


    @Test(expected = DaoException.class)
    public void insertExistingCargoTypeAndThrowException() throws DaoException {
        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoType = new CargoType();
        cargoType.setType("trash");
        Assert.assertEquals(null,cargoType.getId());
        cargoTypeDao.persist(cargoType);
        Assert.assertEquals((Integer) 0,cargoType.getId());
        cargoTypeDao.persist(cargoType);
    }

    @Test
    public void insertTwoCargoTypeAndReadTwoObject() throws DaoException {
        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoTypeOne = new CargoType();
        cargoTypeOne.setType("trash");

        CargoType cargoTypeTwo = new CargoType();
        cargoTypeTwo.setType("trash two");

        cargoTypeDao.persist(cargoTypeOne);
        cargoTypeDao.persist(cargoTypeTwo);

        List<CargoType> cargoTypes = cargoTypeDao.getAll();
        Assert.assertEquals(2,cargoTypes.size());
        Assert.assertEquals(cargoTypeOne , cargoTypes.get(0));
        Assert.assertEquals(cargoTypeTwo , cargoTypes.get(1));
    }

    @Test
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoTypeOne = new CargoType();
        cargoTypeOne.setType("trash");

        CargoType cargoTypeTwo = new CargoType();
        cargoTypeTwo.setType("trash two");

        cargoTypeDao.persist(cargoTypeOne);
        cargoTypeDao.persist(cargoTypeTwo);


        List<CargoType> cargoTypes = cargoTypeDao.getAll();
        Assert.assertEquals(2,cargoTypes.size());
        cargoTypeDao.delete(cargoTypeOne);
        cargoTypes = cargoTypeDao.getAll();
        Assert.assertEquals(1,cargoTypes.size());
    }

    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<CargoType,Integer>  cargoTypeDao = JdbcDaoFactory.getInstance().getDao(CargoType.class);
        CargoType cargoTypeOne = new CargoType();
        cargoTypeOne.setType("trash");

        CargoType cargoTypeTwo = new CargoType();
        cargoTypeTwo.setType("trash two");

        cargoTypeDao.persist(cargoTypeOne);
        cargoTypeDao.persist(cargoTypeTwo);

        CargoType cargoTypeTest = cargoTypeDao.getByPK(0);
        Assert.assertEquals(cargoTypeOne.getId(),cargoTypeTest.getId());
        Assert.assertEquals(cargoTypeOne,cargoTypeTest);
    }

}