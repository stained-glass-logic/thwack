package thwack.model.entities.mobs;

import java.util.ArrayList;

import thwack.view.AimerRenderer;
import thwack.view.LifebarRenderer;
import thwack.view.RatRenderer;

public class MobGroup {

	ArrayList<Rat> rats;
	ArrayList<Aimer> aimers;
	
	public MobGroup() {
		rats = new ArrayList<Rat>();
		aimers = new ArrayList<Aimer>();
	}
	
	
	
	public void renderMobs(RatRenderer ratRenderer, AimerRenderer aimerRenderer, LifebarRenderer lifebarRenderer) {
		for (int i=0;i<rats.size();i++) {
			if (rats.get(i).isAlive()) {
				ratRenderer.render(rats.get(i));
				if (rats.get(i).isAlive() && rats.get(i).lifebar != null) {
					lifebarRenderer.render(rats.get(i).getBody().getPosition().x,
											rats.get(i).getBody().getPosition().y,
											rats.get(i).lifebar);
				}
			}
		}
		
		for (int i=0;i<aimers.size();i++) {
			if (aimers.get(i).isAlive()) {
				aimerRenderer.render(aimers.get(i));
				if (aimers.get(i).isAlive() && aimers.get(i).lifebar != null) {
					lifebarRenderer.render(aimers.get(i).getBody().getPosition().x,
											aimers.get(i).getBody().getPosition().y,
											aimers.get(i).lifebar);
				}
			}
		}
	}
	
	public void addRat(Rat newRat)  { 
		if (newRat != null) {
			rats.add(newRat);
		}
	}
	
	public void addAimer(Aimer newAimer) {
		if (newAimer != null) {
			aimers.add(newAimer);
		}
	}
		
		
	public int getMobCount()  { 
		return rats.size() + aimers.size(); 
	}
	
	public void addMobGroup(MobGroup moreMobs)
	{
		for (int i=0;i<moreMobs.getRatCount();i++) {
			rats.add(moreMobs.getRatByIndex(i));
		}
		for (int i=0;i<moreMobs.getAimerCount();i++) {
			aimers.add(moreMobs.getAimerByIndex(i));
		}
	}
	
	public void clearMobs() {
		rats.clear();
		aimers.clear();
	}
	
	public int getLivingMobCount() {
		int mobCount = 0;
		for (int i=0;i<rats.size();i++) {
			if (rats.get(i).isAlive()) mobCount++;
		}
		for (int i=0;i<aimers.size();i++) {
			if (aimers.get(i).isAlive()) mobCount++;
		}
		return mobCount;
	}
	
	public int getRatCount() { return rats.size(); }
	public int getAimerCount() { return aimers.size(); }
	
	public Rat getRatByIndex(int i) {
		if (i >= 0 && i < rats.size()) {
			return rats.get(i);
		}
		else return null;
	}
	public Aimer getAimerByIndex(int i) {
		if (i >= 0 && i < aimers.size()) {
			return aimers.get(i);
		}
		else return null;
	}
	
}
