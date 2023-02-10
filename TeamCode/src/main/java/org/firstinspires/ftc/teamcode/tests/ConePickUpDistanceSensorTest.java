package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class ConePickUpDistanceSensorTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        DistanceSensor lazerCenter = hardwareMap.get(DistanceSensor.class, "lazerCenter");

        waitForStart();

        while (opModeIsActive()) {
            double a = lazerCenter.getDistance(DistanceUnit.INCH);

            telemetry.addData("lazerCenter", "%.2f", a);
            telemetry.update();
        }

    }
    }

