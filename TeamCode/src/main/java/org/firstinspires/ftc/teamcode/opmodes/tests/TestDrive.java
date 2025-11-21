package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "TestDrive")
public class TestDrive extends NextFTCOpMode {

    public TestDrive() {
        addComponents(
            new SubsystemComponent(
                    Drive.INSTANCE)
            );
    }

    @Override public void onInit() {}
    @Override public void onWaitForStart() { }

    @Override public void onStartButtonPressed() {
        // schedule Drive once
        Drive.INSTANCE.driverControlled.schedule();
    }

    @Override public void onStop() {
        Drive.INSTANCE.stop().schedule();
    }

    @Override public void onUpdate() {
    }
}
