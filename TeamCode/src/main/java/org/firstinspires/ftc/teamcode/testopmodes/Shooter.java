package org.firstinspires.ftc.teamcode.testopmodes;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldVelocity;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToVelocity;

@Configurable
public class Shooter extends Subsystem {
    @IgnoreConfigurable
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() { }

    public MotorEx left_motor;
    public MotorEx right_motor;
    public MotorGroup motors;
    public static int TARGET_RPM = 100;
    public static double VEL_SCALE = 1.;
    public static int TICKS_PER_REV = 537;
    public static double PID_P = 0.10;
    public static double PID_I = 0.00;
    public static double PID_D = 0.00;

    public PIDFController controller = new PIDFController(PID_P, PID_I, PID_D);

    public String left_name = "shooter_left";
    public String right_name = "shooter_right";

    @Override
    public void initialize() {
        left_motor = new MotorEx(left_name);
        right_motor = new MotorEx(right_name);
        right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motors = new MotorGroup(left_motor,right_motor);
        TARGET_RPM = 0;
    }

    @Override
    public void periodic() {
        controller.setKP(PID_P);
        controller.setKI(PID_I);
        controller.setKD(PID_D);
    }

    public Command stop() {
        return new InstantCommand(() -> { motors.setVelocity(0.); });
    }

    public Command defaultCommand() {
        return this.updateConstants();
    }

    public Command toRPM() {
        return new RunToVelocity(
                left_motor, // MOTOR TO MOVE
                rpmToVel(TARGET_RPM), // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command updateConstants() {
        controller.setKP(PID_P);
        controller.setKI(PID_I);
        controller.setKD(PID_D);
        return new SequentialGroup(
            new RunToVelocity(
                left_motor, // MOTOR TO MOVE
                rpmToVel(TARGET_RPM), // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this),
            new HoldVelocity(
                left_motor,
                controller,
                this)
        );
    }

    private double rpmToVel(double rpm) {
        return rpm / 60. * TICKS_PER_REV * VEL_SCALE;
    }
}

