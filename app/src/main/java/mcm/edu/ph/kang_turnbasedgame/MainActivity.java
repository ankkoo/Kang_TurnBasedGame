package mcm.edu.ph.kang_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //I will be adding the skill & arts and further improvements on layouts by today; I am sorry for the delay on submission.

    TextView txtCharName, txtEnemyName, txtCharHp, txtCharMp, txtEnemyHp, txtEnemyMp, txtMyDps, txtEnemyDps, txtCombatLog;
    Button btnNextTurn;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    @Override

    public void onClick(View v) {



        Random randomizer = new Random();
        int charDps = randomizer.nextInt(charMaxDamage - charMinDamage) + charMinDamage;
        int enemyDps = randomizer.nextInt(enemyMaxDamage - enemyMinDamage) + enemyMinDamage;

        switch (v.getId()) {
            case R.id.btnNextTurn:

                if (turnNumber % 2 == 1){

                    enemyHp = enemyHp - charDps;
                    turnNumber++;
                    txtEnemyHp.setText(String.valueOf(enemyHp));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    txtCombatLog.setText(" Character " +charName+" dealt "+charDps+" damage to "+enemyName+" ! ");

                    if (enemyHp < 0) {

                        txtCombatLog.setText("" +charName+ " successfully destroyed "+enemyName+"! But wait... don't you feel kinda off?");
                        charHp = 3000;
                        enemyHp = 5000;
                        turnNumber = 1;
                        btnNextTurn.setText("REPLAY?");

                    }

        }
                else if(turnNumber != 1) {

                    charHp = charHp - enemyDps;
                    turnNumber++;
                    txtCharHp.setText(String.valueOf(charHp));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    txtCombatLog.setText(" Character "+enemyName+" dealt "+enemyDps+" damage to "+charName+" ! ");

                    if (charHp < 0) {

                        txtCombatLog.setText("... " +enemyName+ " mercilessly destroyed "+charName+". Was he crying?");
                        charHp = 3000;
                        enemyHp = 5000;
                        turnNumber = 1;
                        btnNextTurn.setText("REPLAY?");

                    }

                }

                break;
        }


    }


}