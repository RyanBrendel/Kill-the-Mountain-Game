package Game;

public class Skill {
	String name;
	String description;
	int baseDmg; 			//Additional damage based on appropriate stat
	int linkedStatValue;	//stat used for skill; retrieved based on skill
	int skillPoints;		//cost, in skill points, that this skill uses
	
	
	
	public Skill()
	{
		name = "noname";
		description = "no description";
		baseDmg = 0;
		linkedStatValue = 0;
		skillPoints = 1;
	}
	public Skill(String newName, String desc, int dmg, int stat, int sp)
	{
		this.name = newName;
		this.description = desc;
		this.baseDmg = dmg;
		this.linkedStatValue = stat;
		this.skillPoints = sp;
	}
	
}
