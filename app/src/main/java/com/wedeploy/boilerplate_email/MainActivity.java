package com.wedeploy.boilerplate_email;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.wedeploy.boilerplate_email.databinding.ActivityMainBinding;
import com.wedeploy.sdk.Callback;
import com.wedeploy.sdk.WeDeploy;
import com.wedeploy.sdk.auth.TokenAuth;
import com.wedeploy.sdk.transport.Response;

public class MainActivity extends AppCompatActivity {

	private static final String EMAIL_URL = "http://email.boilerplate-email.wedeploy.io";

	private ActivityMainBinding binding;
	private WeDeploy weDeploy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		weDeploy = new WeDeploy.Builder()
			.auth(new TokenAuth("49488085-1ee3-4c33-888f-0a1e37d2694e"))
			.build();

		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		binding.signInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String from = binding.from.getText().toString();
				String to = binding.to.getText().toString();
				String subject = binding.subject.getText().toString();

				if (!from.isEmpty() && !to.isEmpty() && !subject.isEmpty()) {
					sendEmail(from, to, subject);
				}
				else {
					Log.d(MainActivity.this.getClass().getSimpleName(), "You have to fill all the fields");
				}
			}
		});
	}

	private void sendEmail(String from, String to, String subject) {
		weDeploy.email(EMAIL_URL)
			.from(from)
			.to(to)
			.subject(subject)
			.message(subject)
			.send()
			.execute(new Callback() {
				@Override
				public void onSuccess(Response response) {
					showAlert("Success", "Email sent! Wait a little bit until it arrives :)");
				}

				@Override
				public void onFailure(Exception e) {
					showAlert("Error", "Email sending the email");
				}
			});
	}

	private void showAlert(String title, String message) {
		new AlertDialog.Builder(this)
			.setTitle(title)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, null)
			.show();
	}

}