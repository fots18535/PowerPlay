package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmartRobotEyeballs;
import org.firstinspires.ftc.teamcode.SmartRobotEyeballsCGP;

@TeleOp
public class SleeveTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SmartRobotEyeballsCGP coneProphet = new SmartRobotEyeballsCGP(this);
        coneProphet.initialize();
        waitForStart();
        while (opModeIsActive()) {
            idle();
        }
    }
}
