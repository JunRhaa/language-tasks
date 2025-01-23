class Matrix {
  List<List<double>> values;

  Matrix(this.values);

  Matrix operator +(Matrix other) {
    List<List<double>> result = List.generate(
      values.length,
          (i) => List.generate(values[0].length, (j) => values[i][j] + other.values[i][j]),
    );
    return Matrix(result);
  }

  Matrix operator *(Matrix other) {
    int rowsA = values.length;
    int colsA = values[0].length;
    int colsB = other.values[0].length;

    List<List<double>> result = List.generate(rowsA, (i) => List.filled(colsB, 0.0));

    for (int i = 0; i < rowsA; i++) {
      for (int j = 0; j < colsB; j++) {
        for (int k = 0; k < colsA; k++) {
          result[i][j] += values[i][k] * other.values[k][j];
        }
      }
    }
    return Matrix(result);
  }

  @override
  String toString() {
    return values.map((row) => row.toString()).join('\n');
  }
}
