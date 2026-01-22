
package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import java.io.File;

public class AutoSettings {
    public static final AutoSettings INSTANCE = new AutoSettings();
    private AutoSettings() { }

    private String filename = "AutoConfig.json";
    private AutoConfig myAutoConfig = new AutoConfig();

    public int ROW_COUNT = 1;
    public boolean I_AM_BLUE = false;
    public boolean SHOOT_LAST = false;
    public boolean AT_GOAL = true;
    /*
    public boolean GO_PIXEL = true;
    public boolean GO_BACKDROP = true;
    public boolean GO_PARK = true;
    public boolean BACKSTAGE = false;
    public TargetTurnPosition TURN_POSITION = TargetTurnPosition.PERIMETER;
    public boolean RIGHT_SHIFT = false;
    public boolean LEFT_SHIFT = true;
    */

    public int rowCount() { return ROW_COUNT; }
    public boolean shootLast() { return SHOOT_LAST; }
    public boolean iAmBlue() { return I_AM_BLUE; }
    public boolean atGoal() { return AT_GOAL; }
    /*
    public boolean goBackDrop() { return GO_BACKDROP; }
    public boolean goPark() { return GO_PARK; }
    public boolean iAmBackstage() { return BACKSTAGE; }
    public TargetTurnPosition myTurnPosition() { return TURN_POSITION; }
    public boolean rightShift() { return RIGHT_SHIFT; }
    public boolean leftShift() { return LEFT_SHIFT; }
    */

    public void pushToAutoConfig () {
        myAutoConfig.I_AM_BLUE = I_AM_BLUE;
        myAutoConfig.SHOOT_LAST = SHOOT_LAST;
        myAutoConfig.AT_GOAL = AT_GOAL;
        myAutoConfig.ROW_COUNT = ROW_COUNT;
        /*
        myAutoConfig.GO_BACKDROP = GO_BACKDROP;
        myAutoConfig.GO_PARK = GO_PARK;
        myAutoConfig.BACKSTAGE = BACKSTAGE;
        myAutoConfig.TURN_POSITION = TURN_POSITION;
        myAutoConfig.RIGHT_SHIFT = RIGHT_SHIFT;
        myAutoConfig.LEFT_SHIFT = LEFT_SHIFT;
        */
    }

    public void pullFromAutoConfig () {
        I_AM_BLUE     = myAutoConfig.I_AM_BLUE;
        AT_GOAL       = myAutoConfig.AT_GOAL;
        SHOOT_LAST    = myAutoConfig.SHOOT_LAST;
        ROW_COUNT     = myAutoConfig.ROW_COUNT;
        /*
        GO_BACKDROP   = myAutoConfig.GO_BACKDROP;
        GO_PARK       = myAutoConfig.GO_PARK;
        BACKSTAGE     = myAutoConfig.BACKSTAGE;
        TURN_POSITION = myAutoConfig.TURN_POSITION;
        RIGHT_SHIFT   = myAutoConfig.RIGHT_SHIFT;
        LEFT_SHIFT    = myAutoConfig.LEFT_SHIFT;
         */
    }
    public void copyToAutoConfig(AutoConfig tmpAutoConfig) {
        myAutoConfig.I_AM_BLUE     = tmpAutoConfig.I_AM_BLUE;
        myAutoConfig.SHOOT_LAST    = tmpAutoConfig.SHOOT_LAST;
        myAutoConfig.AT_GOAL       = tmpAutoConfig.AT_GOAL;
        myAutoConfig.ROW_COUNT     = tmpAutoConfig.ROW_COUNT;
        /*
        myAutoConfig.GO_BACKDROP   = tmpAutoConfig.GO_BACKDROP;
        myAutoConfig.GO_PARK       = tmpAutoConfig.GO_PARK;
        myAutoConfig.BACKSTAGE     = tmpAutoConfig.BACKSTAGE;
        myAutoConfig.TURN_POSITION = tmpAutoConfig.TURN_POSITION;
        myAutoConfig.RIGHT_SHIFT   = tmpAutoConfig.RIGHT_SHIFT;
        myAutoConfig.LEFT_SHIFT    = tmpAutoConfig.LEFT_SHIFT;
         */
    }
    public void saveAutoConfig() {
        pushToAutoConfig();
        File file = AppUtil.getInstance().getSettingsFile(filename);
        ReadWriteFile.writeFile(file, myAutoConfig.serialize());
    }
    public void readAutoConfig() {
        File file = AppUtil.getInstance().getSettingsFile(filename);
        try {
            AutoConfig tmpAutoConfig = AutoConfig.deserialize(ReadWriteFile.readFile(file));
            copyToAutoConfig(tmpAutoConfig);
        } catch (Exception e) { // if the read fails, presumably the file is not there to create it
            saveAutoConfig();
        }
        pullFromAutoConfig();
    }
}

class AutoConfig implements Cloneable
{
    public boolean I_AM_BLUE = true;
    public boolean SHOOT_LAST = false;
    public boolean AT_GOAL = true;
    public int ROW_COUNT = 1;
    /*
    public boolean GO_BACKDROP = true;
    public boolean GO_PARK = true;
    public boolean BACKSTAGE = true;
    public TargetTurnPosition TURN_POSITION = TargetTurnPosition.PERIMETER;
    public boolean RIGHT_SHIFT = false;
    public boolean LEFT_SHIFT = true;
    */
    public String serialize() {
        return SimpleGson.getInstance().toJson(this);
    }
    public static AutoConfig deserialize(String data) {
        return SimpleGson.getInstance().fromJson(data, AutoConfig.class);
    }
    public AutoConfig clone()
    {
        try {
            AutoConfig result = (AutoConfig) super.clone();
            return result;
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException("internal error: AutoConfig can't be cloned");
        }
    }
}
