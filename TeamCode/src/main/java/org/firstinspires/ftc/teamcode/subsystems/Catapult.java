package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Catapult {
    public static final Catapult INSTANCE = new Catapult();
    private Catapult() { }

    private DcMotorEx left_motor = null;
    private DcMotorEx right_motor = null;

    private String left_name = "left_catapult";
    private String right_name = "right_catapult";

    private static double POWER_TO_LOAD = -1.;
    private static double POWER_TO_HOLD = -0.2;
    private static double POWER_TO_LAUNCH = 1.;

    public void load() {
        left_motor.setPower(POWER_TO_LOAD);
        right_motor.setPower(POWER_TO_LOAD);
    }

    public void hold() {
        left_motor.setPower(POWER_TO_HOLD);
        right_motor.setPower(POWER_TO_HOLD);
    }

    public void launch() {
        left_motor.setPower(POWER_TO_LAUNCH);
        right_motor.setPower(POWER_TO_LAUNCH);
    }

    public int getLPosition() {
        return left_motor.getCurrentPosition();
    }
    public int getRPosition() {
        return right_motor.getCurrentPosition();
    }
    public double getLPower() {
        return left_motor.getPower();
    }
    public double getRPower() {
        return right_motor.getPower();
    }

    public void init(HardwareMap hmap) {
        left_motor = hmap.get(DcMotorEx.class,left_name);
        right_motor = hmap.get(DcMotorEx.class,right_name);
        left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}

