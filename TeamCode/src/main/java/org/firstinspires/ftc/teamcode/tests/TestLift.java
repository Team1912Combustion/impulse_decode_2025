package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp
public class TestLift extends CommandOpMode {

    private GamepadEx toolOp;
    private Lift lift;

    @Override
    public void initialize() {
        toolOp = new GamepadEx(gamepad1);
        lift = new Lift(hardwareMap);
        register(lift);
        lift.setDefaultCommand(lift.run_hold().perpetually());
        toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(lift.run_stow());
        toolOp.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(lift.run_tip());
    }
}
