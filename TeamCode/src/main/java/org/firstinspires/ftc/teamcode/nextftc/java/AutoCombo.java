package org.firstinspires.ftc.teamcode.nextftc.java;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathPoint;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierCurve;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

@Autonomous(name = "AutoCombo")
public class AutoCombo extends NextFTCOpMode {
    public static double RADIUS = 10;
    private Follower follower;
    private PathChain circle;
    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    public AutoCombo() {
        super(Claw.INSTANCE);
    }

    public Command firstRoutine() {
        return new SequentialGroup(
                Claw.INSTANCE.open(),
                new Delay(0.5),
                Claw.INSTANCE.close(),
                new Delay(0.5),
                Claw.INSTANCE.open(),
                new Delay(0.5),
                runCircle(),
                Claw.INSTANCE.close(),
                new Delay(0.5),
                Claw.INSTANCE.open(),
                new Delay(0.5),
                Claw.INSTANCE.close()
        );
    }

    @Override
    public void onStartButtonPressed() {
        firstRoutine().invoke();
    }

    public Command runCircle() {
        return new InstantCommand(()->follower.followPath(circle));
    }

    @Override
    public void onInit() {
        follower = createFollower(hardwareMap);

        circle = follower.pathBuilder()
                    .addPath(new BezierCurve(new Pose(0, 0), new Pose(RADIUS, 0), new Pose(RADIUS, RADIUS)))
                    .setHeadingInterpolation(HeadingInterpolator.facingPoint(0, RADIUS))
                    .addPath(new BezierCurve(new Pose(RADIUS, RADIUS), new Pose(RADIUS, 2 * RADIUS), new Pose(0, 2 * RADIUS)))
                    .setHeadingInterpolation(HeadingInterpolator.facingPoint(0, RADIUS))
                    .addPath(new BezierCurve(new Pose(0, 2 * RADIUS), new Pose(-RADIUS, 2 * RADIUS), new Pose(-RADIUS, RADIUS)))
                    .setHeadingInterpolation(HeadingInterpolator.facingPoint(0, RADIUS))
                    .addPath(new BezierCurve(new Pose(-RADIUS, RADIUS), new Pose(-RADIUS, 0), new Pose(0, 0)))
                    .setHeadingInterpolation(HeadingInterpolator.facingPoint(0, RADIUS))
                    .build();
    }
}
