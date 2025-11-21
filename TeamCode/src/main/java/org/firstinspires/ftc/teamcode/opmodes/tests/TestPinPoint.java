package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.core.components.SubsystemComponent;

import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.utils.Pose2d;

@TeleOp(group = "Test", name = "PinPoint")
public class TestPinPoint extends NextFTCOpMode {

    public TestPinPoint() {
        addComponents(
                new SubsystemComponent(
                        PinPoint.INSTANCE)
        );
    }

    @Override public void onWaitForStart() { }

    @Override
    public void onInit() {
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
    }

    @Override public void onUpdate() {
        Pose2d pose = PinPoint.INSTANCE.getPose2d();
        double x = pose.getX();
        double y = pose.getY();
        double heading = pose.getHeading();

        telemetry.addData("Pose: ","x/y/head: %5.2f / %5.2f / %5.2f",x,y,heading);
        telemetry.update();
    }
}
