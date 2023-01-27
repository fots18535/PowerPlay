package org.firstinspires.ftc.teamcode.eyeballs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;

public class ConeAndPoleHome {
    OpenCvWebcam webcam;
    IconDeterminationPipeline pipeline;
    LinearOpMode op;

    public ConeAndPoleHome(LinearOpMode op) {
        this.op = op;
    }

    public void start() {
        int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(op.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new IconDeterminationPipeline();
        webcam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }

    public void stop() {
        webcam.stopStreaming();
        webcam.closeCameraDevice();
    }

    public static class IconDeterminationPipeline extends OpenCvPipeline {
        // HSV threshold values
        Scalar blueLower = new Scalar(107, 131, 37);
        Scalar blueUpper = new Scalar(124, 221, 252);

        Scalar redLower = new Scalar(78, 78, 37);
        Scalar redUpper = new Scalar(112, 255, 255);

        Scalar poleLower = new Scalar(78, 78, 37);
        Scalar poleUpper = new Scalar(112, 255, 255);

        Scalar lower = new Scalar(0, 0, 0);
        Scalar upper = new Scalar(0, 0, 0);

        Detection currentDetection = new Detection(0, 0, 0);

        /*
         * Working variables
         */
        Mat hsv = new Mat();
        Mat mask = new Mat();
        Mat mask3chan = new Mat();
        Mat output = new Mat();
        int width;

        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(6, 6));
        Mat hierarchy = new Mat();

        public void setTarget(int target) {
            if (target == 0) {
                lower = blueLower;
                upper = blueUpper;

            } else if (target == 1) {
                lower = redLower;
                upper = redUpper;
            } else if (target == 2) {
                lower = poleLower;
                upper = poleUpper;
            }
        }

        @Override
        public void init(Mat firstFrame) {
            Imgproc.cvtColor(firstFrame, hsv, Imgproc.COLOR_RGB2HSV);
            Core.inRange(hsv, lower, upper, mask);
            //Imgproc.cvtColor(mask, mask3chan, Imgproc.COLOR_GRAY2RGB);

            width = firstFrame.width();
            int h = firstFrame.height();
        }

        @Override
        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Core.inRange(hsv, lower, upper, mask);
            //Imgproc.cvtColor(mask, mask3chan, Imgproc.COLOR_GRAY2RGB);

            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            double biggestArea = 0;
            int biggestX = 0;
            int biggestY = 0;
            int biggestIndex = -1;

            for (int i = 0; i < contours.size(); i++) {
                MatOfPoint contour = contours.get(i);

                // area of the blob
                double area = Imgproc.contourArea(contour);

                // minimum bounding rectangle of the blob
                Rect mbr = Imgproc.boundingRect(contour);

                // center of mass of the blob
                Moments p = Imgproc.moments(contour, false);
                int x = (int) (p.get_m10() / p.get_m00());
                int y = (int) (p.get_m01() / p.get_m00());

                if (area > biggestArea) {
                    biggestArea = area;
                    biggestX = x;
                    biggestY = y;
                    biggestIndex = i;
                }
            }

            if (contours.size() > 0) {
                currentDetection.setArea(biggestArea);
                currentDetection.setX(biggestX);
                currentDetection.setY(biggestY);

                Imgproc.drawContours(input, contours, biggestIndex, new Scalar(255, 0, 0), 2);
                Imgproc.circle(input, new Point(biggestX, biggestY), 5, new Scalar(255, 0, 0), 2);
            }

            return input;
        }

        public Detection getDetection()
        {
            return currentDetection.clone();
        }

        public int getWidth()
        {
            return width;
        }
    }

    public void setTarget(int target) {
        if(pipeline != null) {
            pipeline.setTarget(target);
        }
    }
    public Detection getDetection()
    {
        return pipeline.getDetection();
    }

    public int getWidth()
    {
        return pipeline.getWidth();
    }
}
