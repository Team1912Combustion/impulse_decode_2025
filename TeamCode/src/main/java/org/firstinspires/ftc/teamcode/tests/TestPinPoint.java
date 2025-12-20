package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import com.seattlesolvers.solverslib.geometry.Pose2d;

@TeleOp
public class TestPinPoint extends CommandOpMode {
    private PinPoint pinpoint = null;

    @Override
    public void initialize() {
        pinpoint = new PinPoint(hardwareMap);
        register(pinpoint);
        pinpoint.setDefaultCommand(pinpoint.run_update().perpetually());
    }

    @Override public void run() {
        schedule(pinpoint.run_update());
        Pose2d pose = pinpoint.getPose2d();
        telemetry.addData("Pose: ","x/y/head: %5.2f / %5.2f / %5.2f",
                pose.getX(), pose.getY(), pose.getHeading());
        telemetry.update();
    }
}
