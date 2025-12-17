package org.firstinspires.ftc.teamcode.subsystems;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift implements Subsystem {


    private MotorEx lift_motor;
    private String motor_name = "lift";

    private static final int STOW_POSITION = 0;
    private static final int TIP_POSITION = 4800;
    private static final double POWER_TO_STOW = -1.;
    private static final double POWER_TO_TIP = +1.;

    public Lift(final HardwareMap hardwareMap) {
        lift_motor = new MotorEx(hardwareMap, motor_name);
        lift_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void stow() {
        lift_motor.setTargetPosition(STOW_POSITION);
        lift_motor.setRunMode(Motor.RunMode.PositionControl);
        lift_motor.set(POWER_TO_STOW);
    }

    public void hold() {
        lift_motor.set(0.);
    }

    public void tip() {
        lift_motor.setTargetPosition(TIP_POSITION);
        lift_motor.setRunMode(Motor.RunMode.PositionControl);
        lift_motor.set(POWER_TO_TIP);
    }

    public Command run_stow() {
        return new InstantCommand(this::stow, this);
    }

    public Command run_tip() {
        return new InstantCommand(this::tip, this);
    }

    public Command run_hold() {
        return new InstantCommand(this::hold, this);
    }

    public double getPosition() {
        return lift_motor.getCurrentPosition();
    }

}