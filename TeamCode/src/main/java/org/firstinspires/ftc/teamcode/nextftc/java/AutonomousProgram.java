package org.firstinspires.ftc.teamcode.nextftc.java;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
//import org.firstinspires.ftc.teamcode.nextftc.java.Claw;
//import org.firstinspires.ftc.teamcode.nextftc.java.ExampleLift;

@Autonomous(name = "NextFTC Autonomous Program Java")
@Disabled
public class AutonomousProgram extends NextFTCOpMode {
    public AutonomousProgram() {
        addComponents(
                new SubsystemComponent(ExampleLift.INSTANCE, Claw.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
                ExampleLift.INSTANCE.toHigh,
                new ParallelGroup(
                        ExampleLift.INSTANCE.toMiddle,
                        Claw.INSTANCE.close
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open,
                        ExampleLift.INSTANCE.toLow
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
}
