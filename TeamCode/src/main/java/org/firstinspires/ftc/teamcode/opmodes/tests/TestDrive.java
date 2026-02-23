package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

@TeleOp(name = "TestDrive")
@Disabled
public class TestDrive extends OpMode {

    final private ElapsedTime teleopTimer = new ElapsedTime();
    final private ElapsedTime blinkTimer = new ElapsedTime();
    boolean endGameWarning = false;

    Gamepad.RumbleEffect customRumbleEffect;    // Use to build a custom rumble sequence.

    @Override
    public void init() {
        telemetry.addData(">", "Initializing hardware.");
        telemetry.update();
        Drive.INSTANCE.init(hardwareMap);
        customRumbleEffect = new Gamepad.RumbleEffect.Builder()
                .addStep(0.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 300)  //  Pause for 300 mSec
                .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
                .addStep(0.0, 0.0, 250)  //  Pause for 250 mSec
                .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
                .build();
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
        teleopTimer.reset();
        endGameWarning = false;
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        if (teleopTimer.seconds() >= 80. & teleopTimer.seconds() <= 90.) {
            if (! endGameWarning) {
                blinkTimer.reset();
                endGameWarning = ! endGameWarning;
            }
            if (Math.round(blinkTimer.seconds()) % 2 == 1) {
                gamepad1.rumble(250);
            }
        } else {
            gamepad1.rumble(500);
        }

        double drive = -1. * gamepad1.left_stick_y;
        double strafe = -1. * gamepad1.left_stick_x;
        double turn = -1. * gamepad1.right_stick_x;
        Drive.INSTANCE.moveRobot(drive, strafe, turn);

        telemetry.addData("Drive: ","powers: %5.2f / %5.2f / %5.2f",drive,strafe,turn);
        telemetry.update();
    }
}
