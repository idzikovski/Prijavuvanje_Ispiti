package com.example.testt;

import android.widget.CheckBox;
import android.widget.TextView;

/** Klasa PredmetPlaceHolder vo koja se cuvaat
 * kontrolite za stavkite od kontrolata za biranje na predmeti
 * @author Ivan
 *
 */

public class PredmetPlaceHolder {
	private CheckBox checkBox ;  
    private TextView textView ;  
    public PredmetPlaceHolder() {}  
    public PredmetPlaceHolder( TextView textView, CheckBox checkBox ) {  
      this.checkBox = checkBox ;  
      this.textView = textView ;  
    }  
    public CheckBox getCheckBox() {  
      return checkBox;  
    }  
    public void setCheckBox(CheckBox checkBox) {  
      this.checkBox = checkBox;  
    }  
    public TextView getTextView() {  
      return textView;  
    }  
    public void setTextView(TextView textView) {  
      this.textView = textView;  
    }      
}
