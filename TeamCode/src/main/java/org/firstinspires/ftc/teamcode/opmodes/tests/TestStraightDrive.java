package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ActiveOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.utils.Pose2d;
import org.firstinspires.ftc.teamcode.utils.Transform2d;
import org.firstinspires.ftc.teamcode.utils.Translation2d;

@TeleOp(name = "TestStraightDrive")
public class TestStraightDrive extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        ActiveOpMode.INSTANCE.init(this);
        PinPoint.INSTANCE.init(hardwareMap);
        Drive.INSTANCE.init(hardwareMap);
        Odometry.INSTANCE.init(true, true);
        Odometry.INSTANCE.set(0.,0.,0.);
        AutoDrive.INSTANCE.init(telemetry, hardwareMap);
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
        while (!isStarted()) {

            while (ActiveOpMode.INSTANCE.isActive()) {

            boolean runFwd = gamepad1.dpad_up;
            boolean runLeft = gamepad1.dpad_left;
            boolean runBkd = gamepad1.dpad_down;
            boolean runRight = gamepad1.dpad_right;
            boolean runTarget = gamepad1.left_bumper;

            if (runFwd) {
                telemetry.addLine("Drive forward");
                telemetry.update();
                AutoDrive.INSTANCE.driveStraight(0.2, 0.8, 18.,3.);
                telemetry.addLine("... forward");
                telemetry.update();
            }
            if (runLeft) {
                telemetry.addLine("Strafe left");
                telemetry.update();
                AutoDrive.INSTANCE.strafeStraight(0.2, 0.8, 18.);
                telemetry.addLine("... left");
                telemetry.update();
            }
            if (runBkd) {
                telemetry.addLine("Drive backward");
                telemetry.update();
                AutoDrive.INSTANCE.driveStraight(0.2, 0.8, -18., 3.);
                telemetry.addLine("... back");
                telemetry.update();
            }
            if (runRight) {
                telemetry.addLine("Strafe right");
                telemetry.update();
                AutoDrive.INSTANCE.strafeStraight(0.2, 0.8, -18.);
                telemetry.addLine("... right");
                telemetry.update();
            }
            }
        }
    }
}
