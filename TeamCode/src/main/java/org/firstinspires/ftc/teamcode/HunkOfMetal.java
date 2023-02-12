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
import org.firstinspires.ftc.teamcode.eyeballs.ConeAndPoleHome;
import org.firstinspires.ftc.teamcode.eyeballs.Detection;

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
    DistanceSensor lazerCenter;
    public static final int TALLEST = 5700 - 200;
    public static final int MEDIUM = 4000;
    public static final int SHORTY = 2500;


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
        lazerCenter = mode.hardwareMap.get(DistanceSensor.class, "lazerCenter");
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
        //slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

        while(mode.opModeIsActive() && !mag.isPressed()) {
            if(Math.abs(slideMotor.getCurrentPosition()) < 300)
            {
                slideMotor.setPower(0.2);
            }
        }

        slideMotor.setPower(0.0);
        resetSlideEncoder();
    }

    public int tickYeah(DcMotor motor) {
        int ticks = motor.getCurrentPosition();
        if (ticks >= 0)
            return ticks;
        else {
            return ticks * -1;
        }
    }

    //double ticRampScale = 300.0;
    //double ticRampScale = 150.0;
    private double ticRamp(int goal, int cur, double power) {
        double val = 0.0;
        if (goal == 0 && mag.isPressed()) {
            val = 0.0;
        }
        else if (Math.abs(goal) - Math.abs(cur) <= 200) {
            val = power * (Math.abs(goal) - Math.abs(cur)) / 200;
        } else {
            val = power;
        }

        return val;
    }

    public void resetSlideEncoder()
    {
        // Reset the encoder to 0
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Tells the motor to run until we turn it off
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
public void coneStackAlign(int ticHeight){

    slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));

    intakeWheel.setPower(1.0);
    intakeWheelDeux.setPower(-1.0);
    slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));
    double rpower = .2;
    leftBack.setPower(-rpower);
    leftFront.setPower(-rpower);
    rightBack.setPower(rpower);
    rightFront.setPower(rpower);
    slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));
    ElapsedTime timer = new ElapsedTime();
    timer.reset();

    while (mode.opModeIsActive() && lazerCenter.getDistance(DistanceUnit.INCH) >2.0 && timer.seconds() < 3){
        slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));
    }

    stopMotors();

    intakeWheel.setPower(0.35);
    intakeWheelDeux.setPower(-0.35);


    }
    public void autoAlign(int ticHeight) {
        autoAlign(ticHeight, 12, 7.0);
    }
    public void autoAlign(int ticHeight, double highDistance, double otherDistance) {
        double rightX = 0.0;
        double leftX = 0.0;
        double rightY = 0.0;
        double leftY = 0.0;
        double distance = otherDistance;

        slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));

        if(ticHeight > 5000)
        {
            distance = highDistance;
        }

        LazerAverage lazerAvgLeft = new LazerAverage(lazerLeft, 5);
        LazerAverage lazerAvgRight = new LazerAverage(lazerRight, 5);
        while (mode.opModeIsActive()) {
            //double a = lazerAvgLeft.getDistance();
            //double b = lazerAvgRight.getDistance();

            double a = lazerLeft.getDistance(DistanceUnit.INCH);
            double b = lazerRight.getDistance(DistanceUnit.INCH);

            double correctionValue = 0;
            boolean givePower = true;
            boolean driveFoward = false;

            mode.telemetry.addData("distance", distance);
            mode.telemetry.addData("leftLazer", a);
            mode.telemetry.addData("rightLazer", b);
            mode.telemetry.update();

            if (a < distance || b < distance){
                givePower = false;
                driveFoward = false;
            }

            else if(a > b && b > distance) {

                correctionValue = -1 * (((b - 5) * 0.2) / 11 + 0.05);
                givePower = true;
                driveFoward = true;
            }

            else if (b > a && a > distance) {
                correctionValue = ((a - 5) * 0.2) / 11 + 0.05;
                givePower = true;
                driveFoward = true;
            }

            //new stuff
            else if((a < distance || b < distance) && a - b > .5 ) {

                correctionValue = -1 * (((b - 5) * 0.2) / 11 + 0.05);
                givePower = true;
                driveFoward = false;
            }

            else if ((a < distance || b < distance) && b - a > .5 ) {
                correctionValue = ((a - 5) * 0.2) / 11 + 0.05;
                givePower = true;
                driveFoward = false;
            }

            //end new stuff


            else
            {
                correctionValue = 0;
                givePower = true;
                driveFoward = true;
            }

            slideMotor.setPower(ticRamp(ticHeight, slideMotor.getCurrentPosition(), -1.0));

            if (givePower) {
                if (driveFoward) {
                    rightY = -0.2;
                }
                if(correctionValue > .3)
                {
                    correctionValue = .3;
                }

                if(correctionValue < -.3)
                {
                    correctionValue = -.3;
                }
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
        leftBack.setPower(0.0);
    }

    // Positive power slides left
    // Negative power slides right
    public void chaChaRealSmooth(double power) {
            leftBack.setPower(-power);
            leftFront.setPower(power);
            rightBack.setPower(-power);
            rightFront.setPower(power);
    }

    public Gyro2 gyro()
    {
        return gyro;
    }

    public long getMotor()
    {
        return leftFront.getCurrentPosition();
    }

    ConeAndPoleHome vision;
    public void startOpenCV() {
        vision = new ConeAndPoleHome(mode);
        vision.start();
        vision.setTarget(0);
    }
    public void stopOpenCV() {
        vision.stop();
    }
    public void alignToCone(double inches, double power, double target) {
        double b = -0.00123 * target;

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

            if (tics > inches * ticksPerInch) {
                break;
            }

            // Get the angle and adjust the power to correct
            double rpower = ramp(power, startTime);
            double rightX = 0.0;
            Detection detect = vision.getDetection();
            if(detect != null) {
                rightX = -1.0 * detect.getX() * 0.00123 + b;
            }
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
}



