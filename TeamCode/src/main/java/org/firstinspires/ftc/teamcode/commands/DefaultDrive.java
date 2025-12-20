package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class DefaultDrive extends CommandBase {

    private final Drive drive;
    private final GamepadEx joystick;

    public DefaultDrive(Drive m_drive, GamepadEx m_joystick) {
        drive = m_drive;
        joystick = m_joystick;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        if (joystick.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
            drive.driveField(-1.*joystick.getLeftY(), -1.*joystick.getLeftX(), -1.*joystick.getRightX());
        }  else {
            drive.drive(-1.*joystick.getLeftY(), -1.*joystick.getLeftX(), -1.*joystick.getRightX());
        }
    }

}