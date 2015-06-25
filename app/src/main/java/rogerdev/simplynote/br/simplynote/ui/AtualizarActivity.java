package rogerdev.simplynote.br.simplynote.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
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

    String picturePath = "";
    ImageView imgview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);
        Intent intent = getIntent();
        final Anotacao anotacaoAtualizar;

        int idAnotacao;

        try {
            idAnotacao = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
        }
        catch (Exception e){
            idAnotacao = 88880;
        }

        Button btn_atualizar = (Button) findViewById(R.id.btn_atualizar);
        final EditText txtTitulo = (EditText) findViewById(R.id.txtTituloAtualizar);
        final EditText txtTexto = (EditText) findViewById(R.id.txtAnotacaoAtualizar);
        imgview = (ImageView) findViewById(R.id.imgNoteAtualizar);

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        anotacaoAtualizar = AnotacaoDAO.getInstance().selectAnotacao(idAnotacao);

        txtTitulo.setText(anotacaoAtualizar.getTituloAnotacao());
        txtTexto.setText(anotacaoAtualizar.getTextoAnotacao());
        picturePath = anotacaoAtualizar.getImg_uri();

        if(picturePath.length()> 0) {
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            thumbnail = Bitmap.createScaledBitmap(thumbnail, 150, 150, true);
            imgview.setImageBitmap(thumbnail);
        }



        btn_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anotacaoAtualizar.setTextoAnotacao(txtTexto.getText().toString());
                anotacaoAtualizar.setTituloAnotacao(txtTitulo.getText().toString());
                anotacaoAtualizar.setImg_uri(picturePath);

                AnotacaoDAO.getInstance().atualizarAnotacao(anotacaoAtualizar);
                finish();
            }
        });



    }

    private void selectImage(){
        final CharSequence[] options = {"Escolher imagem", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AtualizarActivity.this);
        builder.setTitle("Adicionar imagem");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Escolher imagem")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1)
            {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = Bitmap.createScaledBitmap(thumbnail, 150, 150, true);
                imgview.setImageBitmap(thumbnail);
            }
        }
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
