package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

import org.firstinspires.ftc.teamcode.nextftc.java.Claw;

public class Drive implements Subsystem {

public static final Drive INSTANCE = new Drive();

private Drive() { }

    private MotorEx leftFrontDrive = null;
    private MotorEx rightFrontDrive = null;
    private MotorEx leftBackDrive = null;
    private MotorEx rightBackDrive = null;

    public MecanumDriverControlled driverControlled;

    @Override
    public void initialize() {
        leftFrontDrive = ActiveOpMode.hardwareMap().get(MotorEx.class, "left_front_drive");
        leftBackDrive = ActiveOpMode.hardwareMap().get(MotorEx.class, "left_back_drive");
        rightFrontDrive = ActiveOpMode.hardwareMap().get(MotorEx.class, "right_back_drive");
        rightBackDrive = ActiveOpMode.hardwareMap().get(MotorEx.class, "right_back_drive");

        leftFrontDrive.reverse();
        leftBackDrive.reverse();

    driverControlled = new MecanumDriverControlled(
        leftFrontDrive,
        rightFrontDrive,
        leftBackDrive,
        rightBackDrive,
        Gamepads.gamepad1().leftStickY().negate(),
        Gamepads.gamepad1().leftStickX(),
        Gamepads.gamepad1().rightStickX(),
        driverControlled.getMode()
        );

    }
}