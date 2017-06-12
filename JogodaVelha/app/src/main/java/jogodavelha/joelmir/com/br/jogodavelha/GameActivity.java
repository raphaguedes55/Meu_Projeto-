package jogodavelha.joelmir.com.br.jogodavelha;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.delay;

public class GameActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button[] botoes;
    TextView texto;
    int jogador;
    int total_jogadas = 0;
    int vitoria_x=0;
    int vitoria_o=0;
    int empate=0;
    int Rodadas=0;
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


      Toast.makeText(getApplicationContext(),"JOGADOR X COMECA O JOGO",Toast.LENGTH_LONG).show();



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
            Snackbar.make(v , "VEZ DO JOGADOR O", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {

            ((Button) v).setText("O");
            jogador = 1;
            Snackbar.make(v , "VEZ DO JOGADOR X", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        mp.start();
        ((Button) v).setEnabled(false);
        verificaGanhador();
    }

    public void verificaGanhador() {
        for (int i = 0; i <= 7; i++) {
            if ((botoes[combinações[i][0]].getText().equals(botoes[combinações[i][1]].getText())) && botoes[combinações[i][1]].getText().equals(botoes[combinações[i][2]].getText()) &&
                    !botoes[combinações[i][0]].getText().equals("")) {
                ganhador("JOGADOR "+botoes[combinações[i][0]].getText(),"PARABENS !");
                zerarGame();
                total_jogadas = 0;
            }
        }
        if (total_jogadas == 9) {
            ganhador("JOGO EMPATADO","NENHUM JOGARDOR VENCEU");
            zerarGame();
        }

    }

    public void zerarGame() {
        for (int i = 1; i < 10; i++) {
            botoes[i].setText("");
            botoes[i].setEnabled(true);
            jogador = 1;
            total_jogadas = 0;

        }
    }
    protected void ganhador(String ganhador,String Resultado) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_vencedor, null);
        dialogBuilder.setView(dialogView);
        vibrar();
        final TextView ganhado = (TextView) dialogView.findViewById(R.id.ganhador);
        final TextView resultado=(TextView)dialogView.findViewById(R.id.resultado);
        final Button aceitar = (Button) dialogView.findViewById(R.id.button_aceitar);
        final Button cancelar = (Button) dialogView.findViewById(R.id.button_cancelar);
        ganhado.setText(ganhador);
        resultado.setText(Resultado);
        final AlertDialog b = dialogBuilder.create();
        aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zerarGame();
                b.dismiss();
                Toast.makeText(getApplicationContext(),"JOGADOR X COMECA O JOGO",Toast.LENGTH_LONG).show();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                b.dismiss();

            }
        });



        b.show();

    }
    public void vibrar(){
        Vibrator rr = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        long milliseconds = 300;//'300' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds);
    }
}