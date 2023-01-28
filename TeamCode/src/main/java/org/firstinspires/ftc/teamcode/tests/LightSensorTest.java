package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class LightSensorTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // get a reference to our ColorSensor object.
        RevColorSensorV3 colorSensor = hardwareMap.get(RevColorSensorV3.class, "sensor_color");

        waitForStart();
        while(opModeIsActive()) {
            double light = colorSensor.getRawLightDetected();
            //double proximity = ((DistanceSensor) colorSensor).getDistance(DistanceUnit.INCH);
            telemetry.addData("Distance", light);
            telemetry.update();

        }

    }
}
