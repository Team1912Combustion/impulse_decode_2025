package org.firstinspires.ftc.teamcode.utils;

import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.geometry.Vector2d;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

/**
 * This is a classfile representing the kinematics of a mecanum drivetrain
 * and controls their speed. The drive methods {@link #driveRobotCentric(double, double, double)}
 * and {@link #driveFieldCentric(double, double, double, double)} are meant to be put inside
 * of a loop. You can call them in {@code void loop()} in an OpMode and within
 * a {@code while (!isStopRequested() && opModeIsActive())} loop in the
 * {@code runOpMode()} method in LinearOpMode.
 * <p>
 * For the derivation of mecanum kinematics, please watch this video:
 * https://www.youtube.com/watch?v=8rhAkjViHEQ.
 */
public class PyroMecanum extends MecanumDrive {
    private double rightSideMultiplier;

    Motor[] motors;

    /**
     * Sets up the constructor for the mecanum drive.
     * Automatically inverts right side by default
     *
     * @param frontLeft  the front left motor
     * @param frontRight the front right motor
     * @param backLeft   the back left motor
     * @param backRight  the back right motor
     */
    public PyroMecanum(Motor frontLeft, Motor frontRight, Motor backLeft, Motor backRight) {
        this(true, frontLeft, frontRight, backLeft, backRight);
    }

    /**
     * Sets up the constructor for the mecanum drive.
     *
     * @param autoInvert Whether or not to automatically invert the right motors
     * @param frontLeft  the front left motor
     * @param frontRight the front right motor
     * @param backLeft   the back left motor
     * @param backRight  the back right motor
     */
    public PyroMecanum(boolean autoInvert, Motor frontLeft, Motor frontRight, Motor backLeft, Motor backRight) {
        super(autoInvert, frontLeft, frontRight, backLeft, backRight);
    }

    /**
     * Drives the robot from the perspective of the driver. No matter the orientation of the
     * robot, pushing forward on the drive stick will always drive the robot away
     * from the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot, derived from input
     * @param forwardSpeed the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     * @param gyroAngle    the heading of the robot, derived from the gyro
     */
    @Override
    public void driveFieldCentric(double strafeSpeed, double forwardSpeed,
                                  double turnSpeed, double gyroAngle) {
        strafeSpeed = clipRange(strafeSpeed);
        forwardSpeed = clipRange(forwardSpeed);
        turnSpeed = clipRange(turnSpeed);

        Vector2d input = new Vector2d(forwardSpeed, strafeSpeed);
        input = input.rotateBy(-gyroAngle);

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[MotorType.kFrontLeft.value] = input.getX() + input.getY();
        wheelSpeeds[MotorType.kFrontRight.value] = input.getX() - input.getY();
        wheelSpeeds[MotorType.kBackLeft.value] = input.getX() - input.getY();
        wheelSpeeds[MotorType.kBackRight.value] = input.getX() + input.getY();

        normalize(wheelSpeeds, input.magnitude());

        wheelSpeeds[MotorType.kFrontLeft.value] += turnSpeed;
        wheelSpeeds[MotorType.kFrontRight.value] -= turnSpeed;
        wheelSpeeds[MotorType.kBackLeft.value] += turnSpeed;
        wheelSpeeds[MotorType.kBackRight.value] -= turnSpeed;

        normalize(wheelSpeeds);

        driveWithMotorPowers(
                wheelSpeeds[MotorType.kFrontLeft.value],
                wheelSpeeds[MotorType.kFrontRight.value],
                wheelSpeeds[MotorType.kBackLeft.value],
                wheelSpeeds[MotorType.kBackRight.value]
        );
    }

}

