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
    static final int TALLEST = 5700;
    static final int MEDIUM = 4000;
    static final int SHORTY = 2500;

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

        // Reset the encoder to 0
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Stops coasting
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        boolean bottom = false;
        boolean top = false;
        int conestack = 4;
        boolean squarepressedfirsttime = true;
        while (opModeIsActive()) {

            /*****************************/
            /** Driving Control Section **/
            /*****************************/

            double slowSpeed = 0.5;
            if (gamepad1.left_bumper) {
                slowSpeed = 0.5;
            } else {
                slowSpeed = 0.8;
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
            if (gamepad2.left_bumper) {
                intakeWheel.setPower(1.0);
                intakeWheelDeux.setPower(-1.0);
            } else if (gamepad2.right_bumper) {
                intakeWheel.setPower(-1.0);
                intakeWheelDeux.setPower(1.0);
            } else {
                intakeWheel.setPower(0.0);
                intakeWheelDeux.setPower(0.0);
            }


            // put in code from CalibrateSlide
            if (mag.isPressed()) {
                if (!bottom) {
                    bottom = true;
                    slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
            } else {
                bottom = false;
            }
            //5361
            //if (tickYeah(slideMotor) >= 3000) {
            //    top = true;
            //} else {
            //    top = false;
            //}

            if (gamepad2.circle) {
                if (!top) {
                    slideMotor.setPower(-0.5);
                } else {
                    slideMotor.setPower(0.05);
                }
            } else if (gamepad2.cross) {

                if (!bottom) {
                    slideMotor.setPower(0.5);
                } else {
                    slideMotor.setPower(0.0);
                }

                // press dpad up = raise to top pole height

            } else if (gamepad2.dpad_up) {
                slideMotor.setPower(ticRamp(TALLEST,slideMotor.getCurrentPosition(),-1.0));
            } else if (gamepad2.dpad_down) {
                if (bottom) {
                    slideMotor.setPower(0.0);
                } else {
                    slideMotor.setPower(0.5);
                }
            } else if (gamepad2.dpad_right) {
                slideMotor.setPower(ticRamp(MEDIUM,slideMotor.getCurrentPosition(),-1.0));
            } else if (gamepad2.dpad_left) {
                slideMotor.setPower(ticRamp(SHORTY,slideMotor.getCurrentPosition(),-1.0));
            } else {
                slideMotor.setPower(0);
            }

            // press dpad down = lower to ground

           if(gamepad2.triangle){
               slideMotor.setPower(ticRamp(-240*conestack,slideMotor.getCurrentPosition(),-1.0));
           }

            if(gamepad2.square){
                if (squarepressedfirsttime) {
                    squarepressedfirsttime=false;
                    conestack--;
                    if (conestack == 0){
                        conestack=4;
                    }
                }
            } else {
                squarepressedfirsttime = true;
            }


            telemetry.addData("ticks", slideMotor.getCurrentPosition());
            telemetry.update();
        }
    }



    public int tickYeah(DcMotor motor)
    {
        int ticks = motor.getCurrentPosition();
        if(ticks >= 0)
            return ticks;
        else
        {
            return ticks * -1;
        }
    }

    private double ticRamp(int goal, int cur, double power) {
        double val = 0.0;

        if(Math.abs(goal) - Math.abs(cur) <= 300) {
            val = power * (Math.abs(goal) - Math.abs(cur)) / 300;
        } else {
            val = power;
        }
        return val;
    }
}