package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Catapult implements Subsystem {
    public static final Catapult INSTANCE = new Catapult();
    private Catapult() { }

    // USER CODE
    public MotorEx left_motor;
    public MotorEx right_motor;
    public MotorGroup motors;

    public ControlSystem controller = ControlSystem.builder()
                    .posPid(0.005, 0.0, 0.0)
                    .elevatorFF(0)
                    .build();

    public Command load() {
        return new RunToPosition (controller, 10.).requires(this);
    }

    public Command ready() {
        return new RunToPosition (controller, 20.).requires(this);
    }

    public Command launch() {
        return new RunToPosition (controller, 0.).requires(this);
    }

    public Command hold() {
        return new RunToPosition (controller, motors.getCurrentPosition()).requires(this);
    }

    public double getPosition() {
        return left_motor.getCurrentPosition();
    }

    @Override
    public void initialize() {
        left_motor = new MotorEx("left_catapult").brakeMode().zeroed();
        right_motor = new MotorEx("right_catapult").reversed().brakeMode().zeroed();
        motors = new MotorGroup(left_motor, right_motor);
    }

    @Override
    public void periodic() {
        motors.setPower(controller.calculate(motors.getState()));
    }
}

