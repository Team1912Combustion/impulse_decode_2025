package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(group = "Test", name = "TestIntake")
public class TestIntake extends CommandOpMode {

    private GamepadEx toolOp;
    private Intake intake;

    @Override
    public void initialize() {
        toolOp = new GamepadEx(gamepad1);
        intake = new Intake(hardwareMap);
        register(intake);
        intake.setDefaultCommand(intake.intake_off());
        toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(intake.intake_out());
        toolOp.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(intake.intake_in());
    }
}
