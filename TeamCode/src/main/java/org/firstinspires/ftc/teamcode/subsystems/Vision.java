package org.firstinspires.ftc.teamcode.subsystems;

import com.seattlesolvers.solverslib.command.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class Vision implements Subsystem {
    public static final Vision INSTANCE = new Vision();

    private static final boolean USE_WEBCAM = true;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    private boolean I_AM_BLUE;
    private int BLUE_TAG_ID =  20;
    private int GPP_TAG_ID =  21;
    private int PGP_TAG_ID =  22;
    private int PPG_TAG_ID =  23;
    private int RED_TAG_ID =  24;

    public TargetPose targetPose;
    public int target_id = 0;

    public class TargetPose {
        AprilTagPoseFtc pose;
        int id;
    }

    private List<Integer> ob_arr = new ArrayList<Integer>(3);
    private Timer m_timer;

    private Vision() { }

    private void init(HardwareMap hardwareMap, ActiveOpMode opMode) {
        I_AM_BLUE = opMode.i_am_blue();
        m_timer = new Timer();
        targetPose = new TargetPose();
        targetPose.id = 0;

        // initialize ob_arr
        ob_arr.add(GPP_TAG_ID);
        ob_arr.add(PGP_TAG_ID);
        ob_arr.add(PPG_TAG_ID);

        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "webcam"));
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }

    public void setAlliance(boolean iAmBlue) {
        I_AM_BLUE = iAmBlue;
    }

    public void close() {
        visionPortal.close();
    }

    public int myTimeWaitForTarget(double time) {
        int target_id = 0;
        Timer timer = new Timer();
        timer.resetTimer();
        while (target_id == 0 & timer.getElapsedTimeSeconds() < time) {
            targetPose = getTargetPose();
            target_id = targetPose.id;
        }
        return target_id;
    }

    public int myWaitForTarget() {
        int target_id = 0;
        Timer timer = new Timer();
        timer.resetTimer();
        while (target_id == 0) {
            targetPose = getTargetPose();
            target_id = targetPose.id;
        }
        return target_id;
    }

    public TargetPose getTargetPose() {
        int targetId = I_AM_BLUE ? BLUE_TAG_ID : RED_TAG_ID;
        TargetPose targetPose = new TargetPose();
        targetPose.id = 0;
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == targetId) {
                    targetPose.pose = detection.ftcPose;
                    targetPose.id = detection.id;
                }
            }   // end for() loop
        }
        return targetPose;
    }

    public int getGreenSpot() {
        int i = getObeliskTag();
        if (i == GPP_TAG_ID) {return 0;}
        if (i == PGP_TAG_ID) {return 1;}
        if (i == PPG_TAG_ID) {return 2;}
        return -1;
    }

    public int getObeliskTag() {
        int yaw_sign = I_AM_BLUE ? -1 : 1;
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (ob_arr.contains(detection.id)) {
                    boolean ans = ob_arr.contains(2);
                    if (detection.ftcPose.yaw * yaw_sign > 0) {
                        return detection.id;
                    }
                }
            }   // end for() loop
        }
        return -1;
    }

}
