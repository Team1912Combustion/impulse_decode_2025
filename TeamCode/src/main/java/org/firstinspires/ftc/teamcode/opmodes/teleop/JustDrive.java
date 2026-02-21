package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.opmodes.auto.AutoSettings;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.utils.Pose2d;
import org.firstinspires.ftc.teamcode.utils.Rotation2d;

@TeleOp(name = "JustDrive")
public class JustDrive extends OpMode {


    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();

        Drive.INSTANCE.init(hardwareMap);
        PinPoint.INSTANCE.init(hardwareMap);

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



        if (gamepad1.left_bumper) {
            double drive = -1. * gamepad1.left_stick_y;
            double strafe = -1. * gamepad1.left_stick_x;
            double turn = -1. * gamepad1.right_stick_x;

            telemetry.addData("FieldCentric","Drive %5.2f / %5.2f / %5.2f",drive,strafe,turn);
            driveFieldRelative(drive, strafe, turn);
        } else {
            double drive = -1. * gamepad1.left_stick_y;
            double strafe = -1. * gamepad1.left_stick_x;
            double turn = -1. * gamepad1.right_stick_x;
            telemetry.addData("RobotCentric","Drive %5.2f / %5.2f / %5.2f",drive,strafe,turn);
            Drive.INSTANCE.moveRobot(drive, strafe, turn);
        }
        telemetry.update();
    }

    private void oldDriveFieldRelative(double forward, double right, double rotate) {
        // First, convert direction being asked to drive to polar coordinates
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);
        // Second, rotate angle by the angle the robot is pointing
        theta = AngleUnit.normalizeRadians(theta -
                PinPoint.INSTANCE.getPose2d().getHeading());
        // Third, convert back to cartesian
        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        // Finally, call the drive method with robot relative forward and right amounts
        Drive.INSTANCE.moveRobot(newForward, newStrafe, rotate);
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
}
