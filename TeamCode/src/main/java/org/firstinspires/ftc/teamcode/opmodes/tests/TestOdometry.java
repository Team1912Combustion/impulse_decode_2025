package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;
import org.firstinspires.ftc.teamcode.utils.Pose2d;

@TeleOp(name = "TestOdometry")
public class TestOdometry extends OpMode {

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        Odometry.INSTANCE.init(true,true);
        telemetry.addData(">", "Initialization complete.");
        telemetry.update();
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

        Odometry.INSTANCE.update();
        Pose2d pose = Odometry.INSTANCE.getPose2d();
        double x = pose.getX();
        double y = pose.getY();
        double heading = pose.getHeading();

        telemetry.addData("Pose: ","x/y/head: %5.2f / %5.2f / %5.2f",x,y,heading);
        telemetry.update();
    }
}
