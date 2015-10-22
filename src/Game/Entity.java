package Game;

public class Entity {
	//stat related
	double baseHealth;
	double currentHealth;
	double maxHealth;
	double baseMana;
	double maxMana;
	double minMana;
	double currentMana;
	boolean armorOn;
	int level;
	double strength;
	double maxStrength;
	double minStrength;
	double luck;
	boolean isAlive = true;

	double maxSpeed;
	double minSpeed;
	double magicResistance;
	double physicalResistance;
	double fireResistance;
	double iceResistance;

	//position related
	double xpos;
	double ypos;
	double movementSpeed;
	public Entity(){

	}

	public void takeDamage(double damage) {
		if (armorOn) {
			currentHealth-=damage/2; // will change to damage/armorResistance
		}

		if (currentHealth - damage <= 0) {
			currentHealth = 0;
			isAlive = false;
		}

		else {
			currentHealth-=damage;
		}

	}

	public void manaChange(double amount) {
		if (currentMana + amount >= maxMana) {
			currentMana = maxMana;
		}
		else if (currentMana + amount <= 0) {
			currentMana = 0;
		}
		else {
			currentMana+=amount;
		}
	}

	public void healthUp(double amount) {

		if (currentHealth+amount > maxHealth) {
			currentHealth = maxHealth;
		}
		else {
			currentHealth+=amount;
		}
	}

	public void speedChange(double amount) {

		if (movementSpeed*amount > maxSpeed) {
			movementSpeed = maxSpeed;
		}
		else if (movementSpeed*amount < minSpeed) {
			movementSpeed = minSpeed;
		}
		else{
			movementSpeed*=amount;
		}

	}

	public void strengthChange(int amount) {

		if (strength+amount > maxStrength) {
			strength = maxStrength;
		}
		else if (strength+amount < minStrength) {
			strength = minStrength;
		}
		else {
			strength+=amount;
		}

	}

	public void setMaxMana(double amount) {
		maxMana = amount;
	}

	public double getMaxMana() { 
		return maxMana;
	}

	public double getMana () {
		return currentMana;
	}

	public void setMana(double mana) {
		if (mana >= maxMana) {
			currentMana = maxMana;
		}

		if (mana <= minMana) {
			mana = minMana;
		}

		else {
			currentMana = mana;
		}

	}

	public double getMaxHealth() {
		//loop through inventory and add how they effect maxhealth
		return maxHealth;
	}

	public double getHealth() {
		return currentHealth;
	}

	public double getStrength() {
		return strength;
	}

}
