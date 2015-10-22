package Game;

import java.util.ArrayList;

public class CombatEntity {
	
	/*REMOVE THESE WHEN POSSIBLE*/
	double xpos;
	double ypos;
	double movespeed;
	
	/*Character's combat class. Affects character progression, 
	 * statistics, and specific game conditions/effects*/
	public enum playerRace
	{
		HUMAN, DWARF, ELF, MUNCHKIN, BEAR
	}
	
	public enum combatClass
	{
		FIGHTER, TANK, 
		ROGUE,
		WIZARD, SORCERER,
		PEASANT
	}
		
	/*ABILITY STATS*/
	private int hp;		//hit points; character dies at 0hp
	private int man;	//mana; must have mana to use magic attacks
	private int str;	//strength; basis for physical damage
	private int acc;	//accuracy; basis for attack hit chance
	private int mag;	//magic; basis for magical damage
	private int intel;	//intelligence; basis for skill/spell hit chance
	private int eva;	//evasion; basis for avoiding attacks/skills
	private int	lck;	//luck; affects nearly everything in your favor
	private int arm;	//armor; basis for reducing physical damage
	private int mre;	//magic resistance; basis for reducing magic damage
	
	
	/*COMBAT STATISTICS*/
	//damage variables
	private int physDamage;
	private int magDamage;
	
	//damage reduction multipliers.
	private double physDR;  //physical damage resistance; based on armor
	private double magDR;   //magical damage resistance; based on MRe
	private int totalArmor; //total armor; includes all armor items and natural
	private int totalMRE;	//total magic resist; includes all items and natural
	
	private ArrayList<Skill> skills = new ArrayList<Skill>();
	
	public int getHP()
	{
		return this.hp;
	}
	public void setHP(int newHP)
	{
		this.hp = newHP;
	}
	public int getMana()
	{
		return this.man;
	}
	public void setMana(int newMana)
	{
		this.hp = newMana;
	}
	public int getStrength()
	{
		return this.str;
	}
	public void setStrength(int newStrength)
	{
		this.str = newStrength;
	}
	public int getAcc()
	{
		return this.acc;
	}
	public void setAcc(int newAcc)
	{
		this.acc = newAcc;
	}
	public int getMagic()
	{
		return this.mag;
	}
	public void setMagic(int newMagic)
	{
		this.mag = newMagic;
	}
	public int getIntelligence()
	{
		return this.intel;
	}
	public void setIntelligence(int newIntel)
	{
		this.intel = newIntel;
	}
	public int getEvasion()
	{
		return this.eva;
	}
	public void setEvasion(int newEvasion)
	{
		this.eva = newEvasion;
	}
	public int getLuck()
	{
		return this.lck;
	}
	public void SetLuck(int newLuck)
	{
		this.lck = newLuck;
	}
	public int getArmor()
	{
		return this.arm;
	}
	public void setArmor(int newArmor)
	{
		this.arm = newArmor;
	}
	public int getMagicResist()
	{
		return this.mre;
	}
	public void setMagicResist(int newMagicResist)
	{
		this.mre = newMagicResist;
	}
	public double getPDR()
	{
		return this.physDR;
	}
	public double getMDR()
	{
		return this.magDR;
	}
	public int getPDmg()
	{
		return this.physDamage;
	}
	public int getMDmg()
	{
		return this.magDamage;
	}
		
	/**
	 * Update combat statistics based on ability stats
	 */
	public void updateStats()
	{
		physDamage = str + (1/4)*acc;
		magDamage = mag + (1/4)*intel;
		physDR = 100/(100 + arm);
		magDR = 100/(100 + mre);
	}	
	
	public CombatEntity()
	{
		acc = 10;
		arm = 0;
		eva = 10;
		hp = 10;
		man = 10;
		mag = 10;
		intel = 10;
		str = 10;
		lck = 10;
		mre = 10;
	}
	
	public CombatEntity(playerRace race, combatClass startingClass)
	{
		switch(race)
		{
		case HUMAN:	//average statistics
			hp  = 10;
			man = 0;
			str = 10;
			arm = 0;
			acc = 10;
			eva = 10;
			mag = 10;
			mre = 10;
			lck = 10;
			
			break;
			
		case DWARF:	//high natural resistances and hp, but clumsy
			hp  = 15;
			man = 0;
			str = 12;
			arm = 10;
			acc = 7;
			eva = 7;
			mag = 10;
			mre = 8;
			lck = 8;
			break;
			
		case ELF:	//high magic, low natural resistances
			hp  = 8;
			man = 10;
			str = 6;
			arm = 0;
			acc = 12;
			eva = 8;
			mag = 10;
			mre = 8;
			lck = 8;
			
			break;
			
		case MUNCHKIN: //high accuracy/evasion, low hp/resistance
			hp  = 10;
			man = 0;
			str = 7;
			arm = 0;
			acc = 14;
			eva = 14;
			mag = 10;
			mre = 6;
			lck = 15;
			
			break;
			
		case BEAR:	//very high damage, but clumsy and little magic
			hp  = 20;
			man = 0;
			str = 20;
			arm = 5;
			acc = 6;
			eva = 6;
			mag = 0;
			mre = 6;
			lck = 8;
			
			break;			
		}		
		switch(startingClass)
		{
		case FIGHTER:	
			hp += 0;
			man += 0;
			str += 0;
			arm += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			mre += 0;
			lck += 0;
			
			break;	
				
		case TANK:
			hp += 0;
			man += 0;
			str += 0;
			arm += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			mre += 0;
			lck += 0;
			
			break;
			
		case ROGUE:	
			hp += 0;
			man += 0;
			str += 0;
			arm += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			mre += 0;
			lck += 0;
				
			break;
			
		case WIZARD:	
			hp += 0;
			man += 0;
			str += 0;
			arm += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			mre += 0;
			lck += 0;
			
			break;
		
		case SORCERER:		
			hp += 0;
			man += 0;
			str += 0;
			arm += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			mre += 0;
			lck += 0;
			
			break;
		
		case PEASANT:		
			hp += 0;
			man += 0;
			str += 0;
			arm += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			mre += 0;
			lck += 0;
					
			break;
		}		
		updateStats();		
	}
	
}
