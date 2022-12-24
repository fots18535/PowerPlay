package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HunkOfMetal;

@TeleOp
public class DistanceSensorTest extends LinearOpMode {

    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;

    @Override
    public void runOpMode() throws InterruptedException {
        DistanceSensor lazerLeft = hardwareMap.get(DistanceSensor.class, "lazerLeft");
        DistanceSensor lazerRight = hardwareMap.get(DistanceSensor.class, "lazerRight");

        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        waitForStart();

        while(opModeIsActive()){

            double a = lazerLeft.getDistance(DistanceUnit.INCH);
            double b = lazerRight.getDistance(DistanceUnit.INCH);
            double correctionValue = 0;
            telemetry.addData("lazerLeft", a);
            telemetry.addData("lazerRight", b);



           if(b > 5.0 && b < 16.0 && a > 16.0){

               correctionValue = -1*(((b-5)*0.2)/11+0.05);

               telemetry.addData("scenario", "B");
                telemetry.addData("turn", "right 40");
                adjust(0.0,correctionValue);
                //telemetry.addData("forward", (b - 5) * Math.cos(Math.toRadians(90.0 - 50.0)));
                //telemetry.addData("slide", (b - 5) * Math.sin(Math.toRadians(90.0 - 50.0)));
           }
            else if(a > 5.0 && a < 16.0 && b > 16.0)
            {
                correctionValue = ((a-5)*0.2)/11+0.05;

                telemetry.addData("scenario", "C");
                telemetry.addData("turn", " left 40");
                adjust(0.0,correctionValue);
                //telemetry.addData("forward", (a - 5) * Math.cos(Math.toRadians(90.0 - 50.0)));
                //telemetry.addData("slide", (a - 5) * Math.sin(Math.toRadians(90.0 - 50.0)));
            }else {
                stopMotors();
               telemetry.addData("scenario", "?");
               telemetry.addData("turnm  m ", "?");
           }

            telemetry.update();

        }
        stopMotors();
    }

    private void stopMotors() {
        leftBack.setPower(0.0);
        leftFront.setPower(0.0);
        rightBack.setPower(0.0);
        rightFront.setPower(0.0);
    }

    private void adjust(double power, double correction)
    {
        leftBack.setPower(correction - power);
        leftFront.setPower(correction - power);
        rightBack.setPower(correction + power);
        rightFront.setPower(correction + power);
    }
}
