package org.uniplore;

public class ShipFactory {
	
	private StarShip ss = new HmStarShip();

	public ShipFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public void Xy(){
		ss = new XyStarShip();
	}
	
	public void Zl(){
		ss = new ZlStarShip();
	}
	public void show(){
		ss.action();
	}

}
