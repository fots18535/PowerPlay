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

        // Things the robot needs to know
        double driveSpeed = 0.7;
        int conestack = 4; //number of cones not on the floor


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

        //Get reading from Gyroscope
        //ToDo: create a gyro2 object and reset it
        //Gyro2 gyro = hunk.gyro();
        //gyro.reset();

        // Move away from wall
        hunk.forward(driveSpeed,2);
        // Slide left 24 inches
        hunk.chaChaRealSmooth(driveSpeed,28.65);
        // Go forward 26 inches
        hunk.forwardWithArm(driveSpeed, 26, HunkOfMetal.TALLEST);
        //Turn toward pole
        hunk.turnLeft(35,driveSpeed);
        //Raise cone
       // hunk.raiseCone(HunkOfMetal.TALLEST);
        //Auto align check start position
        long loco = hunk.getMotor();
        telemetry.addData("loco", loco);
        hunk.autoAlign(HunkOfMetal.TALLEST);
        //Measure distance driven
        long locoAfter = hunk.getMotor();
        telemetry.addData("locoAfter", locoAfter);
        hunk.outakeCone(HunkOfMetal.TALLEST);
        telemetry.addData("divide", (locoAfter - loco) / 59.0);
        telemetry.update();

        //Backup the same distance we drove forward
        //ToDo: check the wheel encoder again and backup
        hunk.forwardWithArm(-1*driveSpeed, Math.abs((locoAfter - loco) / 59.0),0);
        hunk.turnRight(35, driveSpeed);

        // Linear slide down
        //hunk.lowerCone();

        //Drive straight to the third row
        hunk.forward(driveSpeed, 26);

        //Turn right 90 degrees
        hunk.turnRight(80, driveSpeed);

        //drive foward
        hunk.forward(driveSpeed, 34);

        //raise arm
        hunk.raiseCone(240*conestack);

        //autoalign
        hunk.autoAlign(240*conestack);

        //go forward and pick up cone
        hunk.coneStackAlign();

        hunk.forward(-.2,1);

        //Raise cone so don't knock over stack
        hunk.raiseCone(HunkOfMetal.SHORTY);

        hunk.forwardWithArm(-1*driveSpeed, 22, HunkOfMetal.SHORTY);

        hunk.turnRight(35,.5);

        hunk.autoAlign(HunkOfMetal.SHORTY);

        hunk.outakeCone(HunkOfMetal.SHORTY);

        hunk.forward(-1,6);








        //Start a loop here?

        //Drive toward the cone stack
        //hunk.forward(driveSpeed,????);

        //Align with the cone stack using the camera


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

