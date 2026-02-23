package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;

@TeleOp(name = "TestCatapult")
@Disabled
public class TestCatapult extends OpMode {

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        Catapult.INSTANCE.init(hardwareMap);
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

        boolean catapultLaunchButton = gamepad1.right_bumper;
        boolean catapultLoadButton = gamepad1.right_trigger > 0.2;
        if (catapultLaunchButton && catapultLoadButton) {
            catapultLaunchButton = false;
        }

        if (catapultLaunchButton) {
            Catapult.INSTANCE.launch();
            telemetry.addLine("Catapult: Launch");
        } else if (catapultLoadButton) {
            Catapult.INSTANCE.load();
            telemetry.addLine("Catapult: Load");
        } else {
            Catapult.INSTANCE.hold();
            telemetry.addLine("Catapult: Hold");
        }
        telemetry.addData("Catapult: position:", Catapult.INSTANCE.getLeftPosition());
        telemetry.addData("Catapult: position:", Catapult.INSTANCE.getRightPosition());
        telemetry.addData("Catapult: lpower:", Catapult.INSTANCE.left_motor.getPower());
        telemetry.addData("Catapult: rpower:", Catapult.INSTANCE.right_motor.getPower());

        telemetry.update();
    }
}
