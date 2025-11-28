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

    private ElapsedTime timer = null;

    public DcMotor left_motor = null;
    public DcMotor right_motor = null;

    private String left_name = "left_catapult";
    private String right_name = "right_catapult";

    //private static int LOAD_POSITION = -85;
    //private static int LAUNCH_POSITION = 0;
    private static double POWER_TO_LOAD = 1.;
    private static double POWER_TO_READY = .2;
    private static double POWER_TO_LAUNCH = -1.;

    public void load() {
        left_motor.setPower(POWER_TO_LOAD);
        right_motor.setPower(POWER_TO_LOAD);
    }

    public void hold() {
        left_motor.setPower(POWER_TO_READY);
        right_motor.setPower(POWER_TO_READY);
    }

    public void launch() {
        left_motor.setPower(POWER_TO_LAUNCH);
        right_motor.setPower(POWER_TO_LAUNCH);
    }

    public int getLeftPosition() {
        return left_motor.getCurrentPosition();
    }

    public int getRightPosition() {
        return right_motor.getCurrentPosition();
    }

    public void rest() {
        left_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_motor.setPower(0.);
        right_motor.setPower(0.);
    }

    public void init(HardwareMap hmap) {
        left_motor = hmap.get(DcMotor.class,left_name);
        right_motor = hmap.get(DcMotor.class,right_name);
        left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        //left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //left_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //right_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}

