package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ActiveOpMode {
    public static final ActiveOpMode INSTANCE = new ActiveOpMode();

    public LinearOpMode activeOpMode = null;
    public static boolean I_AM_BLUE = false;

    private ActiveOpMode() { };

    private void init(LinearOpMode opMode, boolean i_am_blue) {
        activeOpMode = opMode;
        I_AM_BLUE = i_am_blue;
    }

    public boolean i_am_blue() {
        return I_AM_BLUE;
    }
    public boolean isActive() {
        return activeOpMode.opModeIsActive();
    }
}
