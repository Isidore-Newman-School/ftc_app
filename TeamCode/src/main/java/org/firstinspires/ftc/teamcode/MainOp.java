package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Beaus' Xbox 360 controls")//this line just 'points out' the class declared on the next line to the compiler

public class MainOp extends OpMode {
    private enum     Movement       {SPIN,STRAFE,ACCELERATE};
    private enum     InputCurve     {LINEAR, PARABOLIC, SIGMOID};
    private double   CurrBumpRamp   = 0.0;
    private double   CurrDpadVRamp  = 0.0;
    private double   CurrDpadHRamp  = 0.0;
    public  GreenBot greenMachine   = new GreenBot(2);

    /***///============= USER CONTROLLER CONFIG ============///**/
    /***  Mappings:
    /***/ private Movement   BumperMovement = Movement.SPIN;
    /***/ private Movement   TriggerMovment = Movement.ACCELERATE;
    /***/ private Movement   DPadVMovement  = Movement.ACCELERATE;
    /***/ private Movement   DPadHMovement  = Movement.STRAFE;
    /***/
    /***  Curves:
    /***/ private double     stick_deadzone = 0.1;
    /***/ private InputCurve triggerCurve   = InputCurve.PARABOLIC;
    /***/ private InputCurve BumperRampUp   = InputCurve.SIGMOID;
    /***/ private InputCurve DpadVRampUp    = InputCurve.PARABOLIC;
    /***/ private InputCurve DpadHRampUp    = InputCurve.PARABOLIC;
    /***/
    /***  Ramp up (for digital buttons): basically how responsive are the controlls
    /***/ private double    bumperRampSPD   = 1;
    /***/ private double    DpadVSPD        = 1;
    /***/ private double    DpadHSPD        = 1;
    /***/
    /***  Speed:
    /***/ private double     maxSpinSpeed    = 1;
    /***/ private double     maxStrafeSpeed  = 1;
    /***/ private double     maxForwardSpeed = 1;
    /***/
    /***///============= USER CONTROLLER CONFIG ============///**/

    @Override
    public void init() {//init buttion
        greenMachine.init(hardwareMap);
        telemetry.addData("Say", "Hello Driver");    //console.log or print equivalent
    }

    @Override
    public void init_loop() {//init button, loop

    }

    @Override
    public void start() {//play button

    }

    @Override
    public void loop() {//play button, loop
        boolean dpad_in_use =
            gamepad1.dpad_up    ||
            gamepad1.dpad_down  ||
            gamepad1.dpad_left  ||
            gamepad1.dpad_right;

        boolean bumpers_in_use =
            gamepad1.left_bumper || gamepad1.right_bumper;

        boolean bumpers_cancel =
            bumpers_in_use &&
            gamepad1.left_bumper == gamepad1.right_bumper;

        if(bumpers_in_use){
            switch (BumperMovement){
                case SPIN:
                    //rotate bot
                    break;
                case STRAFE:
                    // move bot from side to side
                    break;
            }
        } else if(dpad_in_use){
            //move bot in direction
            return
        } else {
            double throttle = gamepad1.right_trigger - gamepad1.left_trigger;
            double turn_val = gamepad1.left_stick_x;
            double left_power = clampDCMotor(throttle + turn_val);
            double right_power = clampDCMotor(throttle - turn_val);

            drive_FR.setPower(right_power);
            drive_BR.setPower(right_power);
            drive_FL.setPower(left_power);
            drive_BL.setPower(left_power);
        }


        //Basic CONTROLS:
        //DPAD:     directional movement
        //Triggers: forward / backward
        //Bumpers:  rotational
        //Buttons:  a= turn on suckers (HOLD), b=slow movement mode, x=toggle position of the block thingy
        //Lanalog:  "car style" turning

       // double left;//gamepad
       // double right;//gamepad
       //
       // left = -gamepad1.left_stick_y;
       // right = -gamepad1.right_stick_y;
       //
       // greenMachine.leftDrive.setPower(left);
       // greenMachine.rightDrive.setPower(right);
       //
       // if (gamepad1.right_bumper)
       //     clawOffset += CLAW_SPEED;
       // else if (gamepad1.left_bumper)
       //     clawOffset -= CLAW_SPEED;
       //
       // clawOffset = Range.clip(clawOffset, -0.5, 0.5);
       // greenMachine.leftClaw.setPosition(greenMachine.MID_SERVO + clawOffset);
       // greenMachine.rightClaw.setPosition(greenMachine.MID_SERVO - clawOffset);
       //
       // if (gamepad1.y)
       //     greenMachine.leftArm.setPower(greenMachine.ARM_UP_POWER);
       // else if (gamepad1.a)
       //     greenMachine.leftArm.setPower(greenMachine.ARM_DOWN_POWER);
       // else
       //     greenMachine.leftArm.setPower(0.0);
       //
       // telemetry.addData("claw",  "Offset = %.2f", clawOffset);
       // telemetry.addData("left",  "%.2f", left);
       // telemetry.addData("right", "%.2f", right);
    }

    @Override
    public void stop() {

    }
}
