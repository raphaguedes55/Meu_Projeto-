package jogodavelha.joelmir.com.br.jogodavelha;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button[] botoes;
    int jogador;
    int total_jogadas = 0;
    MediaPlayer mp = null;
    private int[][] combinações = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9},
            {1, 5, 9},
            {3, 5, 7},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ((TextView) findViewById(R.id.txtJogador)).setText("Jogador: X");

        botoes = new Button[10];

        botoes[1] = (Button) findViewById(R.id.btn1);
        botoes[2] = (Button) findViewById(R.id.btn2);
        botoes[3] = (Button) findViewById(R.id.btn3);
        botoes[4] = (Button) findViewById(R.id.btn4);
        botoes[5] = (Button) findViewById(R.id.btn5);
        botoes[6] = (Button) findViewById(R.id.btn6);
        botoes[7] = (Button) findViewById(R.id.btn7);
        botoes[8] = (Button) findViewById(R.id.btn8);
        botoes[9] = (Button) findViewById(R.id.btn9);

        for (int i = 1; i < 10; i++) {
            botoes[i].setOnClickListener(this);
        }
        mp = MediaPlayer.create(getBaseContext(), R.raw.click);

    }

    @Override
    public void onClick(View v) {
        total_jogadas++;
        if (jogador == 1) {
            ((Button) v).setText("X");
            jogador = 2;
            ((TextView) findViewById(R.id.txtJogador)).setText("Jogador: O");
        } else {
            ((Button) v).setText("O");
            jogador = 1;
            ((TextView) findViewById(R.id.txtJogador)).setText("Jogador: X");
        }

        mp.start();
        ((Button) v).setEnabled(false);
        verificaGanhador();
    }

    public void verificaGanhador() {
        for (int i = 0; i <= 7; i++) {
            if ((botoes[combinações[i][0]].getText().equals(botoes[combinações[i][1]].getText())) && botoes[combinações[i][1]].getText().equals(botoes[combinações[i][2]].getText()) &&
                    !botoes[combinações[i][0]].getText().equals("")) {

                Toast.makeText(getBaseContext(), "Ganhou o Jogador!" + botoes[combinações[i][0]].getText(), Toast.LENGTH_LONG).show();
                zerarGame();
                total_jogadas = 0;
            }
        }
        if (total_jogadas == 9) {
            Toast.makeText(getBaseContext(), "Empate!", Toast.LENGTH_LONG).show();
            zerarGame();
        }

    }

    public void zerarGame() {
        for (int i = 1; i < 10; i++) {
            botoes[i].setText("");
            botoes[i].setEnabled(true);
            ((TextView) findViewById(R.id.txtJogador)).setText("Jogador: X");
            jogador = 1;
            total_jogadas = 0;
        }
    }
}