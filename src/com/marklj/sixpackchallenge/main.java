package com.marklj.sixpackchallenge;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.Inflater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.image.SmartImageView;
import android.view.ViewGroup;


public class main extends Activity {
	
	
	EditText termField;
	Button button;
	String url = "http://6packchallenge.com/inc/searchJson.php?s=", term;
	TextView results;
	//SmartImageView[] beerImage;
	LayoutInflater inflater;

	
	@Override
	public void onCreate(Bundle savedInstanceState)  
    {  
		
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        termField = (EditText)findViewById(R.id.searchTerm);     
        button = (Button)this.findViewById(R.id.searchButton);
        results = (TextView)findViewById(R.id.results);
        
        //beerImage = (SmartImageView) this.findViewById(R.id.beerImg);  
        button.setOnClickListener(buttonListener);   
        inflater = LayoutInflater.from(main .this);      
    }

	private OnClickListener buttonListener = new OnClickListener() {  
		
        public void onClick(View v)  {    
        	
        	term = termField.getText().toString();
        
        	try {
				term = java.net.URLEncoder.encode(term, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
			String res = "";
        	res += searchJson(url+term);
        	
        	if(!res.equals("NONE")) {
        		
	        	String[] resArr;
	        	resArr = res.split("\n");
	        	results.setText("");
	        	
	        	SmartImageView[] beerImage = new SmartImageView[10];
	        	beerImage[0] = (SmartImageView) findViewById(R.id.beerImg0);
	        	beerImage[1] = (SmartImageView) findViewById(R.id.beerImg1);
	        	beerImage[2] = (SmartImageView) findViewById(R.id.beerImg2);
	        	beerImage[3] = (SmartImageView) findViewById(R.id.beerImg3);
	        	beerImage[4] = (SmartImageView) findViewById(R.id.beerImg4);
	        	beerImage[5] = (SmartImageView) findViewById(R.id.beerImg5);
	        	beerImage[6] = (SmartImageView) findViewById(R.id.beerImg6);
	        	beerImage[7] = (SmartImageView) findViewById(R.id.beerImg7);
	        	beerImage[8] = (SmartImageView) findViewById(R.id.beerImg8);
	        	beerImage[9] = (SmartImageView) findViewById(R.id.beerImg9);
	        	results.setText("Results: " + resArr.length);
	        	
	        	
	        	for(int i=0;i<10;i++) {
	        		beerImage[i].setImageUrl("");
	        	}
	        	for(int i=0;i<resArr.length;i++) {
	        		if(i<10) {
	        			beerImage[i].setImageUrl("http://6packchallenge.com/uploads/" + resArr[i] + ".jpg");	
	        		}else{
	        			break;
	        		}
	        		
	        	}
	        	
        	}
        	else {
        		Toast.makeText(getApplicationContext(), "No Results found", Toast.LENGTH_SHORT).show();
        	}
        }  
          
    }; 
    
    private String searchJson(String searchUrl) {
		try {
			
			
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpGet httpget = new HttpGet(searchUrl);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			byte buffer[] = new byte[1024];
			InputStream is = entity.getContent() ;
			int numBytes = is.read(buffer) ;
			is.close();

			String entityContents = "";	
			if(numBytes>1) {
				entityContents += new String(buffer,0,numBytes);
				return entityContents;
			}
			else {
				return "NONE";
			}
			
		} catch (ClientProtocolException e) {
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
    
}