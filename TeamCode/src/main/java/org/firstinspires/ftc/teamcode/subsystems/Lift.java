package org.firstinspires.ftc.teamcode.subsystems;
import static com.rowanmcalpin.nextftc.ftc.OpModeData.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.ftc.OpModeData;


public class Lift extends Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift() {  }

    public DcMotor lift_motor;

   private double LIFT_UP_POWER = 1;
   public double LIFT_DOWN_POWER = -0.85;
   public double LIFT_OFF_POWER = 0;
   public double liftPower = LIFT_OFF_POWER;
   public enum LiftMode {UP,DOWN,BRAKE}
    private LiftMode liftMode;



    public void Initialize() {
        lift_motor = hardwareMap.get(DcMotor.class, "lift");
    }
}
