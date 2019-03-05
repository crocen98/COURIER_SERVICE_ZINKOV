package by.zinkov.victor.service;

import java.util.Arrays;

import static java.lang.Math.*;

public class DistanceService {
    private static final int EARTH_RADIUS = 6371;

    public double calculate(String pointA, String pointB) {
        Object[] coordinatesA = Arrays.stream(pointA.split(",")).map(Double::valueOf).toArray();
        Object[] coordinatesB = Arrays.stream(pointB.split(",")).map(Double::valueOf).toArray();
        return EARTH_RADIUS *
                Math.acos(
                        Math.cos(Math.toRadians((double) coordinatesA[0])) * Math.cos(Math.toRadians((double) coordinatesB[0]))
                                *
                                Math.cos(Math.toRadians((double) coordinatesB[1]) - Math.toRadians((double) coordinatesA[1]))
                                +
                                Math.sin(Math.toRadians((double) coordinatesA[0]))
                                        *
                                        Math.sin(Math.toRadians((double) coordinatesB[0])));
    }
}
