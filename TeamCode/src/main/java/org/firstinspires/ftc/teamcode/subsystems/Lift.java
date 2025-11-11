package org.firstinspires.ftc.teamcode.subsystems;
import static com.rowanmcalpin.nextftc.ftc.OpModeData.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;


public class Lift extends Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift() {  }

   private double LIFT_UP_POWER = 1;
   public double LIFT_DOWN_POWER = -0.85;
   public double LIFT_OFF_POWER = 0;

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0);

   public MotorEx lift_motor;

    public Command lift() {
        return new RunToPosition(lift_motor, // MOTOR TO MOVE
                500.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

   public void liftup(){
       lift_motor.setPower(LIFT_UP_POWER);
   }
   public void liftdown(){
       lift_motor.setPower(LIFT_DOWN_POWER);
   }
   public void liftoff(){
       lift_motor.setPower(LIFT_OFF_POWER);
   }



   public Command lift_up(){
       return new InstantCommand(this::liftup);
   }
   public Command lift_down(){
       return new InstantCommand(this::liftdown);
   }
   public Command lift_off(){
       return new InstantCommand(this::liftoff);
   }


    public void Initialize() {
        lift_motor = hardwareMap.get(MotorEx.class, "lift");
    }
}