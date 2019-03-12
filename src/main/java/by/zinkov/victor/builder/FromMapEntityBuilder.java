package by.zinkov.victor.builder;

import by.zinkov.victor.dao.Identified;
import by.zinkov.victor.service.ServiceException;

import java.util.Map;

public interface FromMapEntityBuilder<T extends Identified<Integer>> {
    T build(Map<String,String> requestParams) throws ServiceException;

}
