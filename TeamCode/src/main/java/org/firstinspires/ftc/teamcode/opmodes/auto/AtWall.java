package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ActiveOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import java.util.ArrayList;


@Autonomous
public class AtWall {
    private static Follower follower;
    private static int pathState;
    private static Telemetry telemetry;

    private static Timer pathTimer, actionTimer, opmodeTimer;

    private static Pose startPose = null;
    private static PathChain park;

    private static ElapsedTime waittimer = new ElapsedTime();

    ArrayList<Boolean> buttonArray = new ArrayList<>();
    int booleanIncrementer = 0;

    public static void buildPaths(boolean I_AM_BLUE) {

        // Poses
        Pose parkPose = null;

        // Poses
        if (I_AM_BLUE) {
            parkPose = new Pose(-48, -24, Math.toRadians(0));
        } else {
            parkPose = new Pose(-48, 24, Math.toRadians(0));
        }

        park = follower.pathBuilder()
                .addPath(new BezierLine(startPose, parkPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), parkPose.getHeading())
                .build();
    }

    public static void autonomousPathUpdate(Follower follower, int pathState) {
        switch (pathState) {

            case 00:
                mywait(1000);
                Catapult.INSTANCE.load();
                mywait(100);
                Catapult.INSTANCE.hold();
                mywait(20000);
                follower.followPath(park);
                setPathState(10);
                break;
            case 10:
                if (!follower.isBusy()) {
                    setPathState(99);
                }
                break;

            default:
                Catapult.INSTANCE.hold();
                Intake.INSTANCE.intakeoff();
                Drive.INSTANCE.stop();

            /* You could check for
            - Follower State: "if(!follower.isBusy()) {}"
            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
            - Robot Position: "if(follower.getPose().getX() > 36) {}"
            */
        }
    }

    public static void mywait(int msec) {
        waittimer.reset();
        while(waittimer.milliseconds() < msec) {}
    }

    public static void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public static void init(HardwareMap hardwareMap, Telemetry m_telemetry, boolean I_AM_BLUE) {
        telemetry = m_telemetry;
        waittimer.reset();

        AutoSettings.INSTANCE.readAutoConfig();

        Drive.INSTANCE.init(hardwareMap);
        Catapult.INSTANCE.init(hardwareMap);
        Intake.INSTANCE.init(hardwareMap);
        Vision.INSTANCE.setAlliance(AutoSettings.INSTANCE.iAmBlue());

        telemetry.update();
        telemetry.addData(">", "hardware init complete.");
        follower = Constants.createFollower(hardwareMap);
        if (I_AM_BLUE) {
            startPose = new Pose(-63, -24, Math.toRadians(0));
        } else {
            startPose = new Pose(-63, 24, Math.toRadians(0));
        }
        buildPaths(I_AM_BLUE);
        follower.setStartingPose(startPose);
        telemetry.addData(">", "initialization complete.");
        telemetry.update();
    }

    public static void run() {
        ElapsedTime runtime = new ElapsedTime();
        while ( ActiveOpMode.INSTANCE.isActive() && runtime.seconds() < 30) {
            // These loop the movements of the robot, these must be called continuously in order to work
            follower.update();
            autonomousPathUpdate(follower, pathState);

            // Feedback to Driver Hub for debugging
            telemetry.addData("path state", pathState);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.update();
        }
    }

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    public void stop() {}

}