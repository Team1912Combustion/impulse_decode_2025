package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;

@TeleOp
public class TestDrive extends CommandOpMode {

    private GamepadEx joystick;
    private Drive drive;
    private Odometry odometry;

    @Override
    public void initialize() {
        joystick = new GamepadEx(gamepad1);
        odometry = new Odometry(hardwareMap, telemetry);
        drive = new Drive(hardwareMap, odometry);
        register(drive);
        drive.setDefaultCommand( new DefaultDrive( drive, joystick) );
    }
}
