package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.ftc.ActiveOpMode;

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
    private Vision() { }
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

    @Override
    public void initialize() {
        m_timer = new Timer();
        targetPose = new TargetPose();
        targetPose.id = 0;

        initAprilTag();

        // initialize ob_arr
        ob_arr.add(GPP_TAG_ID);
        ob_arr.add(PGP_TAG_ID);
        ob_arr.add(PPG_TAG_ID);
    }

    public void setAlliance(boolean iAmBlue) {
        I_AM_BLUE = iAmBlue;
    }

    private void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(ActiveOpMode.hardwareMap().get(WebcamName.class, "Webcam 1"));
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }

    public void close() {
        visionPortal.close();
    }

    public Command oldWaitForTarget(double time) {
        return new LambdaCommand()
                .setStart(() -> {
                    m_timer.resetTimer();
                })
                .setUpdate(() -> {
                    targetPose = getTargetPose();
                    target_id = targetPose.id;
                })
                .setStop(interrupted -> {
                    target_id = 0;
                })
                .setIsDone(() -> target_id != 0 || m_timer.getElapsedTimeSeconds() > time)
                .requires(/* subsystems the command implements */)
                .setInterruptible(true)
                .named("waitForTarget");
    }

    public Command waitForTarget(double time) {
        return new LambdaCommand()
                .setStart(() -> {
                    target_id = myTimeWaitForTarget(time);
                })
                .setUpdate(() -> {
                })
                .setStop(interrupted -> {
                    target_id = 0;
                })
                .setIsDone(() -> target_id != 0 || m_timer.getElapsedTimeSeconds() > time)
                .requires(/* subsystems the command implements */)
                .setInterruptible(true)
                .named("waitForTarget");
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
