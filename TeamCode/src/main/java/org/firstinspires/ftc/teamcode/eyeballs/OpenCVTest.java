package org.firstinspires.ftc.teamcode.eyeballs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class OpenCVTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ConeAndPoleHome vision = new ConeAndPoleHome(this);
        vision.start();
        vision.setTarget(0);

        waitForStart();
        while (opModeIsActive()) {
            idle();
        }

        vision.stop();
    }
}
