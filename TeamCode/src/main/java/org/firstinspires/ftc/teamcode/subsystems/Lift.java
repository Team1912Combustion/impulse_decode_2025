package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Lift implements Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift() {  }

    public MotorEx lift_motor;
    private String motor_name = "lift";

    public ControlSystem controller =
            ControlSystem.builder().posPid(0.005, 0.0, 0.0).build();

    public Command push() {
        return new RunToPosition(controller, 10.);
    }

    public Command pull() {
        return new RunToPosition(controller, 0.);
    }

    public Command hold() {
        return new RunToPosition (controller, lift_motor.getCurrentPosition()).requires(this);
    }

    @Override
    public void initialize() {
        lift_motor = new MotorEx(motor_name).brakeMode();
    }

    @Override
    public void periodic() {
        lift_motor.setPower(controller.calculate(lift_motor.getState()));
    }


}