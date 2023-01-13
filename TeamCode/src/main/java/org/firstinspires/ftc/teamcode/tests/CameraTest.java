package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmartRobotEyeballs;
import org.firstinspires.ftc.teamcode.SmartRobotEyeballsCGP;

@TeleOp
public class CameraTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SmartRobotEyeballsCGP coneProphet = new SmartRobotEyeballsCGP(this);
        coneProphet.initialize();
        waitForStart();

        double golemCount = 0.0;
        double pizzaCount = 0.0;
        double catCount = 0.0;
        int i = 0;

        while(i < 100)
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

        telemetry.addData("bulb", golemCount);
        telemetry.addData("panel", pizzaCount);
        telemetry.addData("bolt", catCount);
        telemetry.update();

        while (opModeIsActive())
        {
            String seen = coneProphet.whatDoISee();
        }


    }
}
