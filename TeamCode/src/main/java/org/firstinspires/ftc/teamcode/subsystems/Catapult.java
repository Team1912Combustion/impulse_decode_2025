package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import org.firstinspires.ftc.teamcode.nextftc.java.Claw;

public class Catapult {
    public static final Catapult INSTANCE = new Catapult();
    private Catapult() { }

    // USER CODE
    public DcMotor left_motor;
    public DcMotor right_motor;

    private double CATAPULT_UP_MOTOR = -1.0;
    private double CATAPULT_DOWN_MOTOR = 1.0;

    public Command open() {
        return new MotorToPosition(DcMotor, // SERVO TO MOVE
                0.9, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM

    }

    public Command close() {
        return new ServoToPosition(DcMotor, // SERVO TO MOVE
                0.2, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM

    }

    
    public void initialize() {
        left_motor = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class, "left_catapult_motor");
        right_motor = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class, "right_catapult_motor");
        left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
}
}

