package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.auto.AutoSettings;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@TeleOp(name = "Teleop")
public class JustDrive extends OpMode {


    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();

        Drive.INSTANCE.init(hardwareMap);

        telemetry.update();
        telemetry.addData(">", "Initialization complete.");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        double drive = -1. * gamepad1.left_stick_y;
        double strafe = -1. * gamepad1.left_stick_x;
        double turn = -1. * gamepad1.right_stick_x;

        telemetry.addData("Manual","Drive %5.2f / %5.2f / %5.2f",drive,strafe,turn);
        telemetry.update();
        Drive.INSTANCE.moveRobot(drive, strafe, turn);
    }
}
