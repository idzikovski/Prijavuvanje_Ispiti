package com.example.testt;

import java.util.ArrayList;
import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.ksoap2.SoapEnvelope; 
import org.ksoap2.serialization.PropertyInfo; 
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/** Klasa za aktivnosta vo koja se izbiraat i prijavuvaat predmetite */

public class IzberiPredmetiActivity extends Activity {

	public static String username;
	public static Integer semestar;
	
	/* Promenlivi vo koi se cuvaat podatocite za web servisot
	 * za metodite getPredmeti i prijaviIspiti
	 */
	
	private static final String SoapAction="http://tempuri.org/getPredmeti";
	
	private static final String SoapActionPrijavi="http://tempuri.org/prijaviIspiti";
		
	private static final String OperationName="getPredmeti";
	
	private static final String OperationNamePrijavi="prijaviIspiti";
		
	private static final String TargetNamespace="http://tempuri.org/";
		
	private static final String SoapAddress="http://10.0.2.2:20800/SemService/SemService.asmx";
	
	
	
	private ListView lvPredmeti;  //ListView kontrola vo koja se prikazuvaat i selektiraat predmeti

    private ArrayAdapter<Item> listAdapter; //Lista za povrzuvanje na stavkite so ListView kontrolata
    
    private ArrayList <Predmet> predmeti=new ArrayList <Predmet>(); //Lista na predmeti
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izberi_predmeti);
        
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        semestar=intent.getIntExtra("semestar", 0);
        
        predmeti=getPredmeti(semestar); //Zimanje na predmeti za daden semestar
 
        
        
        lvPredmeti=(ListView) findViewById(R.id.lvPredmeti);
        
        
        /* Dodavanje na Listener za klik na kontrolata ListView
         * za da moze da se stiklira so klikanje bilo kade na imeto
         * ne samo vo checkboxot
         */
        
        lvPredmeti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	{
        		Item item=listAdapter.getItem(position);
        		item.toggleChecked();
        		PredmetPlaceHolder placeHolder=(PredmetPlaceHolder) view.getTag();
        		placeHolder.getCheckBox().setChecked(item.getChecked());
        	}
		});
        
        
        /* Polnenje na ListView kontrolata so predmetite */
        
        ArrayList<Item> itemList= new ArrayList<Item>();
        Item item;
        for (int i=0;i<predmeti.size();i++)
        {
        	item=new Item(predmeti.get(i).getIme());
        	itemList.add(item);
        }
        
        listAdapter=new PredmetArrayAdapter(this,itemList);  //Kreiranje na objekt od klasata
        //PredmetArrayAdapter koja e izvedena od ArrayAdapter
        //Vo definicijata na ovaa klasa se prosleduva izgledot na edna stavka (Tekstualna labela i checkbox)
        
        lvPredmeti.setAdapter(listAdapter); //Povrzuvanje na ListView kontrolata so listata na predmeti

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_izberi_predmeti, menu);
        return true;
    }
    
    /** Metoda getPredmeti koja go povikuva web metodot getPredmeti i vraka lista od tipot Predmet
     * 
     * @param semsetar
     * @return lista od predmeti
     */
    
    public ArrayList <Predmet> getPredmeti(int semsetar)
    {
    	SoapObject request = new SoapObject(TargetNamespace, OperationName);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("semestar");
        pi.setValue(semestar);
        pi.setType(Integer.class);
        request.addProperty(pi);
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        
        HttpTransportSE httpTransport = new HttpTransportSE(SoapAddress);
        
        try
        {
        	httpTransport.call(SoapAction, envelope);
        	Object response=envelope.getResponse();
        	
        	return parseResponse(response.toString());
        }
        catch (Exception ex)
        {
        	return null;
        }
    }
    
    /** Pomosna metoda parseResponse koja kako parametar go prima odgovorot
     * od web metodata i istiot go parsira i vraka lista od tipot Predmet
     * @param response
     * @return
     */
    
    public ArrayList <Predmet> parseResponse(String response)
    {
    	ArrayList <Predmet> predmeti=new ArrayList <Predmet>();
    	String [] redovi=response.split("anyType"); //Podelba na odgovorod vo redovi
    	String red;
    	
    	
    	//* Parsiranje na redovite od odgovorot (sekoj predmet e vo poseben red) */
    	
    	for (int i=3;i<redovi.length;i++)
    	{
    		Predmet predmet=new Predmet();
    		red=redovi[i];
    		predmet.setIme(red.substring(red.indexOf("Ime=")+4,red.indexOf(";", red.indexOf("Ime="))));
    		predmet.setSifra(red.substring(red.indexOf("Sifra=")+6,red.indexOf(";", red.indexOf("Sifra="))));
    		predmet.setKrediti(Double.valueOf(red.substring(red.indexOf("Krediti=")+8, red.indexOf(";", red.indexOf("Krediti=")))));
    		predmet.setSemestar(Integer.valueOf(red.substring(red.indexOf("Semestar=")+9, red.indexOf(";", red.indexOf("Semestar=")))));
    		
    		predmeti.add(predmet);
    	}
    	
    	return predmeti;
    }
    
    
    /** Metoda prijaviIspiti vo koja za sekoj od 
     * selektiranite predmeti se pravi povik na web metodot prijaviIspiti
     * so korisnickoto ime i imeto na predmetot kako parametri. Se povikuva po 
     * pritisnuvanjeto na kopceto Prijavi
     * @param view
     */
    

    public void prijaviIspiti(View view)
    {
    	
    	TextView tv=(TextView) findViewById(R.id.lblPrikaz);
    	
    	 SoapObject request = new SoapObject(TargetNamespace, OperationNamePrijavi);
         PropertyInfo pi=new PropertyInfo();
         
         
                
         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
         envelope.dotNet=true;
        
         HttpTransportSE httpTransport = new HttpTransportSE(SoapAddress);
         
         Item item;
         Boolean succ=true;
         
         for (int i=0;i<listAdapter.getCount();i++)
         {
        	 item=listAdapter.getItem(i);
        	 if(item.getChecked())
        	 {
        		 pi.setName("username");
                 pi.setValue(username.toString());
                 pi.setType(String.class);
                 request.addProperty(pi);
        		 
                 pi=new PropertyInfo();
                 
        		 pi.setName("imePredmet");
        		 pi.setValue(item.getIme().toString());
        		 pi.setType(String.class);
        		 request.addProperty(pi);
        		 
        		 envelope.setOutputSoapObject(request);
        		 
        		 try
        		 {
        			 httpTransport.call(SoapActionPrijavi, envelope);
        	         Object response=envelope.getResponse();
        	         if (response.toString().equals("false"))
        	         {
        	        	 succ=false;
        	        	 break;
        	         }
        		 }
        		 catch(Exception ex)
        		 {
        			 tv.setText(ex.toString());
        		 }
        	 }
         }
         
         
         
         if (succ==true)
         {
        	 /*Ako prijavuvanjeto e uspesto aktiviraj ja poslednata aktivnost
        	  * so poraka za uspesno prijavuvanje
        	  */
        	 
        	 Intent intent = new Intent(this, UspesnoPrijaveniActivity.class);
        	 startActivity(intent);
         }
       
         
         
    	
    }
}
