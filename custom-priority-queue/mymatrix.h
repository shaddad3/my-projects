/*mymatrix.h*/

// CS 251: Program 3
// Student Author: Sammy Haddad
// Short Description: Create a matrix class 
// that is a 2D array, and allows the user to
// perform matrix calculations and operations. 
// The matrix class is unique in that it allows
// the creation of jagged matrices 
// TA's that helped: Harshal, Khushboo 
//
/// Assignment details and provided code are created and
/// owned by Adam T Koehler, PhD - Copyright 2023.
/// University of Illinois Chicago - CS 251 Spring 2023


//
// mymatrix
//
// The mymatrix class provides a matrix (2D array) abstraction.
// The size can grow dynamically in both directions (rows and 
// cols).  Also, rows can be "jagged" --- i.e. rows can have 
// different column sizes, and thus the matrix is not necessarily 
// rectangular.  All elements are initialized to the default value
// for the given type T.  Example:
//
//   mymatrix<int>  M;  // 4x4 matrix, initialized to 0
//   
//   M(0, 0) = 123;
//   M(1, 1) = 456;
//   M(2, 2) = 789;
//   M(3, 3) = -99;
//
//   M.growcols(1, 8);  // increase # of cols in row 1 to 8
//
//   for (int r = 0; r < M.numrows(); ++r)
//   {
//      for (int c = 0; c < M.numcols(r); ++c)
//         cout << M(r, c) << " ";
//      cout << endl;
//   }
//
// Output:
//   123 0 0 0
//   0 456 0 0 0 0 0 0
//   0 0 789 0
//   0 0 0 -99
//

#pragma once

#include <iostream>
#include <exception>
#include <stdexcept>

using namespace std;

template<typename T>
class mymatrix
{
private:
  struct ROW
  {
    T*  Cols;     // dynamic array of column elements
    int NumCols;  // total # of columns (0..NumCols-1)
  };

  ROW* Rows;     // dynamic array of ROWs
  int  NumRows;  // total # of rows (0..NumRows-1)

public:
  //
  // default constructor:
  //
  // Called automatically by C++ to construct a 4x4 matrix.  All 
  // elements are initialized to the default value of T.
  //
  mymatrix()
  {
    Rows = new ROW[4];  // an array with 4 ROW structs:
    NumRows = 4;

    // initialize each row to have 4 columns:
    for (int r = 0; r < NumRows; ++r)
    {
      Rows[r].Cols = new T[4];  // an array with 4 elements of type T:
      Rows[r].NumCols = 4;

      // initialize the elements to their default value:
      for (int c = 0; c < Rows[r].NumCols; ++c)
      {
        Rows[r].Cols[c] = T{};  // default value for type T:
      }
    }
  }

  //
  // parameterized constructor:
  //
  // Called automatically by C++ to construct a matrix with R rows, 
  // where each row has C columns. All elements are initialized to 
  // the default value of T.
  //
  mymatrix(int R, int C)
  {
    if (R < 1)
      throw invalid_argument("mymatrix::constructor: # of rows");
    if (C < 1)
      throw invalid_argument("mymatrix::constructor: # of cols");

    Rows = new ROW[R]; //create an array with R ROW structs
    NumRows = R; 

    //initialize each row to have C columns
    for (int i = 0; i < NumRows; i++) {
      Rows[i].Cols = new T[C];
      Rows[i].NumCols = C;

      //initialize the elements to their default value
      for (int j = 0; j < Rows[i].NumCols; j++) {
        Rows[i].Cols[j] = T{};
      }
    }
  }

  //
  // copy constructor:
  //
  // Called automatically by C++ to construct a matrix that contains a 
  // copy of an existing matrix.  Example: this occurs when passing 
  // mymatrix as a parameter by value
  //
  //   void somefunction(mymatrix<int> M2)  <--- M2 is a copy:
  //   { ... }
  //
  mymatrix(const mymatrix<T>& other)
  {
    
    //allocate this matrix with same rows as the other
    Rows = new ROW[other.NumRows];
    NumRows = other.NumRows;

    //copy the elements from the other matrix into this matrix
    for (int i = 0; i < other.NumRows; i++) { 
      Rows[i].Cols = new T[other.Rows[i].NumCols];
      Rows[i].NumCols = other.Rows[i].NumCols;
      
      for (int j = 0; j < other.Rows[i].NumCols; j++) {
        Rows[i].Cols[j] = other.Rows[i].Cols[j];
      }
    }
  }

