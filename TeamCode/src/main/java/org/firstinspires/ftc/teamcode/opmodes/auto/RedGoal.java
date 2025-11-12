package org.firstinspires.ftc.teamcode.opmodes.auto;


import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.subsystems.Catapult;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.commands.Paths;

@Autonomous(name = "RedGoal")
public class RedGoal extends NextFTCOpMode {

    public RedGoal() {
        addComponents(/* vararg components */);
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
                Catapult.INSTANCE.launch(),
                new Delay(0.5),
                new ParallelGroup(
                        Catapult.INSTANCE.load(),
                        Intake.INSTANCE.intake_in()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Catapult.INSTANCE.hold(),
                        Intake.INSTANCE.intake_off()
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
    @Override public void onInit() { }
    @Override public void onWaitForStart() { }
    @Override public void onUpdate() { }
    @Override public void onStop() { }

    public boolean amIBlue() {
        return false;
    }
}
