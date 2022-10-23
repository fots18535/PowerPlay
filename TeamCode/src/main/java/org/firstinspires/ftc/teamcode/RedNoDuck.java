package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class RedNoDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Detector d = new Detector(this);
        d.start();

        waitForStart();
        while(opModeIsActive()) {

        }
    }

}