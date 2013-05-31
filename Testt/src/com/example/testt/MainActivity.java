package com.example.testt;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import org.ksoap2.SoapEnvelope; 
import org.ksoap2.serialization.PropertyInfo; 
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**    ** Klasa za aktivnosta za logiranje **     */

public class MainActivity extends Activity {

	
	/**   Promenlivi vo koi se cuvaat podatocite
	 *    za web servisot koj se koristi
	 */
	
	private static final String SoapAction="http://tempuri.org/login";
	
	private static final String OperationName="login";
	
	private static final String TargetNamespace="http://tempuri.org/";
	
	private static final String SoapAddress="http://10.0.2.2:20800/SemService/SemService.asmx";

	
	
	/** Override na callback metodata onCreate 
	 * koja moze da se koristi za inicijalizacija na aktivnosta
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Metoda login koja se povikuva koga ke se pritisne
     * kopceto za logiranje. Vo nea se prevzemaat kosrinickoto
     * ime i lozinkata od tekstualnite polinja i se prosleduvaat
     * na web metodata login
     * @param view
     */
    
    
    public void login(View view){
    	
    	/* Prevzemanje na korisnicko ime i lozinka */
    	
    	String username,password;
    	
    	EditText tv=(EditText)findViewById(R.id.txtUsername);
    	
    	username=tv.getText().toString();
    	
    	tv=(EditText)findViewById(R.id.txtPassword);
    	
    	password=tv.getText().toString();
    	
    	TextView edit=(TextView)findViewById(R.id.lblPrikaz); 
    	
    	
    	/*  Kreiranje na baranje od tipot SoapObject od bibliotekata ksoap2 */
    	
    	 SoapObject request = new SoapObject(TargetNamespace, OperationName);
         
    	 
    	 /* Prosleduvanje na parametrite na baranjeto */
    	 
    	 PropertyInfo pi=new PropertyInfo();
         
         pi.setName("username");
         pi.setValue(username);
         pi.setType(String.class);
         request.addProperty(pi);
                
         pi=new PropertyInfo();
         pi.setName("password");
         pi.setValue(password);
         pi.setType(String.class);
         request.addProperty(pi);
         
         
         /*  Kreiranje na pliko i dodeluvanje na baranjeto na toa pliko */
         
         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
         envelope.dotNet=true;
         envelope.setOutputSoapObject(request);
         
         HttpTransportSE httpTransport = new HttpTransportSE(SoapAddress);
         
         try
         {
        	
        	/* Otvaranje na konekcija  */ 
        	 
         	httpTransport.call(SoapAction, envelope);
         	
         	/* Vrakanje na rezultato */
         	
         	Object response=envelope.getResponse();
    	
         	
         	if (response.toString().equals("true"))
         	{
         		
         		/* Startuvanje na nova aktivnost */
         		
         		Intent intent = new Intent(this, IzberiSemestarActivity.class);
         		intent.putExtra("username", username); // prenesuvanje na korisnickoti ime vo novata aktivnost
         		startActivity(intent);
         	}
         	else
         	{
         		edit.setText("Погрешно кор. име или лозинка");
         	}
         	
         }
         catch (Exception ex)
         {
         	edit.setText(ex.toString());
         }
    	
    	
    }
}
