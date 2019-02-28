package by.zinkov.victor.service;

import java.util.Arrays;

import static java.lang.Math.*;

public class DistanceService {
    public double calculate(String pointA, String pointB) {
        Double[] coordinatesA = (Double[]) Arrays.stream(pointA.split(",")).map(Double::valueOf).toArray();
        Double[] coordinatesB = (Double[]) Arrays.stream(pointB.split(",")).map(Double::valueOf).toArray();
        double cosD;
        cosD = (sin(coordinatesA[0]) * sin(coordinatesB[0]))
                + (cos(coordinatesA[0]) *
                cos(coordinatesB[0]) *
                cos(coordinatesA[1] - coordinatesB[1]));
        return cosD;
    }
}
