package org.firstinspires.ftc.teamcode.subsystems;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

import org.firstinspires.ftc.teamcode.subsystems.Vision;

public class Drive implements Subsystem {

public static final Drive INSTANCE = new Drive();

private Drive() { }

    private MotorEx leftFrontDrive = null;
    private MotorEx rightFrontDrive = null;
    private MotorEx leftBackDrive = null;
    private MotorEx rightBackDrive = null;

    public MecanumDriverControlled driverControlled;

    @Override
    public void initialize() {
        leftFrontDrive  = new MotorEx("left_front");
        leftBackDrive   = new MotorEx("left_back");
        rightFrontDrive = new MotorEx("right_front");
        rightBackDrive  = new MotorEx("right_back");

        leftFrontDrive.reverse();
        leftBackDrive.reverse();

        driverControlled = new MecanumDriverControlled(
                leftFrontDrive,
                rightFrontDrive,
                leftBackDrive,
                rightBackDrive,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                driverControlled.getMode()
        );

    }

    public Command stop() {
        return new InstantCommand(this::setStop);
    }

    public Command squareToTarget() {
        return new InstantCommand(this::runSquareToTarget).
                then(new InstantCommand(this::forward12));
    }

    public void setStop() {
        leftFrontDrive.setPower(0.);
        rightFrontDrive.setPower(0.);
        leftBackDrive.setPower(0.);
        rightBackDrive.setPower(0.);
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
        while (ActiveOpMode.opModeIsActive() && m_timer.getElapsedTimeSeconds() < timeout) {
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
        leftFrontDrive.setPower(frontLeftPower);
        rightFrontDrive.setPower(frontRightPower);
        leftBackDrive.setPower(backLeftPower);
        rightBackDrive.setPower(backRightPower);
    }

}