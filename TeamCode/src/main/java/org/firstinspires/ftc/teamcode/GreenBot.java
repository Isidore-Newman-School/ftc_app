package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.*;

public class GreenBot {
    public  DcMotor            drive_FR     = null;//front right
    public  DcMotor            drive_FL     = null;//front left
    public  DcMotor            drive_BR     = null;//back right
    public  DcMotor            drive_BL     = null;//back left

    public  Servo              ball_arm     = null;
    public  ArrayList<DcMotor> blockSuckers = null;//index starts at 0, odd numbers are reversed
    public  HardwareMap        hwMap        = null;

    private ElapsedTime        period       = new ElapsedTime();
    private int                num_suckers  = 2;

    public GreenBot(int num_suckers){this.num_suckers = num_suckers;}

    public void rotate(float speed)

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        drive_FR  = initDCMotor("drive_FR"); //front right
        drive_FL  = initDCMotor("drive_FL"); //front left
        drive_BR  = initDCMotor("drive_BR"); //back right
        drive_BL  = initDCMotor("drive_BL"); //back left

        ball_arm  = initServo("ball_arm",initialArmPosition);//that weird arm thing

        for (int i = 0;i < num_suckers; i++)
            blockSuckers.add(initDCMotor("sucker_"+i));

    }

    private DcMotor initDCMotor(String name){
        DcMotor motor = hwMap.get(DcMotor.class,name);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(0);
        return motor;
    }

    private Servo initServo(String name,double defaultPosition){
        Servo servo = hwMap.get(Servo.class,name);
        servo.setPosition(defaultPosition);
        return servo;
    }

    private double clampDCMotor(double x){
        if(x > 0.5){
            return 0.5
        } else if (x < -0.5){
            return -0.5
        } else {
            return x
        }
    }
}
