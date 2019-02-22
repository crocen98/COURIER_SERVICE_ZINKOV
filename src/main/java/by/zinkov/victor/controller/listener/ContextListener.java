package by.zinkov.victor.controller.listener;

import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.pool.ConnectionPoolImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Object "+ConnectionPoolImpl.getInstance() + " loaded in memory.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPoolImpl.getInstance().destroyPool();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }

    }
}