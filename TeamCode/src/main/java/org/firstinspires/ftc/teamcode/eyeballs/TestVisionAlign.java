package org.firstinspires.ftc.teamcode.eyeballs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HunkOfMetal;
@TeleOp
public class TestVisionAlign extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ConeAndPoleHome vision = new ConeAndPoleHome(this);
        HunkOfMetal hunk = new HunkOfMetal(this);
        vision.start();
        hunk.initialize();
        vision.setTarget(0);
        waitForStart();
        while(opModeIsActive()) {
            int midpoint = vision.getWidth() / 2;
            Detection iSeeU = vision.getDetection();

            telemetry.addData("midpoint", midpoint);

            if(iSeeU.getX() > midpoint - 20 && iSeeU.getX() < midpoint + 20)
            {
                hunk.stopMotors();
            }
            else  if (iSeeU.getX() < midpoint) {
                hunk.chaChaRealSmooth(0.3);
            } else if (iSeeU.getX() > midpoint) {
                hunk.chaChaRealSmooth(-0.3);
            }

        }
    }
}
