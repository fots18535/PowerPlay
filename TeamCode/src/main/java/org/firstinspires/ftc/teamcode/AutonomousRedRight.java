package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

@Autonomous
public class AutonomousRedRight extends LinearOpMode {

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
        // Slide left 24 inches
        hunk.chaChaRealSmooth(1.0,24.4);

        // Go forward 48 inches
        hunk.forward(1.0, 46);

        //Side right 12 inches
        hunk.chaChaRealSmooth(-1.0,12);

        // Place cone
        // Linear slide up 34 inches
        hunk.raiseCone(HunkOfMetal.TALLEST - 200);

        hunk.forwardWithArm(0.5,4,HunkOfMetal.TALLEST - 200);
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


        // Park according to cone
        if(boltCount > bulbCount && boltCount > panelCount){
            hunk.chaChaRealSmooth(1.0,11);
        }
        else if(bulbCount > boltCount && bulbCount > panelCount){
            hunk.chaChaRealSmooth(-1.0,11);
        }
        else{
            hunk.chaChaRealSmooth(-1.0,35);
        }
    }
}

