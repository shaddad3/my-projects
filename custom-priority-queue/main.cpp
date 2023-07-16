
/*********************************************
 * CS 251: Program 3 
 * Student Author: Sammy Haddad
 * Short Description: create test cases for
 * the matrix class created (see matrix.h
 * file for a description of the matrix)
 * TA's that helped: Harshal, Khushboo
*********************************************/
#include <iomanip>
#include <istream>
#include "mymatrix.h"

using namespace std;

/* The next 6 functions are tests for the following member functions of the mymatrix class:
*  Default constructor (DC), parameterized constructor (PC), copy constructor (CC), and the 
*  size function. These functions test the functionality of these member functions and make 
*  sure they work in the way they are intended. More specifics for each test case are given
*  inline in each of the functions. They all have no paremeters, and return a boolean value.
*/
bool DCAndCC() {
    mymatrix<int> M1; //create a 4x4 matrix using default constructor
    mymatrix<int> M2 = M1; //make M2 a copy of M1 using copy constructor
    if (M1.size() == M2.size()) { 
        return true;
    }
    else {
        cout << "DCAndCC failed" << endl;
        return false;
    }
}

bool sizeAndDC() {
    mymatrix<int> M1; //create a 4x4 matrix using default constructor
    if (M1.size() == 16) { //4x4 is 16, so there should be 16 elements
        return true;
    }
    else {
        cout << "sizeAndDC failed" << endl;
        return false;
    }
    
}

bool PCAndCC() {
    mymatrix<double> M1(3,2); //create a 3x2 matrix using parameterized constructor
    mymatrix<double> M2(M1); //make M2 a copy of M1 using copy constructor
    if (M1.size() == M2.size()) {
        return true;
    }
    else {
        cout << "PCAndCC failed" << endl;
        return false;
    }
}

bool sizeAndPC1() {
    mymatrix<float> M1(100, 100); //create a large matrix using parameterized constructor
    if (M1.size() == 10000) { //100x100 is 10000, so there should be 10000 elements
        return true;
    }
    else {
        cout << "sizeAndPC1 failed" << endl;
        return false;
    }
}

bool sizeAndPC2() {
    mymatrix<double> M1(10, 10); //create a 10x10 matrix using parameterized constructor
    M1.growcols(5, 20); //make the matrix jagged 

    if (M1.size() == 110) { //10x10 matrix is 100 elements, plus 10 more from growcols()
        return true;
    }
    else {
        cout << "sizeAndPC2 failed" << endl;
        return false;
    }
}

bool sizeAndPC3() {
    mymatrix<string> M1(1, 1); //create a 1x1 (smallest possible) matrix using parameterized constructor
    if (M1.size() == 1) {
        return true;
    }
    else {
        cout << "sizeAndPC3 failed" << endl;
        return false;
    }
}

/* The next 7 functions are tests for the following member functions of the mymatrix class:
*  Parameterized constructor (PC), copy constructor (CC), and the operator() function. 
*  These functions test the functionality of these member functions and make sure they work
*  in the way they are intended. More specifics for each test case are given inline in each
*  of the functions. All the functions have no paremeters, and return a boolean value.
*/
bool PCAndOperatorParentheses() {
    mymatrix<int> M1(2,2); //create a 2x2 matrix using parameterized constructor
    if (M1(0,0) == 0) { //parameterized constructor should initialize elements to default value
        return true;
    }
    else {
        cout << "PCAndOperatorParentheses failed" << endl;
        return false;
    }
}

bool CCAndOperatorParentheses1() {
    mymatrix<long> M1(5,4); //make a 5x4 matrix using parameterized constructor
    M1(0, 0) = 21;
    M1(4, 3) = 42;
    mymatrix<long> M2(M1); //make M2 a copy of M1 using copy constructor

    if (M1(0, 0) == M2(0, 0) && M1(4, 3) == M2(4, 3)) {
        return true;
    }
    else {
        cout << "CCAndOperatorParentheses1 failed" << endl;
        return false;
    }
}

