package com.example.caxirolav2;

//test github
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

import com.example.caxirolav2.ShakeDetector.OnShakeListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.oneroseapps.caxirolav2.R;

public class MainActivity extends Activity {

	private ShakeDetector mShakeDetector;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		// Get instance of Vibrator from current Context
		final Vibrator v2 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		//initialize Caxirola sound
		final MediaPlayer mpButtonClick = MediaPlayer.create(this,
				R.raw.cax);
		v2.cancel();
		// ShakeDetector initialization
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {
			@Override
			public void onShake() {
				//Toast.makeText(getApplicationContext(),
					//	"this is my Toast message!!! =)", Toast.LENGTH_LONG)
						//.show();
				//vibrate
				v2.cancel();
				v2.vibrate(1100);
				mpButtonClick.start();
				//mpButtonClick.pause();


			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
		
	}
	
	
}
