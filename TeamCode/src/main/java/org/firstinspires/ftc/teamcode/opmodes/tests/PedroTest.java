package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;




@Autonomous
public class PedroTest extends LinearOpMode {
    private Follower follower;

    private Intake intake;
    private Catapult catapult;
    private Drive drive;
    private Odometry odometry;


    // Poses
    private final Pose startPose = new Pose(56, 56, Math.toRadians(45));
    private final Pose rowOneStart = new Pose(12, 24, Math.toRadians(90));
    private final Pose rowOneDone = new Pose(12, 48, Math.toRadians(90));
    private final Pose rowTwoStart = new Pose(-12, 24, Math.toRadians(90));
    private final Pose rowTwoDone = new Pose(-12, 48, Math.toRadians(90));
    private final Pose rowThreeStart = new Pose(-36, 24, Math.toRadians(90));
    private final Pose rowThreeDone = new Pose(-36, 48, Math.toRadians(90));
    private final Pose parkPose = new Pose(56, 24, Math.toRadians(-90));

    // Path chains
    private PathChain toRowOne, pickupRowOne, scoreRowOne;
    private PathChain toRowTwo, pickupRowTwo, scoreRowTwo;
    private PathChain toRowThree, pickupRowThree, scoreRowThree;
    private PathChain park;

    public void buildPaths() {
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
                .build();

        scoreRowTwo = follower.pathBuilder()
                .addPath(new BezierLine(rowTwoDone, startPose))
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
    @Override
    public void runOpMode() {
        buildPaths();

}}