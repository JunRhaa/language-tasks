class Student {
  String name;
  String group;
  double grade;

  Student(this.name, this.group, this.grade);
}

class University {
  List<Student> students = [];

  void addStudent(Student student) {
    students.add(student);
  }

  List<Student> sortByName() {
    students.sort((a, b) => a.name.compareTo(b.name));
    return students;
  }

  List<Student> filterByGrade(double minGrade) {
    return students.where((student) => student.grade >= minGrade).toList();
  }
}
