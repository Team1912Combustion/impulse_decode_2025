package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Odometry;

import com.seattlesolvers.solverslib.command.CommandOpMode;

@TeleOp(group = "Test", name = "TestOdometry")
public class TestOdometry extends CommandOpMode {
    private Odometry odometry = null;

    @Override
    public void initialize() {
        odometry = new Odometry(hardwareMap, telemetry);
        register(odometry);
        odometry.setDefaultCommand(odometry.run_update());
    }

    @Override public void run() {
        schedule(odometry.run_update());
        telemetry.update();
    }
}
