package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BlueGoal")
public class BlueGoal extends RedGoal {

    public BlueGoal() {
        addComponents(/* vararg components */);
    }

    @Override
    public boolean amIBlue() {
        return true;
    }

}
