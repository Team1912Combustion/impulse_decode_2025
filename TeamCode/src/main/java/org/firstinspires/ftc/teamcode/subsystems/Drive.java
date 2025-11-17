package org.firstinspires.ftc.teamcode.subsystems;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;

/*
 * Positive X is forward
 * Positive Y is strafe left
 * Positive Yaw is counter-clockwise
   double frontLeftPower    =  x - y - yaw;
   double frontRightPower   =  x + y + yaw;
   double backLeftPower     =  x + y - yaw;
   double backRightPower    =  x - y + yaw;
*/

public class Drive implements Subsystem {

public static final Drive INSTANCE = new Drive();

private Drive() { }

    private AutoDrive autoDrive;
    private Telemetry telemetry;
    private MotorEx leftFrontDrive = null;
    private MotorEx rightFrontDrive = null;
    private MotorEx leftBackDrive = null;
    private MotorEx rightBackDrive = null;

    public MecanumDriverControlled driverControlled;

    @Override
    public void initialize() {
        autoDrive = new AutoDrive();
        autoDrive.init();
        leftFrontDrive  = new MotorEx("left_front_drive");
        leftBackDrive   = new MotorEx("left_back_drive");
        rightFrontDrive = new MotorEx("right_back_drive");
        rightBackDrive  = new MotorEx("right_back_drive");

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



    public Command stop() {
        return new InstantCommand(this::setStop);
    }

    public Command squareToTarget() {
        return new InstantCommand(autoDrive::runSquareToTarget);
    }

    public void setStop() {
        leftFrontDrive.setPower(0.);
        rightFrontDrive.setPower(0.);
        leftBackDrive.setPower(0.);
        rightBackDrive.setPower(0.);
    }

    public void moveRobot(double fwd, double strafe, double rot) {
        double denominator = Math.max(Math.abs(strafe) + Math.abs(fwd) + Math.abs(rot), 1.0);
        double frontLeftPower = (fwd - strafe - rot) / denominator;
        double frontRightPower = (fwd + strafe + rot) / denominator;
        double backLeftPower = (fwd + strafe - rot) / denominator;
        double backRightPower = (fwd - strafe + rot) / denominator;
        leftFrontDrive.setPower(frontLeftPower);
        rightFrontDrive.setPower(frontRightPower);
        leftBackDrive.setPower(backLeftPower);
        rightBackDrive.setPower(backRightPower);
    }

}