package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class DistanceSensorDrive extends LinearOpMode {
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

            double leftX = gamepad1.left_stick_x;
            double leftY = gamepad1.left_stick_y;
            double rightX = -gamepad1.right_stick_x;
            double rightY = gamepad1.right_stick_y;

            double a = lazerLeft.getDistance(DistanceUnit.INCH);
            double b = lazerRight.getDistance(DistanceUnit.INCH);
            double correctionValue = 0;
            boolean givePower = true;
            telemetry.addData("lazerLeft", "%.2f", a);
            telemetry.addData("lazerRight", "%.2f", b);

          //  if(b > 5.0 && b <  16.0 && a > 16.0){
                if(a>b && b>6.0 && b<16.0) {

                correctionValue = -1*(((b-5)*0.2)/11+0.05);
                givePower = true;
            }
           // else if(a > 5.0 && a < 16.0 && b > 16.0)
            else if (b > a && a > 6.0 && a < 16.0) {
                    correctionValue = ((a - 5) * 0.2) / 11 + 0.05;
                    givePower = true;

                    // } else if( a < 5.0 && b < 5.0 ){
                }else if (a < 6.0 || b < 6.0){
                givePower = false;
            }

            if(givePower) {
                telemetry.addData("CTL", "RX %.2f RY %.2f LX %.2f", rightX, rightY, leftX);
                telemetry.addData("PWR", "LB %.2f LF %.2f RB %.2f RF %.2f",
                        rightX + rightY + leftX,
                        rightX + rightY - leftX,
                        rightX - rightY + leftX,
                        rightX - rightY - leftX);
                telemetry.addData("COR", "%.3f", correctionValue);
                telemetry.addData("ADJ", "LB %.2f LF %.2f RB %.2f RF %.2f",
                        correctionValue + rightX + rightY + leftX,
                        correctionValue + rightX + rightY - leftX,
                        correctionValue + rightX - rightY + leftX,
                        correctionValue + rightX - rightY - leftX);

            rightY = -0.4;

                leftBack.setPower((correctionValue + rightX) + rightY + leftX);
                leftFront.setPower((correctionValue + rightX) + rightY - leftX);
                rightBack.setPower((correctionValue + rightX) - rightY + leftX);
                rightFront.setPower((correctionValue + rightX) - rightY - leftX);
            } else {
                stopMotors();

                telemetry.addData("CTL", "RX %.2f RY %.2f LX %.2f", rightX, rightY, leftX);
                telemetry.addData("PWR", "LB %.2f LF %.2f RB %.2f RF %.2f", 0.0f,0.0f,0.0f,0.0f);
                telemetry.addData("COR", "%.3f", correctionValue);
                telemetry.addData("ADJ", "LB %.2f LF %.2f RB %.2f RF %.2f", 0.0f,0.0f,0.0f,0.0f);

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
