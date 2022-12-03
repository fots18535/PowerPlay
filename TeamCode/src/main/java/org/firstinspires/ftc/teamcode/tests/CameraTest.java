package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmartRobotEyeballs;

@TeleOp
public class CameraTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SmartRobotEyeballs coneProphet = new SmartRobotEyeballs(this);
        coneProphet.initialize();
        waitForStart();

        double bulbCount = 0.0;
        double panelCount = 0.0;
        double boltCount = 0.0;
        int i = 0;

        while(i < 100)
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

        telemetry.addData("bulb", bulbCount);
        telemetry.addData("panel", panelCount);
        telemetry.addData("bolt", boltCount);
        telemetry.update();

        while (opModeIsActive())
        {
            String seen = coneProphet.whatDoISee();
        }


    }
}
