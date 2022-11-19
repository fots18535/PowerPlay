package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class CalibrateSlide extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
        TouchSensor mag = hardwareMap.get(TouchSensor.class, "magSense");
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset the encoder to 0
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        boolean bottom = false;
        boolean top = false;
        while(opModeIsActive()){

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
                    slideMotor.setPower(0.5);
                }
                else
                {
                    slideMotor.setPower(0.05);
                }
            } else if(gamepad1.circle) {
                if(!bottom) {
                    slideMotor.setPower(-0.5);
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
}
