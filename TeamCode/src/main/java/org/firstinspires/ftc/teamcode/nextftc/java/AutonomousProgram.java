package org.firstinspires.ftc.teamcode.nextftc.java;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "NextFTC Autonomous Program Java")
public class AutonomousProgram extends NextFTCOpMode {
    public AutonomousProgram() {
        super(Claw.INSTANCE, Lift.INSTANCE);
    }

    public Command firstRoutine() {
        return new SequentialGroup(
                Lift.INSTANCE.toHigh(),
                new ParallelGroup(
                        Lift.INSTANCE.toMiddle(),
                        Claw.INSTANCE.close()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open(),
                        Lift.INSTANCE.toLow()
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        firstRoutine().invoke();
    }
}
