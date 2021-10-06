package de.freesoccerhdx.advancedworldcreator.main;


public class CloneOptions {
	

	
	protected boolean COPY_BIOMESTRUCTURES = false; 
	protected boolean COPY_DECORATIONFEATURES = false; 
	protected boolean COPY_MOBSETTINGS = false; 
	protected boolean COPY_CLIMATE = false; 
	protected boolean COPY_BIOMEFOG = false; 
	protected boolean COPY_GEOGRAPHY = false; 
	protected boolean COPY_SCALE = false; 		
	protected boolean COPY_DEPTH = false; 
	
	public CloneOptions() {	
	}
	
	public CloneOptions COPY_ALL(boolean b) {
		 COPY_BIOMESTRUCTURES = b; 
		 COPY_DECORATIONFEATURES = b; 
		 COPY_MOBSETTINGS = b; 
		 COPY_CLIMATE = b; 
		 COPY_BIOMEFOG = b; 
		 COPY_GEOGRAPHY = b; 
		 COPY_SCALE = b; 		
		 COPY_DEPTH = b; 
		
		return this;
	}
	
	public CloneOptions COPY_BIOMESTRUCTURES(boolean b) {
		COPY_BIOMESTRUCTURES = b;
		return this;
	}
	
	public CloneOptions COPY_DECORATIONFEATURES(boolean b) {
		COPY_DECORATIONFEATURES = b;
		return this;
	}
	
	public CloneOptions COPY_MOBSETTINGS(boolean b) {
		COPY_MOBSETTINGS = b;
		return this;
	}
	
	public CloneOptions COPY_CLIMATE(boolean b) {
		COPY_CLIMATE = b;
		return this;
	}
	
	public CloneOptions COPY_BIOMEFOG(boolean b) {
		COPY_BIOMEFOG = b;
		return this;
	}
	
	public CloneOptions COPY_GEOGRAPHY(boolean b) {
		COPY_GEOGRAPHY = b;
		return this;
	}
	
	public CloneOptions COPY_DEPTH(boolean b) {
		COPY_DEPTH = b;
		return this;
	}
	
	public CloneOptions COPY_SCALE(boolean b) {
		COPY_SCALE = b;
		return this;
	}

}
