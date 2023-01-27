package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HunkOfMetal {
    DcMotor leftBack;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor rightFront;
    Gyro2 gyro;
    DcMotor gandalfStaff;
    LinearOpMode mode;
    TouchSensor maggot;
    DcMotor slideMotor;
    DcMotor eyeball;
    CRServo intakeWheelDeux;
    CRServo intakeWheel;
    TouchSensor mag;
    DistanceSensor lazerLeft;
    DistanceSensor lazerRight;
    public static final int TALLEST = 5700;
    public static final int MEDIUM = 4000;
    public static final int SHORTY = 2500;
    double distance = 7.0;

    float ticksPerInch = 59.0f;
    float gyroCorrection = -0.04f;
    float slideTicksPerInch = 68.24f;

    public HunkOfMetal(LinearOpMode op) {
        mode = op;
    }

    public void initialize() {
        BNO055IMU imu = mode.hardwareMap.get(BNO055IMU.class, "imu");
        gyro = new Gyro2(imu, mode);


        leftBack = mode.hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = mode.hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = mode.hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = mode.hardwareMap.get(DcMotor.class, "rightFront");
        intakeWheel = mode.hardwareMap.get(CRServo.class, "leftIntake");
        intakeWheelDeux = mode.hardwareMap.get(CRServo.class, "rightIntake");
        slideMotor = mode.hardwareMap.get(DcMotor.class, "slideMotor");
        mag = mode.hardwareMap.get(TouchSensor.class, "magSense");
        lazerLeft = mode.hardwareMap.get(DistanceSensor.class, "lazerLeft");
        lazerRight = mode.hardwareMap.get(DistanceSensor.class, "lazerRight");

        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gyro.startGyro();
    }

    public double ramp(double power, long startTime) {
        // ramp for 0.75 seconds
        long t = System.currentTimeMillis() - startTime;
        if (t >= 750) {
            return power;
        } else {
            return power / 750 * t;
        }
    }

    // Positive power slides left
    // Negative power slides right
    public void chaChaRealSmooth(double power, double length) {
        // Reset the encoder to 0
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Slide until encoder ticks are sufficient
        gyro.reset();
        long startTime = System.currentTimeMillis();
        while (mode.opModeIsActive()) {
            //absolute value of getCurrentPosition()
            int tics = leftFront.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }

            double rpower = ramp(power, startTime);
            float rightX = gyroCorrection * (float) gyro.getAngle();
            leftBack.setPower(rightX - rpower);
            leftFront.setPower(rightX + rpower);
            rightBack.setPower(rightX - rpower);
            rightFront.setPower(rightX + rpower);

            if (tics > length * slideTicksPerInch) {
                break;
            }
            mode.idle();
        }

        // Turn off motors
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void forward(double power, double length) {
        // Reset the encoder to 0
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyro.reset();
        long startTime = System.currentTimeMillis();

        // Go forward until tics reached
        while (mode.opModeIsActive()) {

            //absolute value of getCurrentPosition()
            int tics = leftFront.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }
            //telemetry.addData("debug tics", tics);
            //telemetry.addData("debug compare to ", length*ticksPerInch);

            if (tics > length * ticksPerInch) {
                break;
            }

            // Get the angle and adjust the power to correct
            double rpower = ramp(power, startTime);
            float rightX = gyroCorrection * (float) gyro.getAngle();
            leftBack.setPower(rightX - rpower);
            leftFront.setPower(rightX - rpower);
            rightBack.setPower(rightX + rpower);
            rightFront.setPower(rightX + rpower);

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void forwardNoGyro(double power, double length) {
        // Reset the encoder to 0
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        long startTime = System.currentTimeMillis();

        // Go forward until tics reached
        while (mode.opModeIsActive()) {

            //absolute value of getCurrentPosition()
            int tics = leftFront.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }
            //telemetry.addData("debug tics", tics);
            //telemetry.addData("debug compare to ", length*ticksPerInch);

            if (tics > length * ticksPerInch) {
                break;
            }

            // Get the angle and adjust the power to correct
            double rpower = ramp(power, startTime);
            leftBack.setPower(-rpower);
            leftFront.setPower(-rpower);
            rightBack.setPower(rpower);
            rightFront.setPower(rpower);

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void motorsForward(double power) {
        leftBack.setPower(-power);
        leftFront.setPower(-power);
        rightBack.setPower(power);
        rightFront.setPower(power);
    }

    public void turnRight(double howFar, double speed) {
        //gyro.resetWithDirection(Gyro.RIGHT);
        gyro.reset();
        leftBack.setPower(-speed);
        leftFront.setPower(-speed);
        rightBack.setPower(-speed);
        rightFront.setPower(-speed);

        // Go forward and park behind the line
        while (mode.opModeIsActive()) {
            if (gyro.getAngle() <= -howFar) { //change
                break;
            }

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void turnLeft(double howFar, double speed) {
        //gyro.resetWithDirection(Gyro.LEFT);
        gyro.reset();
        leftBack.setPower(speed);
        leftFront.setPower(speed);
        rightBack.setPower(speed);
        rightFront.setPower(speed);

        // Go forward and park behind the line
        while (mode.opModeIsActive()) {
            if (gyro.getAngle() >= howFar) {
                break;
            }

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }


    // Raise arm to 3 different positions
    // variables declared, not the actual values bruh
    //final int TOP_MIN = 864;
    //final int TOP_MAX = 1000;
    //final int Cap_Max = 800;
    //final int Cap_Min = 700;
    //final int MIDDLE_MIN = 575;
    //final int MIDDLE_MAX = 625;
    //final int BOTTOM_MIN = 246;
    //final int BOTTOM_MAX = 320;
    //final int GROUND_MIN = -100;
    //final int GROUND_MAX = 100;

    public void intakeCone() {
        intakeWheel.setPower(1.0);
        intakeWheelDeux.setPower(-1.0);
        mode.sleep(1000);
        intakeWheel.setPower(0.0);
        intakeWheelDeux.setPower(0.0);
    }

    public void outakeCone(int ticHeight) {
        intakeWheel.setPower(-1.0);
        intakeWheelDeux.setPower(1.0);
        long startTime = System.currentTimeMillis();
        while (mode.opModeIsActive() && System.currentTimeMillis() - startTime < 1000) {
            slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));
        }
        intakeWheel.setPower(0.0);
        intakeWheelDeux.setPower(0.0);
    }

    public void wind() {
        slideMotor.setPower(0.5);
    }
    // Raise cone to certain height based encoder ticks

    public void unwind() {
        slideMotor.setPower(-0.5);
    }
    // Lower cone until sensor triggers

    public void stopWind() {
        slideMotor.setPower(0.0);
    }

    public int getTicks() {
        return slideMotor.getCurrentPosition();
    }

    public void raiseCone(int ticHeight) {
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (mode.opModeIsActive() && tickYeah(slideMotor) < ticHeight - 100) {
            slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));
        }
    }

    public void adjustCone(int ticHeight) {
        while (mode.opModeIsActive() && tickYeah(slideMotor) > ticHeight) {
            slideMotor.setPower(0.5);
        }
    }

    public void lowerCone() {
        slideMotor.setPower(1.0);
        while (mode.opModeIsActive() && !mag.isPressed()) {
        }
        slideMotor.setPower(0.0);
    }

    public int tickYeah(DcMotor motor) {
        int ticks = motor.getCurrentPosition();
        if (ticks >= 0)
            return ticks;
        else {
            return ticks * -1;
        }
    }

    private double ticRamp(int goal, int cur, double power) {
        double val = 0.0;

        if (Math.abs(goal) - Math.abs(cur) <= 300) {
            val = power * (Math.abs(goal) - Math.abs(cur)) / 300;
        } else {
            val = power;
        }

        return val;
    }

    public void forwardWithArm(double power, double length, int ticHeight) {
        // Reset the encoder to 0
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        gyro.reset();
        long startTime = System.currentTimeMillis();

        // Go forward until tics reached
        while (mode.opModeIsActive()) {

            //absolute value of getCurrentPosition()
            int tics = leftFront.getCurrentPosition();
            if (tics < 0) {
                tics = tics * -1;
            }
            //telemetry.addData("debug tics", tics);
            //telemetry.addData("debug compare to ", length*ticksPerInch);

            if (tics > length * ticksPerInch) {
                break;
            }

            // Get the angle and adjust the power to correct
            double rpower = ramp(power, startTime);
            float rightX = gyroCorrection * (float) gyro.getAngle();
            leftBack.setPower(rightX - rpower);
            leftFront.setPower(rightX - rpower);
            rightBack.setPower(rightX + rpower);
            rightFront.setPower(rightX + rpower);

            slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));

            mode.idle();
        }

        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }

    public void autoAlign(int ticHeight) {
        double rightX = 0.0;
        double leftX = 0.0;
        double rightY = 0.0;
        double leftY = 0.0;

        while (mode.opModeIsActive()) {
            double a = lazerLeft.getDistance(DistanceUnit.INCH);
            double b = lazerRight.getDistance(DistanceUnit.INCH);
            double correctionValue = 0;
            boolean givePower = true;

            if(a > b && b > distance && b < 16.0) {

                correctionValue = -1*(((b-5)*0.2)/11+0.05);
                givePower = true;
            }

            else if (b > a && a > distance && a < 16.0) {
                correctionValue = ((a - 5) * 0.2) / 11 + 0.05;
                givePower = true;

            }else if (a < distance || b < distance){
                givePower = false;
            }

            slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));

            if (givePower) {
                rightY = -0.3;

                //leftBack.setPower((correctionValue + rightX) + rightY + leftX);
                //leftFront.setPower((correctionValue + rightX) + rightY - leftX);
                //rightBack.setPower((correctionValue + rightX) - rightY + leftX);
                //rightFront.setPower((correctionValue + rightX) - rightY - leftX);

                leftBack.setPower(rightX + rightY + (-correctionValue + leftX));
                leftFront.setPower(rightX + rightY - (-correctionValue + leftX));
                rightBack.setPower(rightX - rightY + (-correctionValue + leftX));
                rightFront.setPower(rightX - rightY - (-correctionValue + leftX));
            } else {
                stopMotors();
                break;
            }
        }
    }
        public void halfSleep(int ticHigh, int time)
        {
            ElapsedTime timer = new ElapsedTime();
            timer.reset();

            while(mode.opModeIsActive() && timer.milliseconds() < time)
            {
                slideMotor.setPower(ticRamp(ticHigh, slideMotor.getCurrentPosition(), -1.0));
            }
        }



    public void stopMotors() {
        leftFront.setPower(0.0);
        rightBack.setPower(0.0);
        rightFront.setPower(0.0);
    }

    // Positive power slides left
    // Negative power slides right
    public void chaChaRealSmooth(double power) { leftBack.setPower(0.0);


            leftBack.setPower(-power);
            leftFront.setPower(power);
            rightBack.setPower(-power);
            rightFront.setPower(power);

    }
}



