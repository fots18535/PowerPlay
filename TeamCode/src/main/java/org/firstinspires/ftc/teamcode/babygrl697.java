package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous
public class babygrl697 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ConceptTensorFlowObjectDetection baby = new ConceptTensorFlowObjectDetection(this);
        baby.initialize();
        waitForStart();
        while (opModeIsActive()){
            baby.whatDoISee();
            telemetry.addData("lAbEl", baby.whatDoISee());
            telemetry.update();
        }
    }
}
