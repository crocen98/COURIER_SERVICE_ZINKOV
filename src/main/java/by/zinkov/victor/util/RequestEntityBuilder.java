package by.zinkov.victor.util;

import by.zinkov.victor.dao.Identified;
import by.zinkov.victor.util.excepton.EntityFromRequestBuilderException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestEntityBuilder {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd, HH:mm";

    public <T extends Identified<Integer>> T build(HttpServletRequest request, Class<T> entityClass, String... expectedFields) throws EntityFromRequestBuilderException {
        try {
            T instance = entityClass.newInstance();
            for (String fieldName : expectedFields) {
                Field field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                String fieldNameToParameter = entityFieldToRequestField(fieldName);
                String parameter = request.getParameter(fieldNameToParameter);
                Class fieldType = field.getType();

                if (fieldType == String.class) {
                    field.set(instance, parameter);
                } else if (fieldType == Integer.class) {
                    field.set(instance, Integer.valueOf(parameter));
                } else if (fieldType == Byte.class) {
                    field.set(instance, Byte.valueOf(parameter));
                } else if (fieldType == BigDecimal.class) {
                    field.set(instance, BigDecimal.valueOf(Long.parseLong(parameter)));
                } else if (fieldType == Timestamp.class) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
                    Date date = dateFormat.parse(parameter);
                    Timestamp timestamp = new Timestamp(date.getTime());
                    field.set(instance, timestamp);
                }
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot create entity! Problem in Instantiation!", e);
        } catch (NumberFormatException | ParseException e) {
            throw new EntityFromRequestBuilderException("Cannot create entity! Problem in parsing numbers or Date!", e);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Cannot create entity! Problem in params of method!", e);
        }
    }

    private String entityFieldToRequestField(String fieldName) {
        char[] chars = fieldName.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char oneChar : chars) {
            if (Character.isUpperCase(oneChar)) {
                builder.append("_");
            }
            builder.append(oneChar);
        }
        return builder.toString().toLowerCase();
    }
}
