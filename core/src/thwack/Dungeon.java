package thwack;

import java.util.ArrayList;

import thwack.model.entity.Updateable;
import thwack.view.AimerRenderer;
import thwack.view.LifebarRenderer;
import thwack.view.RatRenderer;
import thwack.view.SawRatRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Dungeon {

	ArrayList<DungeonStage> stages;
	int currentStage;
	
	ArrayList<String> stageTemplates;
	
	BitmapFont dungeonFont;
	
	public enum DungeonType {
		clearStages,		//clear a stage to get to the next one
		timedStages			//stages are timed and can stack up
	}
	


	
	DungeonType thisDungeonType;
	
	
	public Dungeon(World world, Array<Updateable> updateables) {
		stages = new ArrayList<DungeonStage>();

		//thisDungeonType = DungeonType.clearStages;
		thisDungeonType = DungeonType.timedStages;

		
		stageTemplates = new ArrayList<String>();
		//create arbitrary stages
		stageTemplates.add("rat=1");
		stages.add(new DungeonStage("Stage 1: It's a rat!", thisDungeonType));
		stageTemplates.add("rat=2");
		stages.add(new DungeonStage("Stage 2: Rat's backup", thisDungeonType));
		stageTemplates.add("rat=4");
		stages.add(new DungeonStage("Stage 3: The Rat Pack", thisDungeonType));
		stageTemplates.add("rat=1 aimer=1");
		stages.add(new DungeonStage("Stage 4: This one shoots", thisDungeonType));
		stageTemplates.add("rat=2 sawrat=1");
		stages.add(new DungeonStage("Stage 5: This one has a CHAINSAW!!!", thisDungeonType));
		stageTemplates.add("rat=2 aimer=4 sawrat=2");
		stages.add(new DungeonStage("Stage 6: Rat clan", thisDungeonType));
		stageTemplates.add("rat=50 aimer=50 sawrat=50");
		stages.add(new DungeonStage("BONUS STAGE! YAY!", thisDungeonType));
		
		
		currentStage = -1;
		if (stages.size() > 0 && stages.get(0) != null) {
			currentStage = 0;
			this.getCurrentStage().beginStage(stageTemplates.get(0),world,updateables);
		}
		
		//dungeonFont = new BitmapFont();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ScalaSans_Black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		dungeonFont = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!

	}
	
	public void update(World world, Array<Updateable> updateables) {
		if (thisDungeonType == DungeonType.clearStages && currentStage != -1) {
			stages.get(currentStage).update();
			if (stages.get(currentStage).waitingToStartNextStage == false
					&& stages.get(currentStage).stageCleared == true) {
				advanceToNextStage(world, updateables);
			}
			
		
		}
		else if (thisDungeonType == DungeonType.timedStages && currentStage != -1) {
			stages.get(currentStage).update();
			if (stages.get(currentStage).elapsedSeconds >= stages.get(currentStage).maxSeconds) {
				advanceToNextStage(world, updateables);
			}
		}
	}
	
	public void advanceToNextStage(World world, Array<Updateable> updateables) {
		Global.DebugOutLine("Advancing to next stage");
		if (currentStage+1 < stages.size()) {
			currentStage++;
			if (thisDungeonType == DungeonType.timedStages) {
				stages.get(currentStage).getMobs().addMobGroup(stages.get(currentStage-1).getMobs());
				stages.get(currentStage-1).getMobs().clearMobs();
			}
			else if (thisDungeonType == DungeonType.clearStages) {
				
			}
			stages.get(currentStage).beginStage(stageTemplates.get(currentStage), world, updateables);
		}
		else {
			Global.DebugOutLine("No more stages...");
		}
	}
	
	public DungeonStage getCurrentStage() {
		if (currentStage != -1 && currentStage < stages.size()) {
			return stages.get(currentStage);
		}
		else return null;
	}
	
	public void drawGUI(SpriteBatch batch) {
		dungeonFont.setColor(Color.YELLOW);
		dungeonFont.setScale(0.6f,0.6f);
		dungeonFont.draw(batch,this.getCurrentStage().stageName,-60,-70);
		if (thisDungeonType == DungeonType.clearStages) {
			/*
			dungeonFont.setColor(Color.BLUE);
			dungeonFont.draw(batch,""+getCurrentStage().secondsUntilNextStage,60,90);
			dungeonFont.draw(batch,""+getCurrentStage().maxSeconds,60,80);
			dungeonFont.draw(batch,""+getCurrentStage().elapsedSeconds,60,70);
			*/
			
			if (getCurrentStage().waitingToStartNextStage == false) {
				dungeonFont.setColor(Color.WHITE);
				dungeonFont.draw(batch,"Remaining enemies:",-60,-90);
				dungeonFont.setColor(Color.RED);
				dungeonFont.draw(batch,""+this.getCurrentStage().mobs.getLivingMobCount(),80,-90);
			}
			else {
				dungeonFont.setColor(Color.WHITE);
				dungeonFont.draw(batch,"Next stage in:",-60,-90);
				dungeonFont.setColor(Color.RED);
				int remainingSeconds = (int)(this.getCurrentStage().secondsUntilNextStage - this.getCurrentStage().elapsedSeconds);
				dungeonFont.draw(batch,""+remainingSeconds + "s",80,-90);
				
			}
		}
		if (thisDungeonType == DungeonType.timedStages) {
			dungeonFont.setColor(Color.WHITE);
			dungeonFont.draw(batch,"Next wave in:",-60,-90);
			dungeonFont.setColor(Color.RED);
			int remainingSeconds = (int)(this.getCurrentStage().maxSeconds - this.getCurrentStage().elapsedSeconds);
			dungeonFont.draw(batch,""+remainingSeconds + "s",80,-90);
		}
	}
	
	public void drawMobs(RatRenderer ratRenderer, AimerRenderer aimerRenderer, SawRatRenderer sawratRenderer, LifebarRenderer lifebarRenderer) {
		if (thisDungeonType == DungeonType.clearStages) {
			this.getCurrentStage().getMobs().renderMobs(ratRenderer,aimerRenderer, sawratRenderer, lifebarRenderer);
		}
		else if (thisDungeonType == DungeonType.timedStages) {
			for (int i=0;i<stages.size();i++) {
				if (i <= currentStage && i < stages.size()) {
					stages.get(i).getMobs().renderMobs(ratRenderer, aimerRenderer, sawratRenderer, lifebarRenderer);
				}
			}
		}
	}
	
}
