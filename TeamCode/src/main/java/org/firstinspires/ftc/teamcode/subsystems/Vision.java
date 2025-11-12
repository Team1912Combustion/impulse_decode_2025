package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class Vision implements Subsystem {
    public static final Vision INSTANCE = new Vision();
    private Vision() { }
    private static final boolean USE_WEBCAM = true;

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    int BLUE_TAG_ID =  20;
    int RED_TAG_ID =  21;

    @Override
    public void initialize() {
        initAprilTag();
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

    public double[] getTargetPose(boolean amIBlue) {
        int targetId = amIBlue ? BLUE_TAG_ID : RED_TAG_ID;
        double[] targetPose = {0.,0.,0.};

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == targetId) {
                    targetPose[0] = detection.ftcPose.range;
                    targetPose[1] = detection.ftcPose.bearing;
                    targetPose[2] = detection.ftcPose.yaw;
                }
            }   // end for() loop
        }
        return targetPose;
    }

}
