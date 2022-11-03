package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutonomousRED extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        SmartRobotEyeballs coneProphet = new SmartRobotEyeballs(this);
        coneProphet.initialize();
        waitForStart();

        // Read cone thing :)
        String iSee = coneProphet.whatDoISee();

        // Slide right 24 inches
        hunk.chaChaRealSmooth(-1.0,24);

        // Go forward 48 inches
        hunk.forward(1.0, 48);

        //Side left 12 inches
        hunk.chaChaRealSmooth(1.0,12);

        // Place cone
        // Linear slide up 34 inches
        hunk.forward(1.0,2);
        // Release cone
        hunk.forward(-1.0,2);
        // Linear slide down 34 inches

        //Turn left 90 degrees
        hunk.turnLeft(90,1.0);

        // Go forward 18 inches
        hunk.forward(1.0,18);

        // Pick up cone

        // Go back 18 inches
        hunk.forward(-1.0,18);

        // Turn right 90 degrees
        hunk.turnRight(90,1.0);

        // Place cone

        // Park according to cone
        if(iSee.equals("1 Bolt")){
            hunk.chaChaRealSmooth(1.0,36);
        }
        else if(iSee.equals("2 Bulb")){
            hunk.chaChaRealSmooth(1.0,12);
        }
        else{
            hunk.chaChaRealSmooth(-1.0,12);
        }

    }
}
