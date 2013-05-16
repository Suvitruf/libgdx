package suvitruf.classic.bomberman;

import android.content.Intent;
import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	//int stageNumber;
	//private  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.useGL20 = true;
		//int mode = Activity.MODE_PRIVATE;

		//prefs = getSharedPreferences("bomber-pref", mode);

		//Maze m = new Maze();
		/*Intent intent = new Intent(); 
		 intent.setClass(this, MainActivity.class); 
		 startActivity(intent);*/
		//stageNumber = getStagePref();
		initialize(new BomberMan(this), config);
    }
    
    public void goHome(){
		 Intent i = new Intent();
	        i.setAction(Intent.ACTION_MAIN);
	        i.addCategory(Intent.CATEGORY_HOME);
	        this.startActivity(i);
	}
    
	 
}