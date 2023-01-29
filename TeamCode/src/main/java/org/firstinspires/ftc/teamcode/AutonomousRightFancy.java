package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

@Autonomous
public class AutonomousRightFancy extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HunkOfMetal hunk = new HunkOfMetal(this);
        hunk.initialize();

        SmartRobotEyeballsCGP coneProphet = new SmartRobotEyeballsCGP(this);
        coneProphet.initialize();
        waitForStart();

        // Read cone thing :)
        double golemCount = 0.0;
        double pizzaCount = 0.0;
        double catCount = 0.0;
        int i = 0;
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(i < 100 && timer.seconds() < 7)
        {
            String seen = coneProphet.whatDoISee();

            if(seen != null)
            {
                i++;

                if(seen.equals("c"))
                {
                    catCount++;
                }
                else if(seen.equals("g"))
                {
                    golemCount++;
                }
                else if(seen.equals("p"))
                {
                    pizzaCount++;
                }
            }

        }

        hunk.forward(0.5,2);
        // Slide left 24 inches
        hunk.chaChaRealSmooth(0.5,28.65);

        // Go forward 48 inches
        hunk.forward(0.5, 26);
        //Turn 45 degrees
        hunk.turnLeft(35,0.5);
        //Raise cone
        hunk.raiseCone(HunkOfMetal.TALLEST);
        //Auto align
        hunk.autoAlign(HunkOfMetal.TALLEST);
        //hunk.outakeCone(HunkOfMetal.TALLEST);
        hunk.halfSleep(HunkOfMetal.TALLEST, 100000);

        //Side right 12 inches
//        hunk.chaChaRealSmooth(-0.5,13);
//
//        // Place cone
//        // Linear slide up 34 inches
//        hunk.raiseCone(HunkOfMetal.TALLEST - 100);
//
//        hunk.forwardWithArm(0.5,2.0,HunkOfMetal.TALLEST - 100);
//        sleep(1000);
//
//
//        //hunk.autoAlign(HunkOfMetal.TALLEST - 100);
//        //hunk.halfSleep(HunkOfMetal.TALLEST - 100, 1000);
//        //hunk.halfSleep(HunkOfMetal.TALLEST - 300, 1000);
//
//        // Release cone
//        hunk.outakeCone(HunkOfMetal.TALLEST - 100);
//        hunk.forwardWithArm(-0.5,3,HunkOfMetal.TALLEST - 100);
//        // Linear slide down 34 inches
//        hunk.lowerCone();
//
//
//
//        //Turn left 90 degrees
//        //hunk.turnLeft(90,1.0);
//
//        // Go forward 18 inches
//        //hunk.forward(1.0,18);
//
//
//        // Pick up cone
//        //hunk.raiseCone(1006);
//        //hunk.forwardWithArm(0.5,8,1006);
//
//        // Go back 18 inches
//        //hunk.forward(-1.0,18);
//
//        // Turn right 90 degrees
//        //hunk.turnRight(90,1.0);
//
//        // Place cone
//
//
//        // Park according to cone
//        if(catCount > golemCount && catCount > pizzaCount){
//            hunk.chaChaRealSmooth(0.5,11);
//        }
//        else if(golemCount > catCount && golemCount > pizzaCount){
//            hunk.chaChaRealSmooth(-0.5,11);
//        }
//        else{
//            hunk.chaChaRealSmooth(-0.5,37);
//        }
    }
}

