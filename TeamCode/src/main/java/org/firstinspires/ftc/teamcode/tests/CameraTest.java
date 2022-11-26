package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmartRobotEyeballs;

@TeleOp
public class CameraTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SmartRobotEyeballs coneProphet = new SmartRobotEyeballs(this);
        coneProphet.initialize();
        waitForStart();
        while (opModeIsActive())
        {
            String seen = coneProphet.whatDoISee();
            telemetry.addData("value", seen);
            telemetry.update();
        }
    }
}
