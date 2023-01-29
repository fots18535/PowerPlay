package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class ConePickUpDistanceSensorTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        DistanceSensor lazerIntake = hardwareMap.get(DistanceSensor.class, "lazerIntake");

        waitForStart();

        while (opModeIsActive()) {
            double a = lazerIntake.getDistance(DistanceUnit.INCH);

            telemetry.addData("lazerIntake", "%.2f", a);
            telemetry.update();
        }

    }
    }
