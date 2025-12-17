package org.firstinspires.ftc.teamcode.subsystems;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Catapult implements Subsystem {

    String left_name = "left_catapult";
    String right_name = "right_catapult";

    private static double POWER_TO_LOAD = 1.;
    private static double POWER_TO_HOLD = .2;
    private static double POWER_TO_LAUNCH = -1.;

    // USER CODE
    public MotorEx left_motor;
    public MotorEx right_motor;
    public MotorGroup motors;

    public Catapult(final HardwareMap hardwareMap) {
        left_motor = new MotorEx(hardwareMap,left_name);
        right_motor = new MotorEx(hardwareMap,right_name);
        right_motor.setInverted(true);
        motors = new MotorGroup(left_motor, right_motor);
        motors.resetEncoder();
    }

    public void set_load() {
        motors.set(POWER_TO_LOAD);
    }
    public void set_launch() {
        motors.set(POWER_TO_LAUNCH);
    }
    public void set_hold() {
        motors.set(POWER_TO_HOLD);
    }

    public Command launch() {
        return new InstantCommand (this::set_launch,this);
    }
    public Command hold() {
        return new InstantCommand (this::set_hold,this);
    }
    public Command load() {
        return new InstantCommand (this::set_load,this);
    }

    public double getPosition() {
        return motors.getCurrentPosition();
    }
}
