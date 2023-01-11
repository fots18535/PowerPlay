package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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
        double bulbCount = 0.0;
        double panelCount = 0.0;
        double boltCount = 0.0;
        int i = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(i < 100 && timer.seconds() < 7)
        {
            String seen = coneProphet.whatDoISee();

            if(seen != null)
            {
                i++;

                if(seen.equals("1 Bolt"))
                {
                    boltCount++;
                }
                else if(seen.equals("2 Bulb"))
                {
                    bulbCount++;
                }
                else if(seen.equals("3 Panel"))
                {
                    panelCount++;
                }
            }

        }

        hunk.forward(0.5,2);
        // Slide right 24 inches
        hunk.chaChaRealSmooth(-0.5,19);

        // Go forward 48 inches
        hunk.forward(0.5, 50);

        //Side left 12 inches
        hunk.chaChaRealSmooth(0.5,14);

        // Place cone
        // Linear slide up 34 inches
        hunk.raiseCone(HunkOfMetal.TALLEST - 100);

        hunk.forwardWithArm(0.5,2.0,HunkOfMetal.TALLEST - 100);
        sleep(1000);

        // Add code for auto alignment
        hunk.autoAlign(HunkOfMetal.TALLEST - 100);

        // Release cone
        hunk.outakeCone(HunkOfMetal.TALLEST - 100);
        hunk.forwardWithArm(-0.5,3,HunkOfMetal.TALLEST - 100);
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

        // Park according to cone
        if(boltCount > bulbCount && boltCount > panelCount){
            hunk.chaChaRealSmooth(0.5,35);
        }
        else if(bulbCount > boltCount && bulbCount > panelCount){
            hunk.chaChaRealSmooth(0.5,11);
        }
        else{
            hunk.chaChaRealSmooth(-0.5,11);
        }

    }
}
