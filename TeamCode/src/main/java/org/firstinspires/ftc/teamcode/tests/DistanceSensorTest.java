package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@TeleOp
public class DistanceSensorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DistanceSensor lazerLeft = hardwareMap.get(DistanceSensor.class, "lazerLeft");
        DistanceSensor lazerRight = hardwareMap.get(DistanceSensor.class, "lazerRight");
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("lazerLeft", lazerLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("lazerRight", lazerRight.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
    }
}
