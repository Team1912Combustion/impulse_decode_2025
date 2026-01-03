package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.Range;

import com.pedropathing.util.Timer;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import java.util.function.DoubleSupplier;

public class Drive implements Subsystem {

    public MecanumDrive drive = null;

    private MotorEx left_front = null;
    private MotorEx left_back = null;
    private MotorEx right_front = null;
    private MotorEx right_back = null;
    private Odometry odometry = null;

    public Drive(HardwareMap hardwareMap, Odometry m_odometry) {
        left_front  = new MotorEx(hardwareMap, "left_front");
        left_back   = new MotorEx(hardwareMap, "left_back");
        right_front = new MotorEx(hardwareMap, "right_front");
        right_back  = new MotorEx(hardwareMap, "right_back");

        //left_front.setInverted(true);
        //left_back.setInverted(true);

        left_front.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        left_front.resetEncoder();
        right_front.resetEncoder();
        left_back.resetEncoder();
        right_back.resetEncoder();
        left_front.setRunMode(MotorEx.RunMode.VelocityControl);
        right_front.setRunMode(MotorEx.RunMode.VelocityControl);
        left_back.setRunMode(MotorEx.RunMode.VelocityControl);
        right_back.setRunMode(MotorEx.RunMode.VelocityControl);

        drive = new MecanumDrive(
                left_front,
                right_front,
                left_back,
                right_back
        );

        odometry = m_odometry;
    }

    public Command stop() {
        return new InstantCommand(this::setStop);
    }

    public Command squareToTarget() {
        return new InstantCommand(this::runSquareToTarget).
                andThen(new InstantCommand(this::forward12));
    }

    public void setStop() {
        drive.stop();
    }

    public void runSquareToTarget() {
        final double timeout = 3.;
        final double DESIRED_DISTANCE = 12.0; //  this is how close the camera should get to the target (inches)
        //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
        //  applied to the drive motors to correct the error.
        //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
        final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". e.g. Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  e.g. Ramp up to 37% power at a 25 degree Yaw error.   (0.375 / 25.0)
        final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  e.g. Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
        final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value
        final double MAX_AUTO_STRAFE= 0.5;   //  Clip the strafing speed to this max value
        final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value

        int target_id = Vision.INSTANCE.target_id;
        Timer m_timer = new Timer();
        Vision.TargetPose targetPose = Vision.INSTANCE.targetPose;

        m_timer.resetTimer();;
        while (ActiveOpMode.INSTANCE.isActive() && m_timer.getElapsedTimeSeconds() < timeout) {
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
                moveRobot(drive, strafe, turn);
            }
        }
    }

    public void forward12() {
    }

    public void moveRobot(double x, double y, double yaw) {
        double frontLeftPower    =  x - y - yaw;
        double frontRightPower   =  x + y + yaw;
        double backLeftPower     =  x + y - yaw;
        double backRightPower    =  x - y + yaw;
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));
        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }
        left_front.set(frontLeftPower);
        right_front.set(frontRightPower);
        left_back.set(backLeftPower);
        right_back.set(backRightPower);
    }

    public void drive(double fwd, double str, double rot) {
        drive.driveRobotCentric(str, fwd, rot);
    }
    public void driveField(double fwd, double str, double rot) {
        drive.driveFieldCentric(str, fwd, rot, odometry.getRot());
    }

}