  //
  // numrows
  //
  // Returns the # of rows in the matrix.  The indices for these rows
  // are 0..numrows-1.
  //
  int numrows() const
  {
    return NumRows;
  }
  
  //
  // numcols
  //
  // Returns the # of columns in row r.  The indices for these columns
  // are 0..numcols-1.  Note that the # of columns can be different 
  // row-by-row since "jagged" rows are supported --- matrices are not
  // necessarily rectangular.
  //
  int numcols(int r) const
  {
    if (r < 0 || r >= NumRows)
      throw invalid_argument("mymatrix::numcols: row");
    
    return Rows[r].NumCols;
  }

  //
  // growcols
  //
  // Grows the # of columns in row r to at least C.  If row r contains 
  // fewer than C columns, then columns are added; the existing elements
  // are retained and new locations are initialized to the default value 
  // for T.  If row r has C or more columns, then all existing columns
  // are retained -- we never reduce the # of columns.
  //
  // Jagged rows are supported, i.e. different rows may have different
  // column capacities -- matrices are not necessarily rectangular.
  //
  void growcols(int r, int C) 
  {
    if (r < 0 || r >= NumRows)
      throw invalid_argument("mymatrix::growcols: row");
    if (C < 1)
      throw invalid_argument("mymatrix::growcols: columns");
      
    if (Rows[r].NumCols >= C) { //we have C or more columns in row r
      return;
    }

    //make a new row struct, and allocate it with C columns
    ROW* newRow;
    newRow = new ROW[1];
    newRow[0].Cols = new T[C]; 
    newRow[0].NumCols = C;

    // copy the elements in row r to the new row, if no values are left, intiialize to default value
    for (int j = 0; j < C; j++) { 
      if (j < Rows[r].NumCols) {
        newRow[0].Cols[j] = Rows[r].Cols[j];
        continue;
      }
      newRow[0].Cols[j] = T{};  // default value for type T
    }

    Rows[r].Cols = newRow[0].Cols;
    Rows[r].NumCols = newRow[0].NumCols;
  }

  //
  // grow
  //
  // Grows the size of the matrix so that it contains at least R rows,
  // and every row contains at least C columns.
  // 
  // If the matrix contains fewer than R rows, then rows are added
  // to the matrix; each new row will have C columns initialized to 
  // the default value of T.  If R <= numrows(), then all existing
  // rows are retained -- we never reduce the # of rows.
  //
  // If any row contains fewer than C columns, then columns are added
  // to increase the # of columns to C; existing values are retained
  // and additional columns are initialized to the default value of T.
  // If C <= numcols(r) for any row r, then all existing columns are
  // retained -- we never reduce the # of columns.
  // 
  void grow(int R, int C)
  {
    if (R < 1)
      throw invalid_argument("mymatrix::grow: # of rows");
    if (C < 1)
      throw invalid_argument("mymatrix::grow: # of cols");


    if (NumRows >= R) { //we have R or more rows
      for (int i = 0; i < NumRows; i++) { //grow all existing rows to have C columns
        growcols(i, C);
      }
      return;
    }

    mymatrix<T> temp(R, C); //create a temporary matrix of size (R, C)

    for (int i = 0; i < NumRows; i++) {
      for (int j = 0; j < C; j++) { 
        if (j < Rows[i].NumCols) { //copy over the existing elements into our temporary matrix
          temp.Rows[i].Cols[j] = Rows[i].Cols[j];
          continue;
        }
        temp.Rows[i].Cols[j] = T{};  // default value for type T
      }
    }

    Rows = temp.Rows;
    NumRows = R;
  }

  //
  // size
  //
  // Returns the total # of elements in the matrix.
  //
  int size() const
  {
    int count = 0;
    for (int i = 0; i < numrows(); i++) { //go through the number of rows and add up the number of columns in each row
      count += Rows[i].NumCols;
    }

    return count;
  }

