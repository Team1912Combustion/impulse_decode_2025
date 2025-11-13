package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "TeleopBlue")
public class RunTeleopBlue extends RunTeleopRed {

    public RunTeleopBlue() {
        addComponents(
            new SubsystemComponent(
                    Intake.INSTANCE,
                    Catapult.INSTANCE,
                    Lift.INSTANCE,
                    Drive.INSTANCE,
                    Vision.INSTANCE)
            );
    }

    @Override
    public boolean amIBlue() {
        return true;
    }
}
