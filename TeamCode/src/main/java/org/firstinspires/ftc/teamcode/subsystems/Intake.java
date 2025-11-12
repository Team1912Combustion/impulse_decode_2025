package org.firstinspires.ftc.teamcode.subsystems;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    private String motor_name = "intake_motor";
    public MotorEx intake_motor;

    private double INTAKE_IN_POWER = 1.0;
    private double INTAKE_OUT_POWER = -0.5;
    private double INTAKE_OFF_POWER = 0.0;

    public void intakein(){
        intake_motor.setPower(INTAKE_IN_POWER);
    }
    public void intakeout(){
        intake_motor.setPower(INTAKE_OUT_POWER);
    }
    public void intakeoff(){
        intake_motor.setPower(INTAKE_OFF_POWER);
    }

    public Command intake_in(){
        return new InstantCommand(this::intakein).requires(this);
    }
    public Command intake_out(){
        return new InstantCommand(this::intakeout).requires(this);
    }
    public Command intake_off(){
        return new InstantCommand(this::intakeoff).requires(this);
    }

    @Override
    public void initialize() {
        intake_motor = new MotorEx(motor_name).brakeMode();
    }
}
