package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Autonomous1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        waitForStart();

        // Read cone thing :)

        // Go forward 60 inches
        hunk.forward(1.0,60);

        // Slide right 12 inches
        hunk.chaChaRealSmooth(-1.0,12);

        // Go forward 8 inches
        hunk.forward(1.0, 8);

        // Place cone

        // Go back 8 inches
        hunk.forward(-1.0,8);

        // Turn left 90 degrees
        hunk.turnLeft(90,1.0);

        // Go forward 40 inches
        hunk.forward(1.0,40.0);

        // Pickup cone

        // Go back 40 inches
        hunk.forward(-1.0,40.0);

        // Turn right 90 degrees
        hunk.turnRight(90,1.0);

        // Go forward 8 inches
        hunk.forward(1.0,8);

        // Place cone

        // Based on cone thing, park 1, 2, ou 3
    }
}
