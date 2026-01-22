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
import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import java.util.ArrayList;


@Autonomous
public class AtGoal {
    private static Follower follower;
    private static int pathState;
    private static Telemetry telemetry;

    private static Timer pathTimer, actionTimer, opmodeTimer;

    private static Pose startPose = null;
    // Path chains
    private static PathChain toRowOne;
    private static PathChain pickupRowOne;
    private static PathChain scoreRowOne;
    private static PathChain toRowTwo;
    private static PathChain pickupRowTwo;
    private static PathChain scoreRowTwo;
    private static PathChain toRowThree;
    private static PathChain pickupRowThree;
    private static PathChain scoreRowThree;
    private static PathChain park;

    private static ElapsedTime waittimer = new ElapsedTime();

    ArrayList<Boolean> buttonArray = new ArrayList<>();
    int booleanIncrementer = 0;

    private static int rowCount = 0;

    public static void buildPaths(boolean I_AM_BLUE) {

        // Poses
        Pose rowOneStart = null;
        Pose rowOneDone = null;
        Pose rowTwoStart = null;
        Pose rowTwoDone = null;
        Pose rowThreeStart = null;
        Pose rowThreeDone = null;
        Pose parkPose = null;

        // Poses
        if (I_AM_BLUE) {
           rowOneStart = new Pose(14, -24, Math.toRadians(-90));
           rowOneDone = new Pose(14, -54, Math.toRadians(-90));
           rowTwoStart = new Pose(-11, -24, Math.toRadians(-90));
           rowTwoDone = new Pose(-11, -54, Math.toRadians(-90));
           rowThreeStart = new Pose(-33.5, -24, Math.toRadians(-90));
           rowThreeDone = new Pose(-33.5, -54, Math.toRadians(-90));
           parkPose = new Pose(56, -24, Math.toRadians(90));
        } else {
            rowOneStart = new Pose(14, 24, Math.toRadians(90));
            rowOneDone = new Pose(14, 54, Math.toRadians(90));
            rowTwoStart = new Pose(-11, 24, Math.toRadians(90));
            rowTwoDone = new Pose(-11, 54, Math.toRadians(90));
            rowThreeStart = new Pose(-33.5, 24, Math.toRadians(90));
            rowThreeDone = new Pose(-33.5, 54, Math.toRadians(90));
            parkPose = new Pose(56, 24, Math.toRadians(-90));
        }

        toRowOne = follower.pathBuilder()
                .addPath(new BezierLine(startPose, rowOneStart))
                .setLinearHeadingInterpolation(startPose.getHeading(), rowOneStart.getHeading())
                .build();

        pickupRowOne = follower.pathBuilder()
                .addPath(new BezierLine(rowOneStart, rowOneDone))
                .setLinearHeadingInterpolation(rowOneStart.getHeading(), rowOneDone.getHeading())
                .build();

        scoreRowOne = follower.pathBuilder()
                .addPath(new BezierLine(rowOneDone, startPose))
                .setLinearHeadingInterpolation(rowOneDone.getHeading(), startPose.getHeading())
                .build();

        toRowTwo = follower.pathBuilder()
                .addPath(new BezierLine(startPose, rowTwoStart))
                .setLinearHeadingInterpolation(startPose.getHeading(), rowTwoStart.getHeading())
                .build();

        pickupRowTwo = follower.pathBuilder()
                .addPath(new BezierLine(rowTwoStart, rowTwoDone))
                .setLinearHeadingInterpolation(rowTwoStart.getHeading(), rowTwoDone.getHeading())
                .setVelocityConstraint(0.5)
                .build();

        scoreRowTwo = follower.pathBuilder()
                .addPath(new BezierCurve(rowTwoDone, rowOneStart, startPose))
                .setLinearHeadingInterpolation(rowTwoDone.getHeading(), startPose.getHeading())
                .build();

        toRowThree = follower.pathBuilder()
                .addPath(new BezierLine(startPose, rowThreeStart))
                .setLinearHeadingInterpolation(startPose.getHeading(), rowThreeStart.getHeading())
                .build();

        pickupRowThree = follower.pathBuilder()
                .addPath(new BezierLine(rowThreeStart, rowThreeDone))
                .setLinearHeadingInterpolation(rowThreeStart.getHeading(), rowThreeDone.getHeading())
                .build();

        scoreRowThree = follower.pathBuilder()
                .addPath(new BezierLine(rowThreeDone, startPose))
                .setLinearHeadingInterpolation(rowThreeDone.getHeading(), startPose.getHeading())
                .build();

        park = follower.pathBuilder()
                .addPath(new BezierLine(startPose, parkPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), parkPose.getHeading())
                .build();
    }

