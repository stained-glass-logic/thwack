package thwack.controller;

import thwack.model.entity.Entity;

import com.badlogic.gdx.physics.box2d.Body;

public class Pickup extends Entity {
	 
	  private Body body;
	 
	  public void Player() {
	    body.setUserData(this);
	  }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	 
	}