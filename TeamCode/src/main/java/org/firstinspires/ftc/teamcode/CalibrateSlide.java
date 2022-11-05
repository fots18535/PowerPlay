package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class CalibrateSlide extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
        // Reset the encoder to 0
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while(opModeIsActive()){

            if(gamepad1.a) {
                slideMotor.setPower(1.0);
            } else if(gamepad1.b) {
                slideMotor.setPower(-1.0);
            } else {
                slideMotor.setPower(0);
            }

            telemetry.addData("ticks", slideMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
