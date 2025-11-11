package org.firstinspires.ftc.teamcode.nextftc.java;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.control.controllers.PIDFController;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.controllable.RunToPosition;


public class Lift implements Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();
    private Lift() { }

    // USER CODE
    public MotorEx motor;

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0);

    public Command resetZero() {
        return new InstantCommand(() -> { motor.resetEncoder(); });
    }

    public String name = "lift_motor";

    public Command toLow() {
        return new RunToPosition(motor, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toMiddle() {
        return new RunToPosition(motor, // MOTOR TO MOVE
                500.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toHigh() {
        return new RunToPosition(motor, // MOTOR TO MOVE
                1200.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }
    
    @Override
    public void initialize() {
        motor = new MotorEx(name);
    }
}
