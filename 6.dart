class BankAccount {
  String accountNumber;
  double _balance;

  BankAccount(this.accountNumber) : _balance = 0.0;

  void deposit(double amount) {
    _balance += amount;
  }

  void withdraw(double amount) {
    if (amount <= _balance) {
      _balance -= amount;
    } else {
      print('Insufficient funds');
    }
  }

  double get balance => _balance;
}
