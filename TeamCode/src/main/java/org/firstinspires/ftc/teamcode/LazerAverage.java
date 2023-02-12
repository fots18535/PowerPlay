package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;

public class LazerAverage {
    DistanceSensor distance;
    double[] values;
    boolean reset = true;
    int index = 0;

    public LazerAverage(DistanceSensor distance, int averageSize) {
        this.distance = distance;
        this.values = new double[averageSize];
    }

    public void reset() {
        reset = true;
        index = 0;
    }

    public double getDistance() {
        double dist = distance.getDistance(DistanceUnit.INCH);
        if(reset) {
            reset = false;
            Arrays.fill(values, dist);
        }

        if(index == values.length) {
            index = 0;
        }

        values[index] = dist;
        index++;

        double avg = 0.0;
        for(double v : values) {
            avg += v;
        }
        avg = avg / values.length;

        return avg;
    }
}
