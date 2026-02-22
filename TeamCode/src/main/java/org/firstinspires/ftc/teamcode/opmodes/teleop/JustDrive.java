package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.opmodes.auto.AutoSettings;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.utils.Pose2d;
import org.firstinspires.ftc.teamcode.utils.Rotation2d;

@TeleOp(name = "JustDrive")
public class JustDrive extends OpMode {
    private static Follower follower;
    private static PathChain toScore;
    Pose launchPose = null;

    ElapsedTime squaretimer = new ElapsedTime();
    boolean squareup = false;
    double STICK_MIN = 0.05;

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        follower = Constants.createFollower(hardwareMap);

        //Vision.INSTANCE.init(hardwareMap);
        //Vision.INSTANCE.setAlliance(false);
        Drive.INSTANCE.init(hardwareMap);
        PinPoint.INSTANCE.init(hardwareMap);

        launchPose = PinPoint.INSTANCE.getPose();

        telemetry.update();
        telemetry.addData(">", "Initialization complete.");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        Pose currentPose = PinPoint.INSTANCE.getPose();

        boolean rbp = gamepad1.rightBumperWasPressed();
        boolean rbr = gamepad1.rightBumperWasReleased();
        if (rbp) {
            squareup = true;
            //follower.setStartingPose(currentPose);
            follower.setPose(currentPose);
            toScore = follower.pathBuilder()
                    .addPath(new BezierLine(currentPose, launchPose))
                    .setLinearHeadingInterpolation(currentPose.getHeading(), launchPose.getHeading(), 0.2)
                    .build();
            follower.followPath(toScore);
            squaretimer.reset();
        }
        if (rbr) {
            squareup = false;
        }

        telemetry.addLine(String.format("  Pose %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
        telemetry.addLine(String.format("Target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));

        double drive =  Math.abs(gamepad1.left_stick_y);
        double strafe =  Math.abs(gamepad1.left_stick_x);
        double turn =  Math.abs(gamepad1.right_stick_x);

        if (squareup) {
            if (drive > STICK_MIN || strafe > STICK_MIN || turn > STICK_MIN ) {
                squareup = false;
                follower.breakFollowing();
            }
            //runSquareToTarget(squaretimer);
            follower.update();
            telemetry.addLine(String.format("run   Pose %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
            telemetry.addLine(String.format("run Target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));
            //runToLaunch(follower, toScore, squaretimer);
        } else {
            follower.breakFollowing();
            if (gamepad1.left_bumper) {
                //PinPoint.INSTANCE.setPose(72., 72., 0.);
                //launchPose = new Pose(72., 72., 0.);
                launchPose = PinPoint.INSTANCE.getPose();
            }

            /*if (gamepad1.left_bumper) {
                drive = -1. * gamepad1.left_stick_y;
                strafe = -1. * gamepad1.left_stick_x;
                turn = -1. * gamepad1.right_stick_x;
                telemetry.addData("FieldCentric","Drive %5.2f / %5.2f / %5.2f",drive,strafe,turn);
                driveFieldRelative(drive, strafe, turn);
            } else {
            */
                drive = -1. * gamepad1.left_stick_y;
                strafe = -1. * gamepad1.left_stick_x;
                turn = -1. * gamepad1.right_stick_x;
                telemetry.addData("RobotCentric","Drive %5.2f / %5.2f / %5.2f",drive,strafe,turn);
                Drive.INSTANCE.moveRobot(drive, strafe, turn);
            //}
        }
        telemetry.addData("Gamepad 1 Right Bumper Pressed", rbp);
        telemetry.addData("Gamepad 1 Right Bumper Released", rbr);
        telemetry.addData("Gamepad 1 Right Bumper Status", gamepad1.right_bumper);
        telemetry.update();
    }

    private void driveFieldRelative(double forward, double right, double rotate) {

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            PinPoint.INSTANCE.setPose2d(new Pose2d(PinPoint.INSTANCE.getPose2d().getX(),
                    PinPoint.INSTANCE.getPose2d().getY(), new Rotation2d(0.)));
        }

        double botHeading = PinPoint.INSTANCE.getPose2d().getHeading();

        // Rotate the movement direction counter to the bot's rotation
        double newStrafe = right * Math.cos(-botHeading) - forward * Math.sin(botHeading);
        double newForward = right * Math.sin(botHeading) + forward * Math.cos(-botHeading);

        newStrafe = newStrafe * 1.1;  // Counteract imperfect strafing

        Drive.INSTANCE.moveRobot(newForward, newStrafe, rotate);
    }

    public void runSquareToTarget(ElapsedTime timer) {
        final double DESIRED_DISTANCE = 19.2; //  this is how close the camera should get to the target (inches)
        final double DESIRED_BEARING = 12.;
        //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
        //  applied to the drive motors to correct the error.
        //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
        //final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        //final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
        //final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
        final double SPEED_GAIN  =  0.03  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
        final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
        final double MAX_AUTO_SPEED = 0.7;   //  Clip the approach speed to this max value
        final double MAX_AUTO_STRAFE= 0.7;   //  Clip the strafing speed to this max value
        final double MAX_AUTO_TURN  = 0.5;   //  Clip the turn speed to this max value
        int target_id = Vision.INSTANCE.target_id;
        Vision.TargetPose targetPose = Vision.INSTANCE.targetPose;
        // square up first to the camera
        double timeout1 = 1.5;
        if (timer.seconds() < timeout1) {
            targetPose = Vision.INSTANCE.getTargetPose();
            if (targetPose.id > 0) {
                // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
                double  rangeError      = (targetPose.pose.range - DESIRED_DISTANCE);
                double  headingError    = targetPose.pose.bearing;
                double  yawError        = targetPose.pose.yaw;
                // Use the speed and turn "gains" to calculate how we want the robot to move.
                double drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                double turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
                double strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
                Drive.INSTANCE.moveRobot(drive, strafe, turn);
                telemetry.addLine(String.format("\n==== (ID %d) square up", targetPose.id));
                telemetry.addLine(String.format("RYB %6.1f %6.1f %6.1f  (deg)", targetPose.pose.range, targetPose.pose.yaw, targetPose.pose.bearing));
            }
        }
        // then strafe to center
        double timeout2 = 2.5;
        if (timer.seconds() > timeout1 && timer.seconds() < timeout2) {
            targetPose = Vision.INSTANCE.getTargetPose();
            if (targetPose.id > 0) {
                // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
                double  headingError    = (targetPose.pose.bearing - DESIRED_BEARING);
                // Use the speed and turn "gains" to calculate how we want the robot to move.
                double strafe = Range.clip(headingError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
                double drive= 0.;
                double turn= 0.;
                Drive.INSTANCE.moveRobot(drive, strafe, turn);
                telemetry.addLine(String.format("\n==== (ID %d) center", targetPose.id));
                telemetry.addLine(String.format("RYB %6.1f %6.1f %6.1f  (deg)", targetPose.pose.range, targetPose.pose.yaw, targetPose.pose.bearing));
            }
        }
        if (timer.seconds() > timeout2) {
            Drive.INSTANCE.stop();
        }
    }

    public void runToLaunch(Follower follower, PathChain toScore, ElapsedTime timer) {
        Pose currentPose = PinPoint.INSTANCE.getPose();
        follower.followPath(toScore);
        double timeout = 30.;
        if (timer.seconds() < timeout && follower.isBusy()) {
            telemetry.addLine(String.format("run   Pose %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
            telemetry.addLine(String.format("run Target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));
        } else {
            follower.pausePathFollowing();
            Drive.INSTANCE.stop();
        }
    }



}
