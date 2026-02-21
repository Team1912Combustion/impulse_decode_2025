package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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

        Vision.INSTANCE.init(hardwareMap);
        Vision.INSTANCE.setAlliance(false);
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
        if (gamepad1.right_bumper) {
            runSquareToTarget();
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

    public void runSquareToTarget() {
        final double timeout = 3.;
        final double DESIRED_DISTANCE = 19.2; //  this is how close the camera should get to the target (inches)
        final double DESIRED_STRAFE = 10.0; //  target should be 25 deg to the right of the camera
        final double DESIRED_BEARING = -3.3;

        //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
        //  applied to the drive motors to correct the error.
        //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
        final double SPEED_GAIN  =  0.01  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        final double STRAFE_GAIN =  0.005 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
        final double TURN_GAIN   =  0.005  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
        final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value
        final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value
        final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value

        int target_id = Vision.INSTANCE.target_id;
        ElapsedTime m_timer = new ElapsedTime();
        Vision.TargetPose targetPose = Vision.INSTANCE.targetPose;

        m_timer.reset();
        while (m_timer.seconds() < timeout) {
            targetPose = Vision.INSTANCE.getTargetPose();
            if (targetPose.id > 0) {
                // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
                double  rangeError      = (targetPose.pose.range - DESIRED_DISTANCE);
                double  headingError    = (targetPose.pose.bearing - DESIRED_BEARING);
                double  yawError        = (targetPose.pose.yaw - DESIRED_STRAFE);
                // Use the speed and turn "gains" to calculate how we want the robot to move.
                double drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                double turn   = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
                double strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
                Drive.INSTANCE.moveRobot(drive, strafe, turn);
                telemetry.addLine(String.format("\n==== (ID %d)", targetPose.id));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", targetPose.pose.x, targetPose.pose.y, targetPose.pose.z));
                telemetry.addLine(String.format("RYB %6.1f %6.1f %6.1f  (deg)", targetPose.pose.range, targetPose.pose.yaw, targetPose.pose.bearing));
                telemetry.update();
            }
        }
    }


}
