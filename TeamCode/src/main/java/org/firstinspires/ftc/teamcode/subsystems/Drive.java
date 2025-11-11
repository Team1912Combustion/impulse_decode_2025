package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.hardware.dr;

import org.firstinspires.ftc.teamcode.nextftc.java.Claw;

public class Drive extends Subsystem {

public static final Drive INSTANCE = new Drive();
private Drive() { }

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    public DriverControlledCommand driverControlled;

    @Override
    public void initialize() {
        leftFrontDrive = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class,"left_front_drive");
        leftBackDrive = OpModeData. INSTANCE.getHardwareMap().get(DcMotor.class, "left_back_drive");
        rightFrontDrive = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class,"right_front_drive");
        rightBackDrive = OpModeData.INSTANCE.getHardwareMap().get(DcMotor.class,"left_back_drive");


        leftFrontDrive.setDirection(DcMotor.Direction. FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    driverControlled = new PedroDriverControlled();
            leftFrontDrive,
            rightFrontDrive,
            leftBackDrive,
            rightBackDrive,
            Gamepads.gamepad1().leftStickY().negate(),
    Gamepads.gamepad1().leftStickX(),
    Gamepads.gamepad1().rightStickX()
);
}