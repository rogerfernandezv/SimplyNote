package rogerdev.simplynote.br.simplynote.util;

import android.app.Application;

/**
 * Created by rogerfernandez on 10/05/15.
 */
public class GlobalResource extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        new SQLiteFactory(this.getApplicationContext());
    }

}
