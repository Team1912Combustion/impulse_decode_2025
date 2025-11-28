package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ActiveOpMode;
import org.firstinspires.ftc.teamcode.subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.utils.Pose2d;
import org.firstinspires.ftc.teamcode.utils.Rotation2d;
import org.firstinspires.ftc.teamcode.utils.Transform2d;
import org.firstinspires.ftc.teamcode.utils.Translation2d;

@TeleOp(group = "Test", name = "AutoDrive")
public class TestAutoDrive extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        ActiveOpMode.INSTANCE.init(this);
        AutoDrive.INSTANCE.init(telemetry, hardwareMap);
        Drive.INSTANCE.init(hardwareMap);
        PinPoint.INSTANCE.init(hardwareMap);
        Odometry.INSTANCE.init(true, true);
        Odometry.INSTANCE.set(0.,0.,0.);
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
                Pose2d cur_pose = Odometry.INSTANCE.getPose2d();
                Translation2d trans = new Translation2d(24., 0.);
                Transform2d move = new Transform2d(trans, cur_pose.getRotation());
                Pose2d tgt_pose = cur_pose.plus(move);
                AutoDrive.INSTANCE.driveToPose(0.2,0.8,tgt_pose);
                telemetry.addLine("... forward");
                telemetry.update();
            }
            if (runLeft) {
                telemetry.addLine("Strafe left");
                telemetry.update();
                Pose2d cur_pose = Odometry.INSTANCE.getPose2d();
                Translation2d trans = new Translation2d(0., 24.);
                Transform2d move = new Transform2d(trans, cur_pose.getRotation());
                Pose2d tgt_pose = cur_pose.plus(move);
                AutoDrive.INSTANCE.driveToPose(0.2,0.8,tgt_pose);
                telemetry.addLine("... left");
                telemetry.update();
            }

            if (runBkd) {
                telemetry.addLine("Drive backward");
                telemetry.update();
                Pose2d cur_pose = Odometry.INSTANCE.getPose2d();
                Translation2d trans = new Translation2d(-24., 0.);
                Transform2d move = new Transform2d(trans, cur_pose.getRotation());
                Pose2d tgt_pose = cur_pose.plus(move);
                AutoDrive.INSTANCE.driveToPose(0.2,0.8,tgt_pose);
                telemetry.addLine("... back");
                telemetry.update();
            }
            if (runRight) {
                telemetry.addLine("Strafe right");
                telemetry.update();
                Pose2d cur_pose = Odometry.INSTANCE.getPose2d();
                Translation2d trans = new Translation2d(0., -24.);
                Transform2d move = new Transform2d(trans, cur_pose.getRotation());
                Pose2d tgt_pose = cur_pose.plus(move);
                AutoDrive.INSTANCE.driveToPose(0.2,0.8,tgt_pose);
                telemetry.addLine("... right");
                telemetry.update();
            }
            if (runTarget) {
                telemetry.addLine("Square to target");
                telemetry.update();
                AutoDrive.INSTANCE.runSquareToTarget();
                telemetry.addLine("... target");
                telemetry.update();
            }

            double drive = -1. * gamepad1.left_stick_y;
            double strafe = -1. * gamepad1.left_stick_x;
            double turn = -1. * gamepad1.right_stick_x;
            org.firstinspires.ftc.teamcode.subsystems.Drive.INSTANCE.moveRobot(drive, strafe, turn);

            telemetry.addData("Drive: ","powers: %5.2f / %5.2f / %5.2f",drive,strafe,turn);
            telemetry.update();
            }
        }
    }
}
