package suvitruf.libgdxtutorial.lesson6_1.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.math.Vector2;

public class Player {
	final static float MAX_VELOCITY = 3f;
	public final static float SPEED = 5f;
	public final static float SIZE = 0.8f;
	
	public Fixture playerPhysicsFixture;
	public Fixture playerSensorFixture;
	Body box;
	public  Player(Body b){ 
		box = b;
		//BodyDef def = new BodyDef();
		//def.type = BodyType.DynamicBody;
		//this.createBody(def);
		//box = world.createBody(def);
		
		PolygonShape poly = new PolygonShape();		
		poly.setAsBox(0.4f, 0.4f);
		playerPhysicsFixture = box.createFixture(poly,0);
		poly.dispose();			
		
		CircleShape circle = new CircleShape();		
		circle.setRadius(0.4f);
		circle.setPosition(new Vector2(0, -0.05f));
		playerSensorFixture = box.createFixture(circle, 0);
		//трение
		//setFriction(200F);
		circle.dispose();		
 
		box.setBullet(true);
		
		//box.setGravityScale(3);
		
	}public float getFriction(){
		return playerSensorFixture.getFriction();
	}
	public Body getBody(){
		return box;
	}
	public void setFriction(float f){
		playerSensorFixture.setFriction(f); 
		playerPhysicsFixture.setFriction(f); 
	}
	public Vector2 getPosition(){
		return box.getPosition();
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	Vector2 	velocity = new Vector2();
	public void update(float delta) {
		//box.getPosition().add(velocity.tmp().mul(delta)); 
		Vector2 vel = box.getLinearVelocity();
		//velocity = velocity.tmp().mul(delta);
		velocity.y = vel.y;
		box.setLinearVelocity(velocity);
		if(isJump) {box.applyLinearImpulse(0, 14, box.getPosition().x,  box.getPosition().y);	isJump = false;}
		//bounds.setX(position.x);
		//bounds.setY(position.y);
	}
	boolean isJump = false;
	public void jump(){
		isJump = true;
	}
	public void resetVelocity(){
		getVelocity().x =0;
		getVelocity().y =0;
	}
	/*public Body getPlayer(){
		return box;
	}*/
}
