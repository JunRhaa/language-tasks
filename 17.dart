class Player {
  String name;
  int health;
  List<Weapon> weapons;

  Player(this.name, this.health) : weapons = [];

  void attack(Enemy enemy) {
    if (weapons.isNotEmpty) {
      print('$name attacks ${enemy.name} with ${weapons[0].name}!');
      enemy.takeDamage(weapons[0].damage);
    } else {
      print('$name has no weapons to attack!');
    }
  }

  void addWeapon(Weapon weapon) {
    weapons.add(weapon);
  }
}

class Enemy {
  String name;
  int health;

  Enemy(this.name, this.health);

  void takeDamage(int damage) {
    health -= damage;
    print('$name takes $damage damage, health is now $health');
  }
}

class Weapon {
  String name;
  int damage;

  Weapon(this.name, this.damage);
}
