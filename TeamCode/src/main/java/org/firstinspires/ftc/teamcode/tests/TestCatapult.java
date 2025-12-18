package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;

@TeleOp
public class TestCatapult extends CommandOpMode {

    private GamepadEx toolOp;
    private Catapult catapult;

    @Override
    public void initialize() {
        toolOp = new GamepadEx(gamepad1);
        catapult = new Catapult(hardwareMap);
        register(catapult);
        catapult.setDefaultCommand(catapult.hold().perpetually());
        toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(catapult.load());
        toolOp.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(catapult.launch());
    }
}
