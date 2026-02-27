

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

public class AtGoal {
    private static Follower follower;
    private static int pathState;
    private static Telemetry telemetry;

    private static Timer pathTimer;

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

    private static PathChain startGate;
    private static PathChain knockGate;

    private static PathChain toLaunch;

    private static ElapsedTime waittimer = new ElapsedTime();

    ArrayList<Boolean> buttonArray = new ArrayList<>();
    int booleanIncrementer = 0;

    private static int rowCount = 0;

    private static boolean hitGate = true;

    public static void buildPaths(boolean I_AM_BLUE) {

        // Poses

        Pose rowOneStart = null;
        Pose rowOneDone = null;
        Pose toGate = null;
        Pose hitGate = null;
        Pose rowTwoStart = null;
        Pose rowTwoDone = null;
        Pose rowThreeStart = null;
        Pose rowThreeDone = null;
        Pose parkPose = null;
        Pose launchPose = null;


        // Poses

        if (I_AM_BLUE) {
            launchPose = new Pose(55.4, 50.7, Math.toRadians(45));
            rowOneStart = new Pose(13.5, 24, Math.toRadians(90));
            toGate = new Pose(13.8, 48,Math.toRadians(0));
            hitGate = new Pose(13.8, 62.3,Math.toRadians(0));
            rowOneDone = new Pose(13.5,55, Math.toRadians(90));
            rowTwoStart = new Pose(-9.3, 24, Math.toRadians(90));
            rowTwoDone = new Pose(-9.3, 58, Math.toRadians(90));
            rowThreeStart = new Pose(-32.7, 24, Math.toRadians(90));
            rowThreeDone = new Pose(-32.7 , 60.5, Math.toRadians(90));
            //rowFourStart = new Pose(-56, 72,Math.toRadians(135));
            // rowFourDone = new Pose(-70, 72,Math.toRadians(135));
            parkPose = new Pose(54, 24, Math.toRadians(45));
        } else {
            launchPose = new Pose(56, -50, Math.toRadians(-45));
            rowOneStart = new Pose(18.2, -24, Math.toRadians(-90));
            toGate = new Pose(9, -48,Math.toRadians(0));
            hitGate = new Pose(9, -57.5,Math.toRadians(0));
            rowOneDone = new Pose(18.2, -58, Math.toRadians(-90));
            rowTwoStart = new Pose(-4, -24, Math.toRadians(-90));
            rowTwoDone = new Pose(-4, -58, Math.toRadians(-90));
            rowThreeStart = new Pose(-27, -24, Math.toRadians(-90));
            rowThreeDone = new Pose(-27, -58, Math.toRadians(-90));
            //rowFourStart = new Pose(-56, -72.,Math.toRadians(-135));
            //rowFourDone = new Pose(-70, -72,Math.toRadians(-135));
            parkPose = new Pose(54, -24, Math.toRadians(-90));
        }

        toLaunch = follower.pathBuilder()
                .addPath(new BezierLine(startPose, launchPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), launchPose.getHeading())
                .build();

        toRowOne = follower.pathBuilder()
                .addPath(new BezierLine(launchPose, rowOneStart))
                .setLinearHeadingInterpolation(launchPose.getHeading(), rowOneStart.getHeading())
                .build();

        pickupRowOne = follower.pathBuilder()
                .addPath(new BezierLine(rowOneStart, rowOneDone))
                .setLinearHeadingInterpolation(rowOneStart.getHeading(), rowOneDone.getHeading())
                .build();

        startGate = follower.pathBuilder()
                .addPath(new BezierLine(rowOneDone, toGate))
                .setLinearHeadingInterpolation(rowOneDone.getHeading(), toGate.getHeading(), 0.3)
                .build();

        knockGate = follower.pathBuilder()
                .addPath(new BezierLine(toGate, hitGate))
                .setLinearHeadingInterpolation(toGate.getHeading(), hitGate.getHeading(), 0.3)
                .build();


        scoreRowOne = follower.pathBuilder()
                .addPath(new BezierLine(hitGate, launchPose))
                .setLinearHeadingInterpolation(hitGate.getHeading(), launchPose.getHeading())
                .build();


        toRowTwo = follower.pathBuilder()
                .addPath(new BezierLine(launchPose, rowTwoStart))
                .setLinearHeadingInterpolation(launchPose.getHeading(), rowTwoStart.getHeading())
                .build();

        pickupRowTwo = follower.pathBuilder()
                .addPath(new BezierLine(rowTwoStart, rowTwoDone))
                .setLinearHeadingInterpolation(rowTwoStart.getHeading(), rowTwoDone.getHeading())
                .setVelocityConstraint(0.5)
                .build();

        scoreRowTwo = follower.pathBuilder()
                .addPath(new BezierCurve(rowTwoDone, rowOneStart, launchPose))
                .setLinearHeadingInterpolation(rowTwoDone.getHeading(), launchPose.getHeading())
                .build();

        toRowThree = follower.pathBuilder()
                .addPath(new BezierLine(launchPose, rowThreeStart))
                .setLinearHeadingInterpolation(launchPose.getHeading(), rowThreeStart.getHeading())
                .build();

        pickupRowThree = follower.pathBuilder()
                .addPath(new BezierLine(rowThreeStart, rowThreeDone))
                .setLinearHeadingInterpolation(rowThreeStart.getHeading(), rowThreeDone.getHeading())
                .build();

        scoreRowThree = follower.pathBuilder()
                .addPath(new BezierCurve(rowThreeDone, rowTwoStart, launchPose))
                .setLinearHeadingInterpolation(rowThreeDone.getHeading(), launchPose.getHeading())
                .build();

        park = follower.pathBuilder()
                .addPath(new BezierLine(launchPose, parkPose))
                .setLinearHeadingInterpolation(launchPose.getHeading(), parkPose.getHeading())
                .build();
/*
        toRowFour = follower.pathBuilder()
                .addPath(new BezierCurve(launchPose, rowThreeStart, rowFourStart))
                .setLinearHeadingInterpolation(launchPose.getHeading(), rowFourStart.getHeading())
                .build();

        pickupRowFour = follower.pathBuilder()
                .addPath(new BezierLine(rowFourStart, rowFourDone))
                .setLinearHeadingInterpolation(rowFourStart.getHeading(), rowFourDone.getHeading())
                .build();

        scoreRowFour = follower.pathBuilder()
                .addPath(new BezierCurve(rowFourDone, rowThreeStart,launchPose))
                .setLinearHeadingInterpolation(rowFourDone.getHeading(), launchPose.getHeading())
                .build();
*/
    }

