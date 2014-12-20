package thwack.controller;

import thwack.model.entity.Entity;

/**
 * @author Daniel Holderbaum
 */
public class ContactHandlerInformation {
 
	public Class<? extends Entity> classA;
 
	public Class<? extends Entity> classB;
 
	@SuppressWarnings("rawtypes")
	public ContactHandler contactHandler;
 
	@SuppressWarnings("rawtypes")
	public ContactHandlerInformation(Class<? extends Entity> classA, Class<? extends Entity> classB, ContactHandler contactHandler) {
		this.classA = classA;
		this.classB = classB;
		this.contactHandler = contactHandler;
	}
}