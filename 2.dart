class Worker extends Person {
  double salary;

  Worker(String name, int age, String gender, this.salary) : super(name, age, gender);
}

class Manager extends Worker {
  List<Worker> subordinates;

  Manager(String name, int age, String gender, double salary, this.subordinates)
      : super(name, age, gender, salary);
}

void main() {
  print("Enter worker's name:");
  String name = stdin.readLineSync() ?? '';
  print("Enter worker's age:");
  int age = int.parse(stdin.readLineSync() ?? '0');
  print("Enter worker's gender:");
  String gender = stdin.readLineSync() ?? '';
  print("Enter worker's salary:");
  double salary = double.parse(stdin.readLineSync() ?? '0');

  Worker worker = Worker(name, age, gender, salary);
  worker.displayInfo();
}
