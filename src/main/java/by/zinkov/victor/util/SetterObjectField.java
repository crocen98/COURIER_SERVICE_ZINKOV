package by.zinkov.victor.util;

import by.zinkov.victor.dao.AbstractJdbcDao;
import by.zinkov.victor.dao.exception.DaoException;

import java.lang.reflect.Field;

public class SetterObjectField {

    public void setField(Object object, String fieldName, Object settedObject) throws DaoException {
        try {
            Field field = AbstractJdbcDao.class.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(object, settedObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DaoException("Failed to set connection for transactional DAO. ", e);
        }
    }
}

