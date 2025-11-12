package org.firstinspires.ftc.teamcode.commands;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;

public class Paths {

    public Command SquareUp() {
        return new SequentialGroup(
                new Delay(1.)
        );
    }

}