    public static void autonomousPathUpdate(Follower follower, int pathState) {
        switch (pathState) {

            // preload
            case 00:
                mywait(100);
                Catapult.INSTANCE.load();
                mywait(200);
                Catapult.INSTANCE.launch();
                mywait(100);
                Intake.INSTANCE.intakein();
                mywait(100);
                Catapult.INSTANCE.load();
                mywait(220);
                Catapult.INSTANCE.launch();
                mywait(500);
                Catapult.INSTANCE.load();
                mywait(200);
                Catapult.INSTANCE.hold();
                mywait(200);
                Intake.INSTANCE.intakeoff();
                if (rowCount > 0) {
                    setPathState(10);
                } else {
                    setPathState(90);
                }
                break;

            // row 1
            case 10:
                follower.followPath(toRowOne);
                setPathState(11);
                break;
            case 11:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakein();
                    setPathState(12);
                }
                break;
            case 12:
                follower.followPath(pickupRowOne);
                setPathState(13);
                break;
            case 13:
                if (!follower.isBusy()) {
                    // turn off intake before driving;
                    Intake.INSTANCE.intakeoff();
                    setPathState(14);
                }
                break;
            case 14:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    follower.followPath(scoreRowOne);
                    setPathState(15);
                }
                break;
            case 15:
                if (!follower.isBusy()) {
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(100);
                    Catapult.INSTANCE.launch();
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(100);
                    Catapult.INSTANCE.hold();
                    if (rowCount > 1) {
                        setPathState(20);
                    } else {
                        setPathState(90);
                    }
                }
                break;

            // row 2
            case 20:
                follower.followPath(toRowTwo);
                setPathState(21);
                break;
            case 21:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakein();
                    setPathState(22);
                }
                break;
            case 22:
                follower.followPath(pickupRowTwo);
                setPathState(23);
                break;
            case 23:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakeoff();
                    setPathState(24);
                }
                break;
            case 24:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    follower.followPath(scoreRowTwo);
                    setPathState(25);
                }
                break;
            case 25:
                if (!follower.isBusy()) {
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(100);
                    Catapult.INSTANCE.launch();
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(100);
                    Catapult.INSTANCE.hold();
                    if (rowCount > 2) {
                        setPathState(30);
                    } else {
                        setPathState(90);
                    }
                }
                break;

            // row 3
            case 30:
                follower.followPath(toRowThree);
                setPathState(31);
                break;
            case 31:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakein();
                    mywait(100);
                    follower.followPath(pickupRowThree);
                    setPathState(32);
                }
                break;
            case 32:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakein();
                    mywait(100);
                    follower.followPath(scoreRowThree);
                    setPathState(33);
                }
                break;
            case 33:
                if (!follower.isBusy()) {
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(100);
                    Catapult.INSTANCE.launch();
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(100);
                    Catapult.INSTANCE.hold();
                    setPathState(90);
                }
                break;

            // park
            case 90:
                // turn off intake before parking;
                Intake.INSTANCE.intakeoff();
                follower.followPath(park);
                setPathState(91);
                break;
            case 91:
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

    public static void init(HardwareMap hardwareMap, Telemetry m_telemetry,
                            boolean I_AM_BLUE,
                            int m_rowCount) {
        telemetry = m_telemetry;
        rowCount = m_rowCount;
        waittimer.reset();

        Drive.INSTANCE.init(hardwareMap);
        Catapult.INSTANCE.init(hardwareMap);
        Intake.INSTANCE.init(hardwareMap);
        Vision.INSTANCE.setAlliance(AutoSettings.INSTANCE.iAmBlue());

        telemetry.update();
        telemetry.addData(">", "hardware init complete.");
        follower = Constants.createFollower(hardwareMap);
        if (I_AM_BLUE) {
            startPose = new Pose(56, -56, Math.toRadians(-45));
        } else {
            startPose = new Pose(56, 56, Math.toRadians(45));
        }
        buildPaths(I_AM_BLUE);
        follower.setStartingPose(startPose);
        pathState = 0;
        telemetry.addData(">", "atGoal init complete.");
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