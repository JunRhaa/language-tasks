class UniqueID {
  static int _idCounter = 0;

  static String generateID() {
    _idCounter++;
    return 'ID$_idCounter';
  }
}
