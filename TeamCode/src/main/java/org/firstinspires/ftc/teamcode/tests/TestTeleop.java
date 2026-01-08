package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Odometry;

@TeleOp
public class TestTeleop extends CommandOpMode {

    private GamepadEx joystick;
    private GamepadEx toolOp;

    private Catapult catapult;
    private Intake intake;
    private Lift lift;
    private Drive drive;
    private Odometry odometry;

    @Override
    public void initialize() {
        joystick = new GamepadEx(gamepad1);
        toolOp = new GamepadEx(gamepad2);

        catapult = new Catapult(hardwareMap);
        register(catapult);
        catapult.setDefaultCommand(catapult.hold().perpetually());

        intake = new Intake(hardwareMap);
        register(intake);
        intake.setDefaultCommand(intake.intake_off().perpetually());

        lift = new Lift(hardwareMap);
        register(lift);
        lift.setDefaultCommand(lift.run_hold().perpetually());
        //toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(lift.run_stow());
        //toolOp.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(lift.run_tip());

        odometry = new Odometry(hardwareMap, telemetry);
        drive = new Drive(hardwareMap, odometry, telemetry);
        register(drive);
                drive.setDefaultCommand(new DefaultDrive(drive,
                ()->joystick.getLeftY(),
                ()->joystick.getLeftX(),
                ()->joystick.getRightX(),
                ()->joystick.isDown(GamepadKeys.Button.LEFT_BUMPER)));
        joystick.getGamepadButton(GamepadKeys.Button.B).whileHeld(intake.intake_out());
        joystick.getGamepadButton(GamepadKeys.Button.X).whileHeld(intake.intake_in());

        //joystick.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(catapult.load());
        //joystick.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(catapult.launch());
        joystick.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(lift.run_stow());
        joystick.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(lift.run_tip());
        if (toolOp.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > .5) {
            catapult.launch().schedule();
        }
        if (toolOp.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > .5) {
            catapult.load().schedule();
        }

    }

    @Override
    public void run() {
        super.run();
        if (toolOp.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > .5) {
            catapult.launch().schedule();
        }
        if (toolOp.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > .5) {
            catapult.load().schedule();
        }

    }
}
