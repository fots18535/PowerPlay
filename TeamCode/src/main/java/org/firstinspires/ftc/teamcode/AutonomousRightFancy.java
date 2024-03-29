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
        double driveSpeed = 0.8;
        double turnSpeed = 0.5;
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

        hunk.resetSlideEncoder();

        // Go forward 26 inches
        hunk.forwardWithArm(driveSpeed, 22, HunkOfMetal.MEDIUM);
        //Turn toward pole
        hunk.turnRight(35,turnSpeed);
        hunk.raiseCone(HunkOfMetal.MEDIUM + 100);
        //Raise cone
        //Auto align check start position
        long loco = hunk.getMotor();
        telemetry.addData("loco", loco);
        hunk.autoAlign(HunkOfMetal.MEDIUM + 100, 12.0, 7.0);
        //Measure distance driven
        long locoAfter = hunk.getMotor();
        telemetry.addData("locoAfter", locoAfter);
        hunk.outakeCone(HunkOfMetal.MEDIUM + 100);
        telemetry.addData("divide", (locoAfter - loco) / 59.0);
        telemetry.update();

        //Backup the same distance we drove forward
        //ToDo: check the wheel encoder again and backup
        hunk.forwardWithArm(-1*driveSpeed, Math.abs((locoAfter - loco) / 59.0), HunkOfMetal.MEDIUM);
        hunk.lowerCone();
        hunk.turnLeft(41, turnSpeed);

        // Linear slide down
        //hunk.lowerCone();

        //Drive straight to the third row
        hunk.forward(driveSpeed, 23);

        //Turn right 90 degrees
        hunk.turnRight(85, turnSpeed);

        //drive foward
        hunk.forward(driveSpeed, 34);

        //raise arm
        hunk.raiseCone(240*conestack + 150);

        //autoalign
        hunk.autoAlign(240*conestack + 150, 12, 7.0);
        hunk.raiseCone(240*conestack + 150);

        //go forward and pick up cone
        hunk.coneStackAlign(240*conestack + 150);

        hunk.forward(-.2,1);

        //Raise cone so don't knock over stack
        hunk.raiseCone(HunkOfMetal.SHORTY);

        hunk.forwardWithArm(-1*driveSpeed, 22, HunkOfMetal.SHORTY);

        hunk.turnRight(35,turnSpeed);

        hunk.autoAlign(HunkOfMetal.SHORTY, 12.0, 7.0);

        hunk.outakeCone(HunkOfMetal.SHORTY);

        hunk.forward(-1,6);

        hunk.turnLeft(35,turnSpeed);

        // Park according to cone
        if(catCount > golemCount && catCount > pizzaCount){

            hunk.chaChaRealSmooth(-.4,3);
            hunk.forward(-driveSpeed,23);

        }
        else if(golemCount > catCount && golemCount > pizzaCount){
            hunk.chaChaRealSmooth(-.4,3);
        }
        else {
            hunk.turnLeft(5,turnSpeed);
            hunk.forward(driveSpeed, 23);
            hunk.chaChaRealSmooth(-.4,13);
        }

        hunk.lowerCone();








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

