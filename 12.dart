abstract class PaymentSystem {
  void pay();
  void refund();
}

class CreditCard implements PaymentSystem {
  @override
  void pay() {
    print('Payment made with Credit Card');
  }

  @override
  void refund() {
    print('Refund issued to Credit Card');
  }
}

class PayPal implements PaymentSystem {
  @override
  void pay() {
    print('Payment made with PayPal');
  }

  @override
  void refund() {
    print('Refund issued to PayPal');
  }
}
