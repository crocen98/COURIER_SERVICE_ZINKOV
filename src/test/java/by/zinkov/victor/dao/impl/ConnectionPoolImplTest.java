package by.zinkov.victor.dao.impl;

import by.zinkov.victor.dao.ConnectionPool;
import by.zinkov.victor.dao.exception.ConnectionPoolException;
import by.zinkov.victor.dao.impl.pool.ConnectionPoolImpl;
import org.junit.*;
import org.mockito.Mockito;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ConnectionPoolImplTest {

    private int N_THREADS = 40;
    private Integer POOL_CAPACITY = 20;

    @Test
    public void shouldGetConnection() throws InterruptedException {
        ConnectionPool connectionPool = Mockito.spy(ConnectionPoolImpl.getInstance());
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        Set<Integer> hashCodes = Collections.synchronizedSet(new HashSet<>());
        IntStream.range(0, N_THREADS).forEach(i -> executorService.submit(() -> {

            try (Connection connection = connectionPool.retrieveConnection()) {
                Thread.sleep(1_00L);
                Assert.assertTrue(connection instanceof Proxy);
                int hashCode = connection.hashCode();
                hashCodes.add(hashCode);
            } catch (SQLException | IllegalStateException e) {
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            } catch (ConnectionPoolException e) {
            }
        }));
        executorService.awaitTermination(5L, TimeUnit.SECONDS);
        Assert.assertEquals(POOL_CAPACITY, (Integer) hashCodes.size());
        Mockito.verify(((ConnectionPoolImpl) connectionPool),
                Mockito.times(N_THREADS)).putBackConnection(Mockito.any());
    }

}