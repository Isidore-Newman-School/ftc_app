/*
 * ██████╗ ██╗   ██╗██╗     ██╗         ██████╗  ██████╗ ████████╗
 * ██╔══██╗██║   ██║██║     ██║         ██╔══██╗██╔═══██╗╚══██╔══╝
 * ██████╔╝██║   ██║██║     ██║         ██████╔╝██║   ██║   ██║
 * ██╔══██╗██║   ██║██║     ██║         ██╔══██╗██║   ██║   ██║
 * ██████╔╝╚██████╔╝███████╗███████╗    ██████╔╝╚██████╔╝   ██║
 * ╚═════╝  ╚═════╝ ╚══════╝╚══════╝    ╚═════╝  ╚═════╝    ╚═╝
 */

//First, some vocab:
//This is the "robot file", which contains your robot's most basic code, INCLUDING
//  a list of motors / sensors attached to your robot.
//  a list of

//this tells your computer that you are writing teamcode.
package org.firstinspires.ftc.teamcode;

//these "import" lines tell the computer where to find all of the commands the file uses.
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class BullBot //TIP: make sure that that name matches your filename.java
{
    //this is where all the motors are declared.
    //they arent ready yet, but we need to let the program know we eventually will need them.
    public DcMotor  lDrive      = null;
    public DcMotor  rDrive      = null;
    public DcMotor  arm         = null;
    public Servo    lClaw       = null;
    public Servo    rClaw       = null;

    //these constants are used to help make calculations in autonomous mode. Please, change them.
    final double    motorRPM          = 600 /*rpm*/; //RPM when motors are at max power.
    final double    wheelDiameter     = 100 /*mm*/;  //diameter of DRIVING wheels
    final double    wheelGap          = 100 /*mm*/;  //distance between the two points where the drive wheels touch the ground.
    final double    wheelCircumfrence = Math.PI * Math.pow(wheelDiameter/2,2);
    final double    wheelSpeed        = motorRPM * 60 * wheelCircumfrence;

    //Tinker with these variables to control how powerful the arm's 'boom' is.
    public static final double MAX_ARM_UP_POWER    =  0.5  ;
    public static final double MAX_ARM_DOWN_POWER  = -0.25 ;
    public static final double ARM_RESTING_POWER   =  0.1  ;
        //how much power is applied when the arm isn't moving;
        //kindof like how a car still moves when you don't hit the gas.
        //if your 'boom' is counterweighted, set this to 0.
        //otherwise, experiment with small numbers (0.5 is 100% power)

    HardwareMap hwMap           = null;
    private ElapsedTime period  = new ElapsedTime();

    public HardwarePushbot(){
        //this is the Constructor function. it isn't used for much, so you can ignore it.
    }

    public void init(HardwareMap ahwMap) {
        //these lines of code are run ONCE when the (INIT) button is pushed

        hwMap  = ahwMap;
            //when init is "called", `ah(ard)w(are)Map` is
            //"passed through". we have to store a copy of it.

        //now, we assign each motor to one of the variables decleared earlier in the file.
        lDrive = initDCMotor("left_drive");
        rDrive = initDCMotor("right_drive");
        arm    = initDCMotor("arm");
        lClaw  = hwMap.get(Servo.class, "left_hand");
        rClaw = hwMap.get(Servo.class, "right_hand");
        lClaw.setPosition(MID_SERVO);
        rClaw.setPosition(MID_SERVO);
    }



    /**
     * Returns a fully initialized DC motor.
     * @param name         What did you call the motor when you typed it in on the robot?
     * @param initialPower [default = 0] on a scale of 0-1, how fast should the motor go when it starts?
     * @param direction    [default = forward] what direction should the motor spin?
     *
     * @return the fully initialized motor.
     *
     * @example:
     * arm = initDCMotor('arm');
     */
    private DcMotor initDCMotor(
        String name,                            //What did you name the motor on the phone?
        double initialPower = 0,                //How fast should it start off?
        DcMotor.Direction direction = FORWARD,  //What way should it spin?
    ){
        DcMotor motor = hwMap.get(DcMotor.class,name);
        motor.setDirection(direction);
        motor.setPower(clampDCMotor(initialPower));
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        return motor;
    }

    //this function stops you from trying to make your motors run faster than they are able to.
    private double clampDCMotor(double x){
        if(x > 1){
            return 1;
        } else if (x < -1){
            return -1;
        } else {
            return x;
        }
    }

    private double clampServo(double x){
        if(x > 0.5){
            return 0.5;
        } else if (x < -0.5) {
            return -0.5
        } else {
            return x
        }
    }
 }
