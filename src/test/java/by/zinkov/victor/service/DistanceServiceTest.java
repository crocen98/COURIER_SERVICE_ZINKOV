package by.zinkov.victor.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceServiceTest {

    private static final DistanceService service = new DistanceService();
    @Test
    public void  shouldGetCordAndReturnDataEqualTestData(){
        double distance = service.calculate("53.898277,27.551989" , "53.022145,27.547259");
        System.out.println(distance);
    }

}