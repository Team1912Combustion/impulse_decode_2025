package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.auto.AutoSettings;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {

    final private ElapsedTime teleopTimer = new ElapsedTime();
    final private ElapsedTime blinkTimer = new ElapsedTime();
    boolean endGameWarning = false;

    private static Follower follower;
    private static PathChain toLaunch;

    Pose launchPose = null;
    ElapsedTime autoTimer = new ElapsedTime();
    boolean runAuto = false;

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        AutoSettings.INSTANCE.readAutoConfig();
        Drive.INSTANCE.init(hardwareMap);
        Vision.INSTANCE.init(hardwareMap);
        Vision.INSTANCE.setAlliance(AutoSettings.INSTANCE.iAmBlue());
        Catapult.INSTANCE.init(hardwareMap);
        Lift.INSTANCE.init(hardwareMap);
        Intake.INSTANCE.init(hardwareMap);
        PinPoint.INSTANCE.init(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        launchPose = PinPoint.INSTANCE.getPose();

        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
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
        teleopTimer.reset();
        endGameWarning = false;
        follower.startTeleopDrive();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        follower.update();
        Pose currentPose = PinPoint.INSTANCE.getPose();
        telemetry.addLine(String.format("pose   %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
        telemetry.addLine(String.format("target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));

        if (teleopTimer.seconds() >= 80. & teleopTimer.seconds() <= 90.) {
            if (! endGameWarning) {
                blinkTimer.reset();
                endGameWarning = ! endGameWarning;
            }
            if (Math.round(blinkTimer.seconds()) % 2 == 1) {
                gamepad1.rumble(250);
            }
        }

        // DRIVE
        boolean rbp = gamepad1.rightBumperWasPressed();
        boolean rbr = gamepad1.rightBumperWasReleased();
        if (rbp) {
            runAuto = true;
            follower.setPose(currentPose);
            toLaunch = follower.pathBuilder()
                    .addPath(new BezierLine(currentPose, launchPose))
                    .setLinearHeadingInterpolation(currentPose.getHeading(), launchPose.getHeading(), 0.2)
                    .build();
            follower.followPath(toLaunch);
            autoTimer.reset();
        }
        if (rbr) {
            follower.startTeleopDrive();
            runAuto = false;
        }
        if (runAuto) {
            telemetry.addLine(String.format("...auto pose   %6.1f %6.1f %6.1f", currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
            telemetry.addLine(String.format("...auto target %6.1f %6.1f %6.1f", launchPose.getX(), launchPose.getY(), launchPose.getHeading()));
        } else {
            telemetry.addLine(String.format("...teleOp mode"));
            double drive = -1. * squareInput(gamepad1.left_stick_y);
            double strafe = -1. * squareInput(gamepad1.left_stick_x);
            double turn = -1. * squareInput(gamepad1.right_stick_x);
            follower.setTeleOpDrive(drive, strafe, turn, true);
            // follower.setTeleOpDrive(
            //         -gamepad1.left_stick_y,
            //         -gamepad1.left_stick_x,
            //         -gamepad1.right_stick_x,
            //         true // Robot Centric
            // );
            if (gamepad1.left_bumper) {
                launchPose = PinPoint.INSTANCE.getPose();
            }
        }

        // INTAKE
        boolean intakeInButton = gamepad1.a;
        boolean intakeOutButton = gamepad1.b;
        if (intakeOutButton && intakeInButton) {
            intakeInButton = false;
            intakeOutButton = false;
        }
        if (intakeInButton) {
            Intake.INSTANCE.intakein();
            telemetry.addLine("Intake: In");
        } else if (intakeOutButton) {
            Intake.INSTANCE.intakeout();
            telemetry.addLine("Intake: Out");
        } else {
            Intake.INSTANCE.intakeoff();
            telemetry.addLine("Intake: Off");
        }

        // CATAPULT
        boolean catapultLaunchButton = gamepad2.right_trigger > 0.2;
        boolean catapultLoadButton = gamepad2.right_bumper;
        if (catapultLaunchButton && catapultLoadButton) {
            catapultLaunchButton = false;
        }
        if (catapultLaunchButton) {
            Catapult.INSTANCE.launch();
            telemetry.addLine("Catapult: Launch");
        } else if (catapultLoadButton) {
            Catapult.INSTANCE.load();
            telemetry.addLine("Catapult: Load");
        } else {
            Catapult.INSTANCE.hold();
            telemetry.addLine("Catapult: Hold");
        }
        telemetry.addData("Catapult: position:",Catapult.INSTANCE.getLeftPosition());
        telemetry.addData("Catapult: position:",Catapult.INSTANCE.getRightPosition());

        // LIFT
        boolean liftOutButton = gamepad2.dpad_down;
        boolean liftUpButton = gamepad2.dpad_up;
        if (liftOutButton && liftUpButton) {
            liftOutButton = false;
        }
        if (liftOutButton) {
            Lift.INSTANCE.tip();
            telemetry.addLine("Lift: Tip");
        } else if (liftUpButton) {
            Lift.INSTANCE.stow();
            telemetry.addLine("Lift: Stow");
        } else {
            Lift.INSTANCE.hold();
            telemetry.addLine("Lift: Off");
        }
        telemetry.addData("Lift: position:",Lift.INSTANCE.getPosition());

        //double drive = -1. * squareInput(gamepad1.left_stick_y);
        //double strafe = -1. * squareInput(gamepad1.left_stick_x);
        //double turn = -1. * squareInput(gamepad1.right_stick_x);
        //Drive.INSTANCE.moveRobot(drive, strafe, turn);
        // telemetry.addData("Drive: ","powers: %5.2f / %5.2f / %5.2f",drive,strafe,turn);
        telemetry.update();
    }

    public double squareInput(double stick) {
        return stick*stick*stick;
    }
}
