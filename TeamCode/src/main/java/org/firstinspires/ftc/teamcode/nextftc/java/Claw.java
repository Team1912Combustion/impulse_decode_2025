package org.firstinspires.ftc.teamcode.nextftc.java;

import com.qualcomm.robotcore.hardware.Servo;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Claw implements Subsystem {
    // BOILERPLATE
    public static final Claw INSTANCE = new Claw();
    private Claw() { }

    // USER CODE
    public ServoEx servo;
    
    public String name = "claw_servo";

    public Command open() {
        return new SetPosition(servo,  0.9);
    }

    public Command close() {
        return new SetPosition(servo, 0.2);
    }

    @Override
    public void initialize() {
        servo = ActiveOpMode.hardwareMap().get(ServoEx.class, name);
    }
}
