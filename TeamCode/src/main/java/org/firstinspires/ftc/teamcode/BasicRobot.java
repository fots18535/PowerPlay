package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BasicRobot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor1");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "motor2");
        waitForStart();
        motor1.setPower(1.0);
        motor2.setPower(1.0);
        sleep(300);
        motor1.setPower(0.0);
        motor2.setPower(0.0);
    }
}
