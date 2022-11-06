package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CRServo intakeWheel = hardwareMap.get(CRServo.class, "leftIntake");
        CRServo intakeWheelDeux = hardwareMap.get(CRServo.class, "rightIntake");

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.a) {
                //intakeWheel.setDirection(DcMotorSimple.Direction.FORWARD);
                //intakeWheelDeux.setDirection(DcMotorSimple.Direction.FORWARD);
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
    }
}
