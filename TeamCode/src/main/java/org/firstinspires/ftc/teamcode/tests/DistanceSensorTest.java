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

            double a = lazerLeft.getDistance(DistanceUnit.INCH);
            double b = lazerRight.getDistance(DistanceUnit.INCH);
            telemetry.addData("lazerLeft", a);
            telemetry.addData("lazerRight", b);



           if(b > 5.0 && b < 12.0 && a > 12.0)
            {
                telemetry.addData("scenario", "B");
                telemetry.addData("turn", "right 40");
                //telemetry.addData("forward", (b - 5) * Math.cos(Math.toRadians(90.0 - 50.0)));
                //telemetry.addData("slide", (b - 5) * Math.sin(Math.toRadians(90.0 - 50.0)));
            }
            else if(a > 5.0 && a < 12.0 && b > 12.0)
            {
                telemetry.addData("scenario", "C");
                telemetry.addData("turn", " left 40");
                //telemetry.addData("forward", (a - 5) * Math.cos(Math.toRadians(90.0 - 50.0)));
                //telemetry.addData("slide", (a - 5) * Math.sin(Math.toRadians(90.0 - 50.0)));
            }else {
               telemetry.addData("scenario", "?");
               telemetry.addData("turnm  m ", "?");
           }

            telemetry.update();

        }
    }
}