bool CCAndOperatorParentheses2() {
    mymatrix<float> M1(3,4); //make a 3x4 matrix using parameterized constructor
    M1(1, 2) = 22;
    mymatrix<float> M2 = M1; //make M2 a copy of M1 using copy constructor
    M2(1, 2)  = 44;

    if (M1(1, 2) != M2(1, 2)) { //M2 should be a deep copy, so changes made to M2 will not effect M1
        return true;
    }
    else {
        cout << "CCAndOperatorParentheses2 failed" << endl;
        return false;
    }
}

bool exceptionTestPC1() {
  try {
    mymatrix<int>  M1(0, 1); //should throw an exception, can't have 0 rows
    cout << "exceptionTestPC1 failed, 0 rows in parameterized constructor did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

bool exceptionTestPC2() {
  try {
    mymatrix<double>  M1(3, 0); //should throw an exception, can't have 0 columns
    cout << "exceptionTestPC2 failed, 0 columns in parameterized constructor did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

bool exceptionTestParentheses1() {
  try {
    mymatrix<int> M1(1, 1); //create a 1x1 matrix
    M1(1, 0); //should throw exception, row indices here are 0
    cout << "exceptionTestGrow1 failed, index out of range did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

bool exceptionTestParentheses2() {
  try {
    mymatrix<bool> M1; //creates default 4x4 matrix
    M1(1, 4); //should throw exception, row indices here are 0 to 3
    cout << "exceptionTestGrow1 failed, index out of range did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

/* The next 4 functions are tests for the following member functions of the mymatrix class:
*  Default constructor (DC), parameterized constructor (PC), and the numrows() and numcols() 
*  functions. These functions test the functionality of these member functions and make sure
*  they work in the way they are intended. More specifics for each test case are given inline
*  in each of the functions. All the functions have no paremeters, and return a boolean value.
*/
bool numrowsAndDC() {
    mymatrix<char> M1; //create a default matrix 
    if (M1.numrows() == 4) { //default constructor makes a 4x4, so rows should == 4
        return true;
    }
    else {
        cout << "numrowsAndDC failed" << endl;
        return false;
    }
}

bool numrowsAndPC() {
    mymatrix<string> M1(6, 8); //create a 6x8 matrix 
    if (M1.numrows() == 6) { //parameterized constructor makes a 6x8, so rows should == 6
        return true;
    }
    else {
        cout << "numrowsAndPC failed" << endl;
        return false;
    }
}

bool numcolsAndDC() {
    mymatrix<double> M1; //default 4x4 matrix
    if (M1.numcols(0) == 4) {
        return true;
    }
    else {
        cout << "numcolsAndDC failed" << endl;
        return false;
    }
}

bool exceptionTestNumcols() {
  try {
    mymatrix<long>  M1; //create a default 4x4 matrix
    M1.numcols(4); //should throw exception, indices for default matrix are 0 to 3
    cout << "exceptionTestNumcols failed, index out of range did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

/* The next 3 functions are tests for the following member functions of the mymatrix class:
*  The numcols() function and the growcols() function. These functions test the functionality
*  of these member functions and make sure they work in the way they are intended. More specifics
*  for each test case are given inline in each of the functions. All the functions have no 
*  paremeters, and return a boolean value.
*/
bool numcolsAndGrowcols() {
    mymatrix<long> M1; //default 4x4 matrix
    M1.growcols(1, 10); //make the matrix jagged
    if (M1.numcols(1) == 10 && M1.numcols(0) == 4) {
        return true;
    }
    else {
        cout << "numcolsAndGrowcols failed" << endl;
        return false;
    }
}

bool exceptionTestGrowcols1() {
  try {
    mymatrix<string> M1(5, 5); //create a 5x5 matrix
    M1.growcols(5, 10); //should throw exception, indices for 5x5 matrix are 0 to 4
    cout << "exceptionTestNumcols failed, index out of range did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

bool exceptionTestGrowcols2() {
  try {
    mymatrix<char> M1(2, 2); //create a 2x2 matrix
    M1.growcols(0, 0); //should throw exception, can not make columns size 0
    cout << "exceptionTestNumcols failed, attempt to grow column size to 0 did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

/* The next 5 functions are tests for the following member functions of the mymatrix class:
*  The numcols(), numrows(), and grow() functions. These functions test the functionality
*  of these member functions and make sure they work in the way they are intended. More specifics
*  for each test case are given inline in each of the functions. All the functions have no 
*  paremeters, and return a boolean value.
*/
bool numcolsAndGrow() {
    mymatrix<bool> M1; //default 4x4 matrix
    M1.grow(4, 8); //grow only the columns
    if (M1.numcols(0) == 8 && M1.numrows() == 4) {
        return true;
    }
    else {
        cout << "numcolsAndGrow failed" << endl;
        return false;
    }
}

bool numrowsAndGrow() {
    mymatrix<bool> M1(1, 1); //create a 1x1 matrix
    M1.grow(10, 1); //grow only the rows
    if (M1.numcols(0) == 1 && M1.numrows() == 10) {
        return true;
    }
    else {
        cout << "numrowsAndGrow failed" << endl;
        return false;
    }
}

bool growRowsAndColumns() {
    mymatrix<float> M1(3, 5); //create a 3x5 matrix
    M1.grow(7, 12); //grow both the rows and columns
    if (M1.numcols(0) == 12 && M1.numrows() == 7) {
        return true;
    }
    else {
        cout << "growRowsAndColumns failed" << endl;
        return false;
    }
}

bool exceptionTestGrow1() {
  try {
    mymatrix<float> M1(2, 3); //create a 2x3 matrix
    M1.grow(0, 4); //should throw exception, can not make row size 0
    cout << "exceptionTestGrow1 failed, attempt to grow row size to 0 did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

bool exceptionTestGrow2() {
  try {
    mymatrix<int> M1(1, 3); //create a 1x3 matrix
    M1.grow(4, 0); //should throw exception, can not make column size 0
    cout << "exceptionTestGrow1 failed, attempt to grow column size to 0 did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

/* The next 4 functions are tests for the following member functions of the mymatrix class:
*  Parameterized constructor (PC), copy constructor (CC), and .at() function. These functions test
*  the functionality of these member functions and make sure they work in the way they are intended.
*  More specifics for each test case are given inline in each of the functions. All the functions
*  have no paremeters, and return a boolean value.
*/
bool PCAndOperatorAt() {
    mymatrix<int> M1(8, 9); //create a 8x9 matrix using parameterized constructor
    if (M1.at(0,0) == 0) { //parameterized constructor should initialize elements to default value
        return true;
    }
    else {
        cout << "PCAndOperatorAt failed" << endl;
        return false;
    }
}

bool CCAndOperatorAt() {
    mymatrix<long> M1(9, 12); //create a 9x12 matrix using parameterized constructor
    M1.at(2,10) = 65;
    mymatrix<long> M2 = M1; //make M2 a copy of M1 using copy constructor
    M2.at(2,10) = 32;
    if (M1.at(2,10) != M2.at(2,10)) { //M2 should be a deep copy, so changes to M2 should not be reflected in M1
        return true;
    }
    else {
        cout << "CCAndOperatorAt failed" << endl;
        return false;
    }
}

bool exceptionTestAt1() {
  try {
    mymatrix<string> M1(4, 6); //create a 4x6 matrix
    M1.at(4, 0); //should throw exception, row indices here are 0 to 3
    cout << "exceptionTestGrow1 failed, index out of range did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

bool exceptionTestAt2() {
  try {
    mymatrix<bool> M1(6, 7); //create a 6x7 matrix
    M1.at(3, 7); //should throw exception, column indices here are 0 to 6
    cout << "exceptionTestGrow1 failed, index out of range did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true;
  }
}

/* The next 3 functions are tests for the following member function of the mymatrix class:
*  The scalar multipilcation function. These functions test the functionality of this 
*  member function and make sure it works in the intended way. More specifics for each
*  test case are given inline in each of the functions. All the functions have no 
*  paremeters, and return a boolean value.
*/
bool scalarMultiplication1() {
    mymatrix<int> M1(2, 2); //create a 2x2 matrix
    M1(0,0) = 3;
    M1(0,1) = 6;
    M1.at(1,0) = 12;
    M1.at(1,1) = 24;
    mymatrix<int> M2 = M1 * 2;
    if (M2(0,0) == 6 && M2(0,1) == 12 && M2.at(1,0) == 24 && M2.at(1,1) == 48) { //should double the value at each index
        return true;
    }
    else {
        cout << "scalarMultiplication1 failed" << endl;
        return false;
    }
}

bool scalarMultiplication2() {
    mymatrix<double> M1(2, 2); //create a 2x2 matrix
    mymatrix<double> M2;
    M1(0,0) = 1;
    M1(0,1) = 2;
    M1.at(1,0) = 3;
    M1.at(1,1) = 4;

    M2 = M1 * 10;
    if (M2(0,0) == 10 && M2(0,1) == 20 && M2.at(1,0) == 30 && M2.at(1,1) == 40) { //should 10x the value at each index
        return true;
    }
    else {
        cout << "scalarMultiplication2 failed" << endl;
        return false;
    }
}

bool scalarMultiplication3() {
    mymatrix<float> M1(3, 6); //create a 3x6 matrix
    mymatrix<float> M2;
    M1.growcols(2, 8);
    M1.at(1, 3) = 2;
    M1.at(2, 7) = 4;

    M2 = M1 * 100;
    if (M2(1,3) == 200 && M2(2,7) == 400) { //should work with jagged matrices
        return true;
    }
    else {
        cout << "scalarMultiplication3 failed" << endl;
        return false;
    }
}

/* The next 5 functions are tests for the following member function of the mymatrix class:
*  The matrix multiplication function. These functions test the functionality of this
*  member function and makes sure it works in the intended way. More specifics for each 
*  test case are given inline in each of the functions. All the functions have no 
*  paremeters, and return a boolean value.
*/
bool matrixMultiplication1() {
    mymatrix<int> M1(2,2); //create 2x2 matrix
    mymatrix<int> M2(2,2);
    mymatrix<int> M3;
    //initialize the indices in each matrix
    M1(0,0) = 3;
    M1(0,1) = 4;
    M1(1,0) = 6;
    M1(1,1) = 5;

    M2(0,0) = 2;
    M2(0,1) = 5;
    M2(1,0) = 8;
    M2(1,1) = 10;

    M3 = M1 * M2; //if the columns of M1 and rows of M2 are equal, matrix multiplication can occur
    if (M3.at(0,0) == 38 && M3.at(0,1) == 55 && M3.at(1,0) == 52 && M3.at(1,1) == 80) { //matrix multiplication should work with square matrices
        return true;
    }
    else {
        cout << "matrixMultiplication1 failed" << endl;
        return false;
    }
}

bool matrixMultiplication2() {
    mymatrix<double> M1(1,2); //create 1x2 matrix
    mymatrix<double> M2(2,1); //2x1 matrix
    mymatrix<double> M3;
    //initialize the indices in each matrix
    M1(0,0) = 6;
    M1(0,1) = 7;

    M2(0,0) = 3;
    M2(1,0) = 9;

    M3 = M1 * M2; //if the columns of M1 and rows of M2 are equal, matrix multiplication can occur
    if (M3.at(0,0) == 81) { //matrix multiplication should work with rectangular matrices
        return true;
    }
    else {
        cout << "matrixMultiplication2 failed" << endl;
        return false;
    }
}

bool exceptionTestMatrixMultiply1() {
  try {
    mymatrix<long>  M1(3, 2); //create 3x2 matrix
    mymatrix<long>  M2(2, 3); //create 2x3 matrix
    mymatrix<long> M3;

    M1.growcols(1, 4);
    M3 = M1 * M2;  //because the columns of M1 and rows of M2 are not equal, this should throw a size mismatch

    cout << "exceptionTestMatrixMultiply1 failed, jagged this matrix did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true; 
  }
}

bool exceptionTestMatrixMultiply2() {
  try {
    mymatrix<float>  M1(4, 3); //create 4x3 matrix
    mymatrix<float>  M2(3, 4); //create 3x4 matrix
    mymatrix<float> M3;

    M2.growcols(2, 8);
    M3 = M1 * M2;  //because the columns of M1 and rows of M2 are not equal, this should throw a size mismatch

    cout << "exceptionTestMatrixMultiply2 failed, jagged other matrix did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true; 
  }
}

bool exceptionTestMatrixMultiply3() {
  try {
    mymatrix<double>  M1(6, 5); //create 6x5 matrix
    mymatrix<double>  M2(6, 5); //create 6x5 matrix
    mymatrix<double> M3;

    M3 = M1 * M2;  //because the columns of M1 and rows of M2 are not equal, this should throw a size mismatch

    cout << "exceptionTestMatrixMultiply3 failed, size mismatch did not throw exception" << endl;
    return false;
  }
  catch (...) {
    return true; 
  }
}

int main() {
    //create a passed and failed counter
    int passed = 0;
    int failed = 0;

    //the following 6 ternary operators check whether the test cases for the default constructor,
    //parameterized constructor, copy constructor, and size function work correctly
    (DCAndCC()) ? passed++ : failed++;
    (PCAndCC()) ? passed++ : failed++;
    (sizeAndDC()) ? passed++ : failed++;
    (sizeAndPC1()) ? passed++ : failed++;
    (sizeAndPC2()) ? passed++ : failed++;
    (sizeAndPC3()) ? passed++ : failed++;

    //the following 7 ternary operators check whether the test cases for the paramterized constructor
    //and operator() work correctly, as well as if their exceptions work correctly
    (PCAndOperatorParentheses()) ? passed++ : failed++;
    (CCAndOperatorParentheses1()) ? passed++ : failed++;
    (CCAndOperatorParentheses2()) ? passed++ : failed++;
    (exceptionTestPC1()) ? passed++ : failed++;
    (exceptionTestPC2()) ? passed++ : failed++;
    (exceptionTestParentheses1()) ? passed++ : failed++;
    (exceptionTestParentheses2()) ? passed++ : failed++;

    //the following 4 ternary operators check whether the test cases for the default constructor, 
    //paramterized constructor, and numrows() function work correctly, as well as their exceptions
    (numrowsAndDC()) ? passed++ : failed++;
    (numrowsAndPC()) ? passed++ : failed++;
    (numcolsAndDC()) ? passed++ : failed++;
    (exceptionTestNumcols()) ? passed++ : failed++;
    
    //the following 3 ternary operators check whether the test cases for the numcols() and
    //growcols() functions work correctly, as well as their exceptions
    (numcolsAndGrowcols()) ? passed++ : failed++;
    (exceptionTestGrowcols1()) ? passed++ : failed++;
    (exceptionTestGrowcols2()) ? passed++ : failed++;

    //the following 5 ternary operators check whether the test cases for the numcols(),
    //growcols(), and grow() functions work correctly, as well as their exceptions
    (numcolsAndGrow()) ? passed++ : failed++;
    (numrowsAndGrow()) ? passed++ : failed++;
    (growRowsAndColumns()) ? passed++ : failed++;
    (exceptionTestGrow1()) ? passed++ : failed++;
    (exceptionTestGrow2()) ? passed++ : failed++;

    //the following 4 ternary operators check whether the test cases for the paramterized constructor,
    //copy constructor, and operator.at() work correctly, as well as if their exceptions work correctly
    (PCAndOperatorAt()) ? passed++ : failed++;
    (CCAndOperatorAt()) ? passed++ : failed++;
    (exceptionTestAt1()) ? passed++ : failed++;
    (exceptionTestAt2()) ? passed++ : failed++;

    //the following 3 ternary operators check whether the scalar multiplication function works correctly
    (scalarMultiplication1()) ? passed++ : failed++;
    (scalarMultiplication2()) ? passed++ : failed++;
    (scalarMultiplication3()) ? passed++ : failed++;

    //the following 5 ternary operators check whether the test cases for matrix multiplication work correctly,
    //as well as if the exceptions for matrix multiplication work correctly
    (matrixMultiplication1()) ? passed++ : failed++;
    (matrixMultiplication2()) ? passed++ : failed++;
    (exceptionTestMatrixMultiply1()) ? passed++ : failed++;
    (exceptionTestMatrixMultiply2()) ? passed++ : failed++;
    (exceptionTestMatrixMultiply3()) ? passed++ : failed++;

    //output how many test cases passed and failed
    cout << "Tests Passed: " << passed << endl;
    cout << "Tests Failed: " << failed << endl;
    return 0;
}