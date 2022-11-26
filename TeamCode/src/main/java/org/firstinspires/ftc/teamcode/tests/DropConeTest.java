package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HunkOfMetal;

@Autonomous
public class DropConeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();
        waitForStart();

        hunk.intakeCone();
       // hunk.placeCone();
        //hunk.raiseCone();
        //hunk.outakeCone();
        //hunk.lowerCone();

    }
}
