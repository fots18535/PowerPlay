package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.checkerframework.checker.units.qual.min;

@TeleOp
public class ManualDriveDookie extends LinearOpMode {

    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;

    @Override
    public void runOpMode() throws InterruptedException {
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        CRServo intakeWheel = hardwareMap.get(CRServo.class, "leftIntake");
        CRServo intakeWheelDeux = hardwareMap.get(CRServo.class, "rightIntake");
        DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TouchSensor mag = hardwareMap.get(TouchSensor.class, "magSense");

        // Reset the encoder to 0
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Stops coasting
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        boolean bottom = false;
        boolean top = false;
        while (opModeIsActive()) {

            /*****************************/
            /** Driving Control Section **/
            /*****************************/

            double slowSpeed = 1.0;
            if (gamepad1.left_bumper) {
                slowSpeed = 0.5;
            } else {
                slowSpeed = 1.0;
            }
            //Get the input from the gamepad controller
            double leftX = gamepad1.left_stick_x * slowSpeed;
            double leftY = gamepad1.left_stick_y * slowSpeed;
            double rightX = -gamepad1.right_stick_x * slowSpeed;
            double rightY = gamepad1.right_stick_y * slowSpeed;

            leftBack.setPower(rightX + rightY + leftX);
            leftFront.setPower(rightX + rightY - leftX);
            rightBack.setPower(rightX - rightY + leftX);
            rightFront.setPower(rightX - rightY - leftX);


            // put in code from IntakeTest
            if (gamepad1.a) {
                intakeWheel.setPower(1.0);
                intakeWheelDeux.setPower(-1.0);
            } else if (gamepad1.b) {
                intakeWheel.setPower(-1.0);
                intakeWheelDeux.setPower(1.0);
            } else {
                intakeWheel.setPower(0.0);
                intakeWheelDeux.setPower(0.0);
            }
        }

        // put in code from CalibrateSlide
        if(mag.isPressed()) {
            if(!bottom) {
                bottom = true;
                slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
        } else {
            bottom = false;
        }
        //5361
        if(slideMotor.getCurrentPosition() <= -3000) {
            top = true;
        } else {
            top = false;
        }

        if(gamepad1.cross) {
            if (!top) {
                slideMotor.setPower(-0.5);
            }
            else
            {
                slideMotor.setPower(0.05);
            }
        } else if(gamepad1.circle) {
            if(!bottom) {
                slideMotor.setPower(0.5);
            }
            else
            {
                slideMotor.setPower(0.0);
            }
        } else {
            slideMotor.setPower(0);
        }

        telemetry.addData("ticks", slideMotor.getCurrentPosition());
        telemetry.update();
    }
}