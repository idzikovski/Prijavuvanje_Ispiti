package com.example.testt;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/** Klasa za aktivnost vo koja se ispisuva poraka za uspesno 
 * prijaveni ispiti
 * @author Ivan
 *
 */

public class UspesnoPrijaveniActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uspesno_prijaveni);
        
        Intent intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_uspesno_prijaveni, menu);
        return true;
    }
}
