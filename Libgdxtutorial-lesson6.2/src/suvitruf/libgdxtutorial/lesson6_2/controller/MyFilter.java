package suvitruf.libgdxtutorial.lesson6_2.controller;

import suvitruf.libgdxtutorial.lesson6_2.model.MyWorld;
import android.util.Log;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class MyFilter implements ContactFilter{
	
	
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		
		Filter filterA = fixtureA.getFilterData();

		Filter filterB = fixtureB.getFilterData();
		   
		if((filterA.categoryBits == MyWorld.CATEGORY_PLAYER && filterB.categoryBits == MyWorld.CATEGORY_RUNNER) ||
				(filterB.categoryBits == MyWorld.CATEGORY_PLAYER && filterA.categoryBits == MyWorld.CATEGORY_RUNNER )){
			
			//Log.e("don't","+");
			return false; 
		} 
		/*&& ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("p"))||
				(fixtureB.getUserData().equals("player") && fixtureA.getUserData().equals("p")))*/
		
		return true;
	}
}
