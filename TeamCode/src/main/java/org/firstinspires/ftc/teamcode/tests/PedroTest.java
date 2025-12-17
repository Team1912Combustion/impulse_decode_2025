package org.firstinspires.ftc.teamcode.tests;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;
import com.seattlesolvers.solverslib.util.TelemetryData;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;

@Autonomous
public class PedroTest extends CommandOpMode {
    private Follower follower;

    private Intake intake;
    private Catapult catapult;
    private Drive drive;
    private Odometry odometry;

    TelemetryData telemetryData = new TelemetryData(telemetry);

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
                .addPath(new BezierLine( startPose, parkPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), parkPose.getHeading())
                .build();
    }

    @Override
    public void initialize() {
        super.reset();

        // Initialize follower
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();

        Intake intake = new Intake(hardwareMap);
        Catapult catapult = new Catapult(hardwareMap);
        Odometry odometry = new Odometry(hardwareMap, telemetry);
        Drive drive = new Drive(hardwareMap, odometry);

        // Schedule the autonomous sequence
        schedule(
                new RunCommand(() -> follower.update()),
                // Score preload
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second
                catapult.load(),
                new WaitCommand(1000), // Wait 1 second
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second

                // First pickup cycle
                new FollowPathCommand(follower, toRowOne),
                intake.intake_in(),
                new WaitCommand(100), // Wait 1 second
                new FollowPathCommand(follower, pickupRowOne).setGlobalMaxPower(0.5), // Sets globalMaxPower to 50% for all future paths
                new WaitCommand(100), // Wait 1 second
                intake.intake_off(),
                new FollowPathCommand(follower, scoreRowOne).setGlobalMaxPower(0.5), // Sets globalMaxPower to 50% for all future paths
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second
                catapult.load(),
                new WaitCommand(1000), // Wait 1 second
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second

                // Row 2 pickup cycle
                new FollowPathCommand(follower, toRowTwo),
                intake.intake_in(),
                new WaitCommand(100), // Wait 1 second
                new FollowPathCommand(follower, pickupRowTwo).setGlobalMaxPower(0.5), // Sets globalMaxPower to 50% for all future paths
                new WaitCommand(100), // Wait 1 second
                intake.intake_off(),
                new FollowPathCommand(follower, scoreRowTwo).setGlobalMaxPower(0.5), // Sets globalMaxPower to 50% for all future paths
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second
                catapult.load(),
                new WaitCommand(1000), // Wait 1 second
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second

                // First pickup cycle
                new FollowPathCommand(follower, toRowThree),
                intake.intake_in(),
                new WaitCommand(100), // Wait 1 second
                new FollowPathCommand(follower, pickupRowThree).setGlobalMaxPower(0.5), // Sets globalMaxPower to 50% for all future paths
                new WaitCommand(100), // Wait 1 second
                intake.intake_off(),
                new FollowPathCommand(follower, scoreRowThree).setGlobalMaxPower(0.5), // Sets globalMaxPower to 50% for all future paths
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second
                catapult.load(),
                new WaitCommand(1000), // Wait 1 second
                catapult.launch(),
                new WaitCommand(100), // Wait 1 second
                // Park
                new FollowPathCommand(follower, park, false), // park with holdEnd false
                catapult.load()
        );
    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("PP X:Y:H", "%f:%f:%f",
                follower.getPose().getX(),
                follower.getPose().getY(),
                follower.getPose().getHeading());
        telemetryData.update();
    }
}
