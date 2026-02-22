package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;

@TeleOp(name = "JD2")
public class JD2 extends OpMode {
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
        follower.startTeleopDrive();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        follower.update();
        Pose currentPose = PinPoint.INSTANCE.getPose();
        telemetry.addLine(String.format("  Pose %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
        telemetry.addLine(String.format("Target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));

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
            follower.startTeleopDrive();
            squareup = false;
        }

        if (squareup) {
            telemetry.addLine(String.format("run   Pose %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
            telemetry.addLine(String.format("run Target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));
        } else {
            telemetry.addLine(String.format("teleOp Mode"));
            follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    true // Robot Centric
            );
            if (gamepad1.left_bumper) {
                //PinPoint.INSTANCE.setPose(72., 72., 0.);
                //launchPose = new Pose(72., 72., 0.);
                launchPose = PinPoint.INSTANCE.getPose();
            }
        }
        telemetry.addData("Gamepad 1 Right Bumper Pressed", rbp);
        telemetry.addData("Gamepad 1 Right Bumper Released", rbr);
        telemetry.addData("Gamepad 1 Right Bumper Status", gamepad1.right_bumper);
        telemetry.update();
    }

}
