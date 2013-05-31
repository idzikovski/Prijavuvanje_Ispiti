package com.example.testt;

/** Klasa Item koja se koristi za cuvanje na 
 * podatocite za edna stavka od Kontrolata za biranje na ispit
 * @author Ivan
 *
 */

public class Item {  
    private String ime = "" ;  
    private boolean checked = false ;  
    public Item() {}  
    public Item( String ime ) {  
      this.ime = ime ;  
    }  
    public Item( String ime, boolean checked ) {  
      this.ime = ime ;  
      this.checked = checked ;  
    }  
    public String getIme() {  
      return ime;  
    }  
    public void setName(String ime) {  
      this.ime = ime;  
    }  
    public boolean isChecked() {  
      return checked;  
    }  
    public void setChecked(boolean checked) {  
      this.checked = checked;  
    }  
    public String toString() {  
      return ime ;   
    }  
    public void toggleChecked() {  
      checked = !checked ;  
    }  
    public boolean getChecked()
    {
    	return checked;
    }
  }  