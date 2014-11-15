package thwack.controller;

import com.sun.xml.internal.stream.Entity;


/**
 * @author Daniel Holderbaum
 */
public class ContactHandlerInformation {
 
	public Class<? extends Entity> classA;
 
	public Class<? extends Entity> classB;
 
	public ContactHandler contactHandler;
 
	public ContactHandlerInformation(Class<? extends Entity> classA, Class<? extends Entity> classB, ContactHandler contactHandler) {
		this.classA = classA;
		this.classB = classB;
		this.contactHandler = contactHandler;
	}
}