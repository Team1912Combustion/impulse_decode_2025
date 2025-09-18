package org.firstinspires.ftc.teamcode.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "ShooterTuner")
public class ShooterTuner extends NextFTCOpMode {
    public ShooterTuner() {
        super(Shooter.INSTANCE);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onStartButtonPressed() {
        Shooter.INSTANCE.updateConstants().invoke();
    }

    @Override
    public void onUpdate() {
        Shooter.INSTANCE.updateConstants().invoke();
    }

    @Override
    public void onStop() {
        Shooter.INSTANCE.stop().invoke();
    }
}
