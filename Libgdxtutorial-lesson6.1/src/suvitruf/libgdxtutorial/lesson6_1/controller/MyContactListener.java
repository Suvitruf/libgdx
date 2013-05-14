package suvitruf.libgdxtutorial.lesson6_1.controller;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class MyContactListener implements ContactListener{
	World world;
	public MyContactListener(World world){
		super();
		this.world = world;
	}
	  @Override 
      public void endContact(Contact contact) {
      }
	  
      @Override
      public void beginContact(Contact contact) {
    	
    	  //String x="", y="";
    	  /* String p ="";
    	  WorldManifold manifold = contact.getWorldManifold();
    	 
    	  if(contact.getFixtureA().getUserData() != null){
    		  x = contact.getFixtureA().getBody().getPosition().x;
    		  y = contact.getFixtureA().getBody().getPosition().y;
    	  }
    	  if(contact.getFixtureB().getUserData() != null){
    		  x = contact.getFixtureB().getBody().getPosition().x;
    		  y = contact.getFixtureB().getBody().getPosition().y;
    	  }*/
    	  /*for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
    		   p+="["+Float.toString(manifold.getPoints()[j].x)+";"+Float.toString(manifold.getPoints()[j].y)+"]";
    		   
				
			
			}
    	  Log.e("contact",p);*/
      }
      
      @Override
      public void preSolve (Contact contact, Manifold oldManifold){
    	 /* String p ="";
    	  WorldManifold manifold = contact.getWorldManifold();
    	  for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
   		   p+="["+Float.toString(manifold.getPoints()[j].x)+";"+Float.toString(manifold.getPoints()[j].y)+"]";
   		   
				
			
			}
   	  Log.e("contact",p);*/
    	  WorldManifold manifold = contact.getWorldManifold();
    	  for(int j = 0; j < manifold.getNumberOfContactPoints(); j++){
    		  if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("p"))
    			  contact.setEnabled(false);
    		  if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("p"))
    			  contact.setEnabled(false);
    	  }
      }
      
      @Override
      public void postSolve (Contact contact, ContactImpulse impulse){
    
    	  
    	  Body body = null;
    	  if(contact.getFixtureA() != null && contact.getFixtureA().getUserData() != null  && contact.getFixtureA().getUserData().equals("b"))
    		  body = contact.getFixtureA().getBody();
    	  
    	  if(contact.getFixtureB() != null && contact.getFixtureB().getUserData() != null  && contact.getFixtureB().getUserData().equals("b"))
    		  body = contact.getFixtureB().getBody();
    	 
    	  if(body != null){  
    		  
    		 
    		 body.setActive(false);
    		  world.destroyBody(body); 
    		
    		  
    	  }
      }

}
