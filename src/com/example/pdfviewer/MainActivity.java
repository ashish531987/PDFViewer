package com.example.pdfviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity
{
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		if(intent != null && intent.getData() != null)
		{
			// Load FileInfo fragment
			Bundle arguments = new Bundle();
			arguments.putParcelable(PdfRendererBasicFragment.class.getSimpleName(), getIntent().getData());
			PdfRendererBasicFragment fragment = new PdfRendererBasicFragment();
			fragment.setArguments(arguments);
			mFragmentManager = getSupportFragmentManager();
			mFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		}
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
}
