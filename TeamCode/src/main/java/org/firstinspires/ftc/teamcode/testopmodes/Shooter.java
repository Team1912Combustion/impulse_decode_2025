package org.firstinspires.ftc.teamcode.testopmodes;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
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

    public static int MAX_RPM = 5000;
    public static int RPM_INC = 25;
    public static int TARGET_RPM = 100;
    public static double VEL_SCALE = 1.;
    public static int TICKS_PER_REV = 28;
    public static double PID_P = 2.50;
    public static double PID_I = 0.10;
    public static double PID_D = 0.20;
    public static double PID_F = 0.50;
    public static PIDFCoefficients PIDF;

    public MotorEx left_motor;
    public MotorEx right_motor;

    public String left_name = "shooter_left";
    public String right_name = "shooter_right";

    @Override
    public void initialize() {
        left_motor = new MotorEx(left_name);
        right_motor = new MotorEx(right_name);
        right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        PIDF = left_motor.getMotor().getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        TARGET_RPM = 0;
    }

    @Override
    public void periodic() {
    }

    public Command stop() {
        return new InstantCommand(this::allstop);
    }

    public Command updateConstants() {
        return new SequentialGroup(
                new InstantCommand(this::updatePIDF),
                new InstantCommand(this::updateRPM)
        );
    }

    public void allstop() {
        TARGET_RPM = 0;
        left_motor.getMotor().setVelocity(0.);
        right_motor.getMotor().setVelocity(0.);
    }

    public void updateRPM() {
        left_motor.getMotor().setVelocity(rpmToVel(TARGET_RPM * VEL_SCALE));
        right_motor.getMotor().setVelocity(rpmToVel(TARGET_RPM * VEL_SCALE));
    }

    public void updatePIDF() {
        PIDFCoefficients pidfNew = new PIDFCoefficients(PID_P,PID_I,PID_D,PID_F);
        left_motor.getMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfNew);
        right_motor.getMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfNew);
    }

    private double rpmToVel(double rpm) {
        return rpm * (float) TICKS_PER_REV * VEL_SCALE / 60.;
    }
    private double velToRPM(double vel) {
        return vel * 60. / (float) TICKS_PER_REV;
    }

    double getLeftRPM() {
        return velToRPM(left_motor.getVelocity());
    }
    double getRightRPM() {
        return velToRPM(right_motor.getVelocity());
    }
    double getLeftVel() {
        return left_motor.getVelocity();
    }
    double getRightVel() {
        return right_motor.getVelocity();
    }
    double getTargetVel() {
        return rpmToVel(TARGET_RPM);
    }

}

