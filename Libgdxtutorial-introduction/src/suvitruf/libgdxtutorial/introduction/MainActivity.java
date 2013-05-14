package suvitruf.libgdxtutorial.introduction;



import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.os.Bundle;


public class MainActivity extends AndroidApplication {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true; 
		config.useGL20 = true;
	
		initialize(new MyGame(), config);
    }
    
	 
}