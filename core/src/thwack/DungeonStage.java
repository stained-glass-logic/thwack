package thwack;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import thwack.Dungeon.DungeonType;
import thwack.model.entities.mobs.Aimer;
import thwack.model.entities.mobs.MobGroup;
import thwack.model.entities.mobs.Rat;
import thwack.model.entity.Updateable;



public class DungeonStage {
	
	MobGroup mobs;
	long elapsedSeconds;
	long elapsedNanoSeconds;
	long maxSeconds;
	long lastNanoTick;
	String stageName;
	long secondsUntilNextStage;
	
	boolean waitingToStartNextStage = false;
	boolean stageCleared;
	
	DungeonType dungeonType;
	
	public DungeonStage(String name, DungeonType dungeonMode) {
		mobs = new MobGroup();
		lastNanoTick = System.nanoTime();
		stageName = name;
		elapsedSeconds = 0;
		maxSeconds = 10;
		waitingToStartNextStage = false;
		stageCleared = false;
		secondsUntilNextStage = -1;
		dungeonType = dungeonMode;
	}
	
	public DungeonStage(String name, String stageMobs, World world, Array<Updateable> updateable) {
		stageName = name;
		mobs = new MobGroup();
		lastNanoTick = System.nanoTime();
		buildFromString(stageMobs, world, updateable);
	}
	
	public void buildFromString(String mobString, World world, Array<Updateable> updateables) {
		System.out.print("Building stage from string");
		String[] mobTypes = mobString.split(" ");
		System.out.print("...got " + mobTypes.length + " types of mobs");
		for (int i=0;i<mobTypes.length;i++) {
			int MOBTYPE =0;
			int MOBCOUNT = 1;
			String[] thisMobType = mobTypes[i].split("=");
			int mobCount = Integer.parseInt(thisMobType[MOBCOUNT]);
			System.out.print("...Creating " + mobCount + " of " + thisMobType[MOBTYPE]);
			for (int n=0;n<mobCount;n++) {
				if (thisMobType[MOBTYPE].equals("rat")) {
			        Vector2 ratPos = new Vector2(6f, 25f);
			        Vector2 ratSize = new Vector2(.5f, .5f);
		            Rat rat = new Rat(world, ratPos, ratSize);
		            mobs.addRat(rat);
		            updateables.add(rat);
				}
				else if (thisMobType[MOBTYPE].equals("aimer")) {
			        Vector2 aimerPos = new Vector2(6f, 25f);
			        Vector2 aimerSize = new Vector2(.5f, .5f);
		            Aimer aimer = new Aimer(world, aimerPos, aimerSize);
		            mobs.addAimer(aimer);
		            updateables.add(aimer);
		  		}
				else {
					System.out.println(".Couldn't add " + thisMobType[MOBTYPE]);
				}
			}
		}
		System.out.println("");
	}
	
	public void beginStage(String stageTemplate, World world, Array<Updateable> updateables) {
			buildFromString(stageTemplate, world, updateables);
			lastNanoTick = System.nanoTime();
	}
	
	public void update() {
		long thisNanoTick = System.nanoTime();
		elapsedNanoSeconds += thisNanoTick - lastNanoTick;
		elapsedSeconds = TimeUnit.SECONDS.convert(elapsedNanoSeconds,TimeUnit.NANOSECONDS);
		//System.out.println("" + elapsedSeconds);
		lastNanoTick = System.nanoTime();
		if (dungeonType == DungeonType.clearStages) {
			//check to see if any living mobs are left
			if (this.mobs.getLivingMobCount() < 1) {
				//check for stage clear and do appropriate actions
				if (stageCleared == false) {
					//do stage clear actions
					stageCleared = true;
					waitingToStartNextStage = true;
					
					
				}
				//otherwise update timer etc
				else if (stageCleared == true) {
					if (waitingToStartNextStage == true) {
						if (secondsUntilNextStage == -1) {
							//start timer
							elapsedNanoSeconds = 0;
							elapsedSeconds = 0;
							secondsUntilNextStage = 5;
							
						}
						else {
							
						}
					}
					else {
						
					}
				}
			}
			if (secondsUntilNextStage != -1 && waitingToStartNextStage == true 
					&& elapsedSeconds >= secondsUntilNextStage) {
				waitingToStartNextStage = false;
			}

		}
	}
	

	
	public MobGroup getMobs() { return mobs; }
	
}