    public static void autonomousPathUpdate(Follower follower, int pathState) {
        switch (pathState) {

            case 0:
                follower.followPath(toLaunch);
                setPathState(1);
                break;

            // preload
            case 1:
                if (!follower.isBusy()) {
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(200);
                    Catapult.INSTANCE.launch();
                    mywait(100);
                    Catapult.INSTANCE.load();
                    mywait(200);
                    Catapult.INSTANCE.hold();
                    /*
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
                    */

                    if (rowCount > 0) {
                        setPathState(10);
                    } else {
                        setPathState(90);
                    }
                }
                break;

            case 2:
                follower.followPath(toRowOne);
                setPathState(10);
                break;


            // row 1
            case 10:
                follower.followPath(toRowOne);
                setPathState(12);
                break;
            case 12:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakein();
                    setPathState(13);
                }
                break;
            case 13:
                follower.followPath(pickupRowOne);
                setPathState(14);
                break;
            case 14:
                if (!follower.isBusy()) {
                    // turn off intake before driving;
                    Intake.INSTANCE.intakeoff();
                    if (hitGate) {
                        setPathState(15);

                    } else {
                        setPathState(16);

                    }
                }
                break;


            case 15:
                    follower.followPath(startGate);
                    setPathState(151);
                break;
            case 151:   if (!follower.isBusy()) {
                follower.followPath(knockGate);
                setPathState((16));
            }
            break;
            case 16:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    follower.followPath(scoreRowOne);
                    setPathState(17);
                }
                break;
            case 17:
                if (!follower.isBusy()) {
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
                    setPathState(32);
                }
                break;
            case 32:
                follower.followPath(pickupRowThree);
                setPathState(33);
                break;
            case 33:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    Intake.INSTANCE.intakeoff();
                    setPathState(34);
                }
                break;
            case 34:
                if (!follower.isBusy()) {
                    // turn on intake before driving;
                    follower.followPath(scoreRowThree);
                    setPathState(35);
                }
                break;
            case 35:
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
                follower.pausePathFollowing();
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
                            int m_rowCount,
                            boolean m_HIT_GATE) {
        telemetry = m_telemetry;
        rowCount = m_rowCount;
        hitGate = m_HIT_GATE;
        waittimer.reset();
        pathTimer = new Timer();
        pathTimer.resetTimer();


        Drive.INSTANCE.init(hardwareMap);
        Catapult.INSTANCE.init(hardwareMap);
        Intake.INSTANCE.init(hardwareMap);
        Vision.INSTANCE.setAlliance(AutoSettings.INSTANCE.iAmBlue());

        telemetry.update();
        telemetry.addData(">", "hardware init complete.");
        follower = Constants.createFollower(hardwareMap);
        if (I_AM_BLUE) {
            startPose = new Pose(56, 56, Math.toRadians(45));
        } else {
            startPose = new Pose(56, -56, Math.toRadians(-45));
        }
        buildPaths(I_AM_BLUE);
        follower.setStartingPose(startPose);
        pathState = 0;
        setPathState(0);
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

    /** We do not use this because everything should automatically disable **/
    public void stop() {}

}