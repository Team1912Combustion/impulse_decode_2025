package org.firstinspires.ftc.teamcode.subsystems;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake implements Subsystem {

    String motor_name = "intake_motor";

    private static double INTAKE_IN_POWER = 1.0;
    private static double INTAKE_OUT_POWER = -0.7;
    private static double INTAKE_OFF_POWER = 0.;
    private MotorEx intake_motor;

    public Intake(final HardwareMap hardwareMap) {
        intake_motor = new MotorEx(hardwareMap, motor_name);
        intake_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        intake_motor.setInverted(true);
    }

    public void set_in(){
        intake_motor.set(INTAKE_IN_POWER);
    }
    public void set_out(){
        intake_motor.set(INTAKE_OUT_POWER);
    }
    public void set_off(){
        intake_motor.set(INTAKE_OFF_POWER);
    }

    public Command intake_in(){
        return new InstantCommand(this::set_in,this);
    }
    public Command intake_out(){
        return new InstantCommand(this::set_out, this);
    }
    public Command intake_off(){
        return new InstantCommand(this::set_off,this);
    }

}
