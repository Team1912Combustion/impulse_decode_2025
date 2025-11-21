package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.utils.Pose2d;

@TeleOp(group = "Test", name = "Odometry")
public class TestOdometry extends NextFTCOpMode {

    public TestOdometry() {
        addComponents(
                new SubsystemComponent(
                        PinPoint.INSTANCE,
                        Odometry.INSTANCE)
        );
    }

    @Override public void onWaitForStart() { }

    @Override
    public void onInit() {
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
    }

    @Override public void onUpdate() {
        Pose2d pose = Odometry.INSTANCE.getPose2d();
        double x = pose.getX();
        double y = pose.getY();
        double heading = pose.getHeading();

        telemetry.addData("Pose: ","x/y/head: %5.2f / %5.2f / %5.2f",x,y,heading);
        telemetry.update();
    }
}
