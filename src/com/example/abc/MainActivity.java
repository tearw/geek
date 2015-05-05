package com.example.abc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
	
	private Button myButton = null; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myButton = (Button)findViewById(R.id.button1);
		myButton.setOnClickListener(new MyButtonListener());
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MyButtonListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			TextView text = (TextView)findViewById(R.id.textView1);
			text.setText("fdsfdsfds");
			
			try {
				HttpClient client = new DefaultHttpClient();
				//HttpGet httpGet=new HttpGet("http://jsonplaceholder.typicode.com/posts"); 
				HttpGet httpGet=new HttpGet("http://192.168.11.190:8080/com.wangxi.filecounter/1"); 
				HttpResponse response = client.execute(httpGet);
				InputStream inputStream=response.getEntity().getContent();
				
				StringBuffer buffer = new StringBuffer();
                BufferedReader bufferReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String str = new String("");
                while ((str = bufferReader.readLine()) != null) {
                    buffer.append(str);
                }
                bufferReader.close();
                
                System.out.println(buffer.toString());
                
                JSONTokener jsonParser = new JSONTokener(buffer.toString());

                JSONObject person = (JSONObject) jsonParser.nextValue(); 
                
                /*JSONArray jsonArray = new JSONArray(buffer.toString());
                for (int i = 0; i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    String title = jsonObject.optString("title");
                    int id = jsonObject.optInt("id");
                    int k = 0;
                    k++;
                }*/
                
                String strTitle = person.getString("title"); 
                int id = person.getInt("id");  
                text.setText(strTitle);
                
                int k = 0;
                k++;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
