package com.eztransition.survey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements TextWatcher  {

	private static final String TAG = "MainActivity";
	private EditText mName;
	private EditText mPhone;
	private EditText mEmail;
	private EditText mComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mName = (EditText) findViewById(R.id.name);
		mPhone = (EditText) findViewById(R.id.phone);
		mEmail = (EditText) findViewById(R.id.email);
		mComment = (EditText) findViewById(R.id.comment);
		
		mComment.addTextChangedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void processForm(View duck) {
		String comment = mComment.getText().toString();
		String email = mEmail.getText().toString();
		String phone = mPhone.getText().toString();
		String name = mName.getText().toString();

		//Simplest way to send som text....
//		Intent i = new Intent(Intent.ACTION_SEND);
//		i.setType("text/plain");
//		i.putExtra(Intent.EXTRA_TEXT, "What a wonderful app!");
		
		//Send an sms message
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setData(Uri.parse("sms:"+ phone));
//		i.putExtra("sms_body", comment);
		
		//send an email
		Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","fake@email.com", null));
		
		i.putExtra(Intent.EXTRA_SUBJECT, "important news");
		
		String message = name + " says .. \n" + comment;
		if(phone.length()>0){
			message = message + "\nPhone:"+phone;
		}
		
		
		i.putExtra(Intent.EXTRA_TEXT, message);
		
		try{
			startActivity(i);
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "cannot send email", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void processFormOld(View duck){
		String comment = mComment.getText().toString();
		String email = mEmail.getText().toString();
		String phone = mPhone.getText().toString();
		String name = mName.getText().toString();
		
		int len = comment.length();
		if (len == 0 ){
			Toast.makeText(this.getApplicationContext(), "Give me comments", Toast.LENGTH_LONG).show();
		}
		
		if (name.equals("fred")){
			Toast.makeText(this.getApplicationContext(), "Hi Fred", Toast.LENGTH_LONG).show();
		}
		 
		int value = Integer.parseInt(phone);
		Log.d(TAG, "phone number: "+ value );
				
		int position = email.indexOf("@");
		if (position == -1) {
			Toast.makeText(this.getApplicationContext(),
					"Invalid email address!", Toast.LENGTH_LONG).show();
			mEmail.requestFocus();
			return;
		}
		String username = email.substring(0, position);
		String thanks = "Thanks " + username + " s!";

		Toast.makeText(this.getApplicationContext(), thanks, Toast.LENGTH_LONG)
				.show();

		duck.setVisibility(View.INVISIBLE);
		Toast.makeText(this.getApplicationContext(), R.string.app_name,
				Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		String comments = s.toString();
		String duck = getString(R.string.duck);
		boolean valid = comments.length() > 0 && comments.toLowerCase().indexOf(duck) == -1; 
		View view = findViewById(R.id.imageButton1);
		boolean isVisible = view.getVisibility() == View.VISIBLE;
		
		if(isVisible == valid){
			return;
		}
		
		Animation anim;
		if(valid){
			view.setVisibility(View.VISIBLE);
			anim = AnimationUtils.makeInAnimation(this, true);
		}else{
			anim = AnimationUtils.makeOutAnimation(this, true);
			view.setVisibility(View.INVISIBLE);
		}
		view.startAnimation(anim);
	}


}
