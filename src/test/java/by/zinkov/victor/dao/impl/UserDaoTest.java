package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.AbstractDaoTest;
import by.zinkov.victor.dao.GenericDao;
import by.zinkov.victor.dao.exception.DaoException;
import by.zinkov.victor.dao.factory.JdbcDaoFactory;
import by.zinkov.victor.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class UserDaoTest extends AbstractDaoTest {
    @Test
    public void insertNewUser24() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }

    @Test
    public void insertNewUser() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        System.out.println("tttttttttttttttttttttttt");
        userDao.persist(user);
        System.out.println("end");
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

       userDao.persist(user2);
       Assert.assertEquals((Integer)1,user2.getId());
    }

    @Test
    public void insertNewUser7() throws DaoException {
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
        System.out.println("ttttt222222222222222");
        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }
    @Test
    public void insertNewUser2() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }
    @Test
    public void insertNewUser3() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }
    @Test(expected = DaoException.class)
    public void insertExistingUserAndThrowException() throws DaoException {
        GenericDao<User,Integer>  userDao = JdbcDaoFactory.getInstance().getDao(User.class);
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
        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer) 0,user.getId());
        userDao.persist(user);
    }
    @Test
    public void insertNewUser4() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }

    @Test
    public void insertNewUser5() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }
    @Test
    public void insertNewUser6() throws DaoException {
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

        Assert.assertEquals(null,user.getId());
        userDao.persist(user);
        Assert.assertEquals((Integer)0,user.getId());

        User user2 = new User();
        user2.setLocation("trash");
        user2.setLogin("crocen988");
        user2.setLastName("Zinkov");
        user2.setFirstName("Victor");
        user2.setEmail("s@icloud.com");
        user2.setPhone("+35291052630");
        user2.setUserStatus(1);
        user2.setUserRoleId(2);
        user2.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(user2);
        Assert.assertEquals((Integer)1,user2.getId());
    }
    @Test
    public void insertTwoUserAndReadTwoObject() throws DaoException {
        GenericDao<User,Integer>  userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User userOne = new User();
        userOne.setLocation("trash");
        userOne.setLogin("crocen98");
        userOne.setLastName("Zinkov");
        userOne.setFirstName("Victor");
        userOne.setEmail("s@icloud.com");
        userOne.setPhone("+35291052630");
        userOne.setUserStatus(1);
        userOne.setUserRoleId(2);
        userOne.setPassword("122234234512223423451222342345122234234545555");

        User userTwo = new User();

        userTwo.setLocation("trash");
        userTwo.setLogin("crocen988");
        userTwo.setLastName("Zinkov");
        userTwo.setFirstName("Victor");
        userTwo.setEmail("s@icloud.com");
        userTwo.setPhone("+35291052630");
        userTwo.setUserStatus(1);
        userTwo.setUserRoleId(2);
        userTwo.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(userOne);
        userDao.persist(userTwo);

        List<User> users = userDao.getAll();
        Assert.assertEquals(2,users.size());
        Assert.assertEquals(userOne , users.get(0));
        Assert.assertEquals(userTwo , users.get(1));
    }

    @Test
    public void insertTwoObjectAndDeleteOneTest() throws DaoException {
        GenericDao<User,Integer>  userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User userOne = new User();
        userOne.setLocation("trash");
        userOne.setLogin("crocen98");
        userOne.setLastName("Zinkov");
        userOne.setFirstName("Victor");
        userOne.setEmail("s@icloud.com");
        userOne.setPhone("+35291052630");
        userOne.setUserStatus(1);
        userOne.setUserRoleId(2);
        userOne.setPassword("122234234512223423451222342345122234234545555");

        User userTwo = new User();

        userTwo.setLocation("trash");
        userTwo.setLogin("crocen988");
        userTwo.setLastName("Zinkov");
        userTwo.setFirstName("Victor");
        userTwo.setEmail("s@icloud.com");
        userTwo.setPhone("+35291052630");
        userTwo.setUserStatus(1);
        userTwo.setUserRoleId(2);
        userTwo.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(userOne);
        userDao.persist(userTwo);


        List<User> users = userDao.getAll();
        Assert.assertEquals(2,users.size());
        userDao.delete(userOne);
        users = userDao.getAll();
        Assert.assertEquals(1,users.size());
    }

    @Test
    public void insertTwoObjectAndFindByPKOne() throws DaoException {
        GenericDao<User,Integer>  userDao = JdbcDaoFactory.getInstance().getDao(User.class);
        User userOne = new User();
        userOne.setLocation("trash");
        userOne.setLogin("crocen98");
        userOne.setLastName("Zinkov");
        userOne.setFirstName("Victor");
        userOne.setEmail("s@icloud.com");
        userOne.setPhone("+35291052630");
        userOne.setUserStatus(1);
        userOne.setUserRoleId(2);
        userOne.setPassword("122234234512223423451222342345122234234545555");

        User userTwo = new User();

        userTwo.setLocation("trash");
        userTwo.setLogin("crocen988");
        userTwo.setLastName("Zinkov");
        userTwo.setFirstName("Victor");
        userTwo.setEmail("s@icloud.com");
        userTwo.setPhone("+35291052630");
        userTwo.setUserStatus(1);
        userTwo.setUserRoleId(2);
        userTwo.setPassword("122234234512223423451222342345122234234545555");

        userDao.persist(userOne);
        userDao.persist(userTwo);

        User userTest = userDao.getByPK(0);
        Assert.assertEquals(userOne.getId(),userTest.getId());
        Assert.assertEquals(userOne,userTest);
    }
}