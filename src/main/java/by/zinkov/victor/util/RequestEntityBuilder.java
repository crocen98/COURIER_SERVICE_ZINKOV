package by.zinkov.victor.util;

import by.zinkov.victor.dao.Identified;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.util.excepton.EntityFromRequestBuilderException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestEntityBuilder {
    private static final String DATETIME_PATTERNN = "yyyy-MM-dd, HH:mm";

    public <T extends Identified<Integer>> T build(HttpServletRequest request, Class<T> entityClass) throws EntityFromRequestBuilderException {
        try {
            T instance = entityClass.newInstance();
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String parameterNameToFieldName = entityFieldToRequestField(fieldName);
                String parameter = request.getParameter(parameterNameToFieldName);
                if (parameter == null) {
                    continue;
                }
                Class fieldType = field.getType();

                if (fieldType == String.class) {
                    field.set(instance, parameter);
                } else if (fieldType == Integer.class) {
                    field.set(instance, Integer.valueOf(parameter));
                } else if (fieldType == Byte.class) {
                    field.set(instance, Byte.valueOf(parameter));
                } else if (fieldType == BigDecimal.class) {
                    field.set(instance, BigDecimal.valueOf(Long.parseLong(parameter)));
                } else if (fieldType == Date.class) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
                    Date date = dateFormat.parse(parameter);
                    field.set(instance, date);
                }
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityFromRequestBuilderException("Cannot create entity! Problem in Instantiation!", e);
        } catch (NumberFormatException | ParseException e) {
            throw new EntityFromRequestBuilderException("Cannot create entity! Problem in parsing numbers or Date!", e);
        }
    }

    private String entityFieldToRequestField(String fieldName){
        char[] chars = fieldName.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char oneChar : chars){
            if(Character.isUpperCase(oneChar)){
                builder.append("_");
            }
            builder.append(oneChar);
        }
        return builder.toString().toLowerCase();
    }
}
