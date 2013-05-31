package com.example.testt;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.widget.*;

/** Klasa za aktivnosta za izbiranje na semestar */

public class IzberiSemestarActivity extends Activity {

	public static String username;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izberi_semestar);
        
        /* Zemanje na korisnickoto ime od roditelskata aktivnost  */
        
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        
 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_izberi_semestar, menu);
        return true;
    }
    
    
    /**  Metoda koja se izvrsuva po pritisnuvanjeto
     * na kopceto prodolzi. Vo nea se startuva nova aktivnost i vo 
     * istata se prenesuva izbraniot semestar
     * @param view
     */
    
    public void prodolzi(View view)
    {
    	Spinner sem=(Spinner)findViewById(R.id.spinSemestar); // Spinner kontrola (drop down list)
    	
    	Intent intent =new Intent(this, IzberiPredmetiActivity.class);
    	intent.putExtra("username", username);
    	intent.putExtra("semestar", Integer.valueOf(sem.getSelectedItem().toString())); //prenesuvanje na odbraniot semestar
    	startActivity(intent);
    }

}