  //
  // at
  //
  // Returns a reference to the element at location (r, c); this
  // allows you to access the element or change it:
  //
  //    M.at(r, c) = ...
  //    cout << M.at(r, c) << endl;
  //
  T& at(int r, int c) const
  {
    if (r < 0 || r >= NumRows)
      throw invalid_argument("mymatrix::at: row");
    if (c < 0 || c >= Rows[r].NumCols)
      throw invalid_argument("mymatrix::at: col");

    return Rows[r].Cols[c]; //returns the index at (r, c)
  }

  //
  // ()
  //
  // Returns a reference to the element at location (r, c); this
  // allows you to access the element or change it:
  //
  //    M(r, c) = ...
  //    cout << M(r, c) << endl;
  //
  T& operator()(int r, int c) const
  {
    if (r < 0 || r >= NumRows)
      throw invalid_argument("mymatrix::(): row");
    if (c < 0 || c >= Rows[r].NumCols)
      throw invalid_argument("mymatrix::(): col");

    return Rows[r].Cols[c]; //returns the index at (r, c)
  }

  //
  // scalar multiplication
  //
  // Multiplies every element of this matrix by the given scalar value,
  // producing a new matrix that is returned.  "This" matrix is not
  // changed.
  //
  // Example:  M2 = M1 * 2;
  //
  mymatrix<T> operator*(T scalar)
  {
    mymatrix<T> result = *this; //make a copy of "this" matrix

    for (int i = 0; i < NumRows; i++) { 
      for (int j = 0; j < Rows[i].NumCols; j++) { //go through all rows and columns and multipy each element by the scalar
        result.Rows[i].Cols[j] = scalar * result.Rows[i].Cols[j];
      }
    }

    return result;
  }

  //
  // matrix multiplication
  //
  // Performs matrix multiplication M1 * M2, where M1 is "this" matrix and
  // M2 is the "other" matrix.  This produces a new matrix, which is returned.
  // "This" matrix is not changed, and neither is the "other" matrix.
  //
  // Example:  M3 = M1 * M2;
  //
  // NOTE: M1 and M2 must be rectangular, if not an exception is thrown.  In
  // addition, the sizes of M1 and M2 must be compatible in the following sense:
  // M1 must be of size RxN and M2 must be of size NxC.  In this case, matrix
  // multiplication can be performed, and the resulting matrix is of size RxC.
  //
  mymatrix<T> operator*(const mymatrix<T>& other)
  {
    for (int i = 0; i < NumRows; i++) { //checking if "this" matrix is not jagged
      if (Rows[0].NumCols != Rows[i].NumCols) {
        throw runtime_error("mymatrix::*: this not rectangular");
      }
    }

    for (int i = 0; i < other.NumRows; i++) { //checking if "other" matrix is not jagged
      if (other.Rows[0].NumCols != other.Rows[i].NumCols) {
        throw runtime_error("mymatrix::*: other not rectangular");
      }
    }

    if (Rows->NumCols != other.NumRows) { //checking if there is a size mismatch
      throw runtime_error("mymatrix::*: size mismatch");
    }

    mymatrix<T> result(NumRows, other.Rows->NumCols); //make the dimensions of the matrix

    for (int i = 0; i < NumRows; i++) { //go thru rows of "this" matrix
      for (int j = 0; j < other.Rows->NumCols; j++) { //go thru columns of "other" matrix
        for (int k = 0; k < other.NumRows; k++) { //go thru rows of "other" matrix, or thru columns of "this" matrix (they're the same)
          result.Rows[i].Cols[j] += Rows[i].Cols[k] * other.Rows[k].Cols[j];
        }
      }
    }

    return result;
  }

  //
  // _output
  //
  // Outputs the contents of the matrix; for debugging purposes.
  //
  void _output()
  {
    for (int r = 0; r < this->NumRows; ++r)
    {
      for (int c = 0; c < this->Rows[r].NumCols; ++c)
      {
        cout << this->Rows[r].Cols[c] << " ";
      }
      cout << endl;
    }
  }

};
