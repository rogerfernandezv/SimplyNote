package rogerdev.simplynote.br.simplynote.ui;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import rogerdev.simplynote.br.simplynote.R;
import rogerdev.simplynote.br.simplynote.dao.AnotacaoDAO;
import rogerdev.simplynote.br.simplynote.model.Anotacao;


public class AtualizarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);
        Intent intent = getIntent();
        final Anotacao anotacaoAtualizar;

        int idAnotacao = 0;

        try {
            idAnotacao = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
        }
        catch (Exception e){
            idAnotacao = 88880;
        }

        Button btn_atualizar = (Button) findViewById(R.id.btn_atualizar);
        final EditText txtTitulo = (EditText) findViewById(R.id.txtTituloAtualizar);
        final EditText txtTexto = (EditText) findViewById(R.id.txtAnotacaoAtualizar);
        final ImageView imgview = (ImageView) findViewById(R.id.imgNoteAtualizar);

        anotacaoAtualizar = AnotacaoDAO.getInstance().selectAnotacao(idAnotacao);

        txtTitulo.setText(anotacaoAtualizar.getTituloAnotacao());
        txtTexto.setText(anotacaoAtualizar.getTextoAnotacao());



        btn_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anotacaoAtualizar.setTextoAnotacao(txtTexto.getText().toString());
                anotacaoAtualizar.setTituloAnotacao(txtTitulo.getText().toString());
                anotacaoAtualizar.setImg_uri(imgview.getTag().toString());
                AnotacaoDAO.getInstance().atualizarAnotacao(anotacaoAtualizar);
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
