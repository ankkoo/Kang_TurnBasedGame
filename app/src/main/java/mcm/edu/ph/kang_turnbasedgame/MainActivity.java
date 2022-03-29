package mcm.edu.ph.kang_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //I will be adding the skill & arts and further improvements on layouts by today; I am sorry for the delay on submission.

    TextView txtCharName, txtEnemyName, txtCharHp, txtCharMp, txtEnemyHp, txtEnemyMp, txtMyDps, txtEnemyDps, txtCombatLog;
    Button btnNextTurn;
    ImageButton skill1, skill2, passive1, passive2;

    //Hero Status
    String charName = "Alpha";
    int charHp = 3000;
    int charMp = 2500;
    int charMinDamage = 300;
    int charMaxDamage = 550;

    //Enemy Status
    String enemyName = "Gamma";
    int enemyHp= 5000;
    int enemyMp= 700;
    int enemyMinDamage = 180;
    int enemyMaxDamage = 320;

    int turnNumber = 1;

    int statusCounter = 0;
    int coolDown = 0;
    int coolDown2= 0;

    boolean disabledStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //code for removing action bar & status bar

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


        txtCharName = findViewById(R.id.txtCharName);
        txtEnemyName = findViewById(R.id.txtEnemyName);
        txtCharHp = findViewById(R.id.txtCharHp);
        txtCharMp= findViewById(R.id.txtCharMp);
        txtEnemyHp = findViewById(R.id.txtEnemyHp);
        txtEnemyMp = findViewById(R.id.txtEnemyMp);
        txtMyDps = findViewById(R.id.txtMyDps);
        txtEnemyDps = findViewById(R.id.txtEnemyDps);

        btnNextTurn = findViewById(R.id.btnNextTurn);

        skill1 = findViewById(R.id.btnskill1);
        skill2 = findViewById(R.id.btnskill2);
        passive1 = findViewById(R.id.btnpassive1);
        passive2 = findViewById((R.id.btnpassive2));

        txtCombatLog = findViewById(R.id.txtCombatLog);

        txtCharName.setText(charName);
        txtCharHp.setText(String.valueOf(charHp));
        txtCharMp.setText(String.valueOf(charMp));

        txtEnemyName.setText(enemyName);
        txtEnemyHp.setText(String.valueOf(enemyHp));
        txtEnemyMp.setText(String.valueOf(enemyMp));

        txtMyDps.setText(String.valueOf(charMinDamage)+ " ~ " + String.valueOf(charMaxDamage));
        txtEnemyDps.setText(String.valueOf(enemyMinDamage)+ " ~ " + String.valueOf(enemyMaxDamage));



        //btn onClick Listener
        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);
        skill2.setOnClickListener(this);

    }

    @Override

    public void onClick(View v) {

        Random randomizer = new Random();
        int charDps = randomizer.nextInt(charMaxDamage - charMinDamage) + charMinDamage;
        int enemyDps = randomizer.nextInt(enemyMaxDamage - enemyMinDamage) + enemyMinDamage;

        if (turnNumber %2 != 1){ // if its enemy's turn, disable button

            skill1.setEnabled(false);
            skill2.setEnabled(false);
        }

        else if(turnNumber % 2 == 1) { // if its player's turn, able button

            skill1.setEnabled(true);
            skill2.setEnabled(true);

        }

        if (coolDown > 0) {

            skill1.setEnabled(false);
            skill2.setEnabled(false);
            coolDown--;
        }

        else if(coolDown == 0) {

            skill1.setEnabled(true);
            skill2.setEnabled(true);

        }

        if (coolDown2 > 0) {

            skill1.setEnabled(false);
            skill2.setEnabled(false);
            coolDown--;
        }

        else if(coolDown2 == 0) {

            skill1.setEnabled(true);
            skill2.setEnabled(true);

        }

        switch (v.getId()) {

            case R.id.btnskill1:

                //skill 1 effect = Can deal 700 damage by sacrificing 200 HP. Cool down = 4 turns.

                enemyHp = enemyHp - 700;
                charHp = charHp - 200;
                turnNumber++;
                txtEnemyHp.setText(String.valueOf(enemyHp));
                txtCharHp.setText(String.valueOf(charHp));

                btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                txtCombatLog.setText(" Character " +charName+" summons a group of swords, aiming at "+enemyName+". "+enemyName+" takes 700 damage.");


                if (enemyHp < 0) {

                    txtCombatLog.setText("" +charName+ " successfully destroyed "+enemyName+"! But wait... don't you feel kinda off?");
                    charHp = 3000;
                    enemyHp = 5000;
                    turnNumber = 0;
                    btnNextTurn.setText("REPLAY?");

                }

                coolDown = 4;

                break;

            case R.id.btnskill2:
                // Deals 150 dmg and stuns enemy for 4 turns. Cool down = 12 turns

                enemyHp = enemyHp - 150;
                turnNumber++;
                txtEnemyHp.setText(String.valueOf(enemyHp));
                btnNextTurn.setText("Next Turn("+ String.valueOf(turnNumber)+")");

                disabledStatus = true;
                statusCounter = 4;

                txtCombatLog.setText(""+charName+ " used stun! It dealt "+150+" to "+enemyName+"." +enemyName+ " is stunned for 4 turns.");

                if(enemyHp < 0) {
                    txtCombatLog.setText("" +charName+ " successfully destroyed "+enemyName+"! But wait... don't you feel kinda off?");
                    charHp = 3000;
                    enemyHp = 5000;
                    turnNumber = 0;
                    btnNextTurn.setText("REPLAY?");
                }
                coolDown2 = 12;

                break;


            case R.id.btnNextTurn:

                if (turnNumber % 2 == 1){ //player's turn

                    enemyHp = enemyHp - charDps;
                    turnNumber++;
                    txtEnemyHp.setText(String.valueOf(enemyHp));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    txtCombatLog.setText(" Character " +charName+" dealt "+charDps+" damage to "+enemyName+" ! ");

                    if (enemyHp < 0) {

                        txtCombatLog.setText("" +charName+ " successfully destroyed "+enemyName+"! But wait... don't you feel kinda off?");
                        charHp = 3000;
                        enemyHp = 5000;
                        turnNumber = 0;
                        coolDown = 0;
                        coolDown2 = 0;
                        btnNextTurn.setText("REPLAY?");

                    }

                    if(statusCounter>0) {
                        statusCounter--;
                        if (statusCounter == 0) {
                            disabledStatus = false;
                        }
                    }
                    coolDown--;
                    coolDown2--;

        }
                else if(turnNumber %2 != 1) { //enemy's turn


                    if(disabledStatus == true) {
                        txtCombatLog.setText("" + enemyName + " is still stunned in a sight of fear for " + statusCounter + " turns");
                        statusCounter--;

                        if (statusCounter == 0) {
                            disabledStatus = false;
                        }
                    }


                    else {
                        charHp = (charHp - enemyDps) + 20; // passive 4 = takes 20 less damage everytime enemy attacks.
                    }
                    turnNumber++;
                    txtCharHp.setText(String.valueOf(charHp));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    txtCombatLog.setText(" Character "+enemyName+" dealt "+enemyDps+" damage to "+charName+" ! ");

                    if (charHp < 0) {

                        txtCombatLog.setText("... " +enemyName+ " mercilessly destroyed "+charName+". Was he crying?");
                        charHp = 3000;
                        enemyHp = 5000;
                        turnNumber = 0;
                        coolDown = 0;
                        coolDown2 = 0;
                        btnNextTurn.setText("REPLAY?");

                    }

                    coolDown--;
                    coolDown2--;

                }

                break;
        }


    }


}