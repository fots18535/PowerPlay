package org.firstinspires.ftc.teamcode.eyeballs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HunkOfMetal;

@Autonomous
public class TrackToConeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();
        hunk.startOpenCV();

        waitForStart();

        hunk.alignToCone(12, 0.5, 325);

        while(opModeIsActive()) {

        }

        hunk.stopOpenCV();
    }
}
