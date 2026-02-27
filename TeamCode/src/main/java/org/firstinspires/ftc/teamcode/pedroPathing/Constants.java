package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(9)
            .forwardZeroPowerAcceleration(-33.)
            .lateralZeroPowerAcceleration(-60.)
            .useSecondaryTranslationalPIDF(false)
            .useSecondaryHeadingPIDF(false)
            .useSecondaryDrivePIDF(false)
            .centripetalScaling(0.0005)
            .translationalPIDFCoefficients(new PIDFCoefficients(
                    0.08, 0, 0.001, 0.001))
            .headingPIDFCoefficients(new PIDFCoefficients(
                    0.55, 0, 0.002, 0.025))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(
                    0.006, 0, 0.001, 0.3, 0.0005));

    public static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName("left_front")
            .leftRearMotorName("left_back")
            .rightFrontMotorName("right_front")
            .rightRearMotorName("right_back")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .useBrakeModeInTeleOp(true)
            .xVelocity(58.9)
            .yVelocity(40.3);

    public static PinpointConstants pinpointConstants =
            new PinpointConstants().hardwareMapName("pinpoint")
                    .forwardPodY(-6.61)
                    .strafePodX(-3.31)
                    .distanceUnit(DistanceUnit.INCH)
                    .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
                    .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
                            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);

    public static ThreeWheelConstants localizerConstants =
            new ThreeWheelConstants()
                    .forwardTicksToInches(.001989436789)
                    .strafeTicksToInches(.001989436789)
                    .turnTicksToInches(.001989436789)
                    .leftPodY(1)
                    .rightPodY(-1)
                    .strafePodX(-2.5)
                    .leftEncoder_HardwareMapName("leftFront")
                    .rightEncoder_HardwareMapName("rightRear")
                    .strafeEncoder_HardwareMapName("rightFront")
                    .leftEncoderDirection(Encoder.REVERSE)
                    .rightEncoderDirection(Encoder.REVERSE)
                    .strafeEncoderDirection(Encoder.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.995,
            100,
            1.5,
            0.5
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(pinpointConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}

class DistroConstants {
    public static FollowerConstants followerConstants = new FollowerConstants();

    public static PathConstraints pathConstraints = new PathConstraints(
            0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .build();
    }
}
