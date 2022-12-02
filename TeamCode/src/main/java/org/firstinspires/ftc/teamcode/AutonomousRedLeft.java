package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutonomousRedLeft extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        SmartRobotEyeballs coneProphet = new SmartRobotEyeballs(this);
        coneProphet.initialize();
        waitForStart();

        // Read cone thing :)
        String iSee = coneProphet.whatDoISee();

        hunk.forward(0.5,2);
        // Slide right 24 inches
        hunk.chaChaRealSmooth(-1.0,19);

        // Go forward 48 inches
        hunk.forward(1.0, 47);

        //Side left 12 inches
        hunk.chaChaRealSmooth(1.0,14);

        // Place cone
        // Linear slide up 34 inches
        hunk.raiseCone(HunkOfMetal.TALLEST - 200);

        hunk.forwardWithArm(0.5,3.5,HunkOfMetal.TALLEST - 200);
        sleep(1000);
        // Release cone
        hunk.outakeCone(HunkOfMetal.TALLEST - 200);
        hunk.forwardWithArm(-1.0,4,HunkOfMetal.TALLEST - 200);
        // Linear slide down 34 inches
        hunk.lowerCone();



        //Turn left 90 degrees
        //hunk.turnLeft(90,1.0);

        // Go forward 18 inches
        //hunk.forward(1.0,18);

        // Pick up cone
        //hunk.raiseCone(1006);
        //hunk.forwardWithArm(0.5,8,1006);

        // Go back 18 inches
        //hunk.forward(-1.0,18);

        // Turn right 90 degrees
        //hunk.turnRight(90,1.0);

        // Place cone

        {
            iSee = "";
        }
        // Park according to cone
        if(iSee.equals("1 Bolt")){
            hunk.chaChaRealSmooth(1.0,35);
        }
        else if(iSee.equals("2 Bulb")){
            hunk.chaChaRealSmooth(1.0,11);
        }
        else{
            hunk.chaChaRealSmooth(-1.0,11);
        }

    }
}
