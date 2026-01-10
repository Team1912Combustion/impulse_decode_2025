package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class ActiveOpMode {
    public static final ActiveOpMode INSTANCE = new ActiveOpMode();
    private ActiveOpMode() { }

    public OpMode activeOpMode = null;
    public void init(OpMode opMode) {
        activeOpMode = opMode;
    }
    //public boolean isActive() {
    //    return activeOpMode.opModeIsActive();
   // }
}
