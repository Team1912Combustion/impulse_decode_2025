package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.PinPoint;
import org.firstinspires.ftc.teamcode.utils.Pose2d;

@TeleOp(group = "Test", name = "PinPoint")
public class TestPinPoint extends OpMode {

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        PinPoint.INSTANCE.init(hardwareMap);
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

        Pose2d pose = PinPoint.INSTANCE.getPose2d();
        double x = pose.getX();
        double y = pose.getY();
        double heading = pose.getHeading();

        telemetry.addData("Pose: ","x/y/head: %5.2f / %5.2f / %5.2f",x,y,heading);
        telemetry.update();
    }
}
