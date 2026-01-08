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
    private DoubleSupplier fwd;
    private DoubleSupplier str;
    private DoubleSupplier rot;
    private BooleanSupplier field;

    public DefaultDrive(Drive m_drive, DoubleSupplier m_fwd, DoubleSupplier m_str, DoubleSupplier m_rot, BooleanSupplier m_field) {
        drive = m_drive;
        fwd = m_fwd;
        str = m_str;
        rot = m_rot;
        field = m_field;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        if (field.getAsBoolean()) {
            drive.driveField(fwd.getAsDouble(), str.getAsDouble(), rot.getAsDouble());
        } else {
            drive.drive(fwd.getAsDouble(), str.getAsDouble(), rot.getAsDouble());
        }
    }

}