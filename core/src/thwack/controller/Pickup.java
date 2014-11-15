package thwack.controller;

import thwack.model.Entity;

import com.badlogic.gdx.physics.box2d.Body;

public class Pickup extends Entity {
	 
	  private Body body;
	 
	  public void Player() {
	    body.setUserData(this);
	  }
	 
	}