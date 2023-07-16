/// @file tests.cpp
/// @date March 17, 2023

/// CS 251: Program 5
/// Student Author: Sammy Haddad
/// Short Description: test cases for our priority queue
/// that is made using a custom BST (see priorityqueue.h for
/// more details). The test cases test all the functions of 
/// the priority queue class, ensuring functions work correctly,
/// and that they work with different types  
/// TA's that helped: Harshal, Nandana, Khushboo, Anurag, Allan, Sahas

/// Provided testing file to implement framework based tests in. The examples
/// below demonstrates a basic empty test in each framework with a single
/// assertion. The version uses the supplied catch.hpp file in starter code.
///
/// Assignment details and provided code are created and
/// owned by Adam T Koehler, PhD - Copyright 2023.
/// University of Illinois Chicago - CS 251 Spring 2023

// TODO:
// Choose which framework to keep and delete the other.
//


// Catch 1.0 Framework Testing
#define CATCH_CONFIG_MAIN

#include "catch.hpp"
#include "priorityqueue.h"
#include <map>

using namespace std;

TEST_CASE("(0) no tests") 
{
    REQUIRE(true);
}

// PRIORITY QUEUE TEST CASE 1 -- tests the default constructor, size, 
// toString, and getRoot functions.
TEST_CASE("default constructor, toString, size, getRoot") {
    //create a priority queue, and make sure it is empty
    priorityqueue<string> pq;
    REQUIRE(pq.Size() == 0);
    REQUIRE(pq.toString() == "");
    REQUIRE(pq.getRoot() == NULL);
}

// PRIORITY QUEUE TEST CASE 2 -- tests the default constructor, size, 
// and enqueue functions.
TEST_CASE("default constructor, enqueue, size") {
    priorityqueue<double> pq; //create a priority queue
    int x = 0;
    int y = 0;
    for (int i = 1; i < 100; i++) {
        x = rand() % 10;
        y = rand() % 10;
        pq.enqueue(x, y); //enqueue 100 random elements and ensure they are added
        REQUIRE(pq.Size() == i); //ensure size returns the correct number of elements
    }
}

// PRIORITY QUEUE TEST CASE 3 -- tests the default constructor, size, 
// toString, and enqueue functions.
TEST_CASE("default constructor, enqueue, toString, size") {
    map<int, vector<int> > map;
    int n = 12;
    int vals[] = {10, 13, 12, 8, 1, 2, 5, 9, 11, 14, 13, 16};
    int prs[] = {4, 5, 3, 2, 4, 3, 5, 1, 6, 2, 7, 5};
    priorityqueue<int> pq; //create a priority queue
    for (int i = 0; i < n; i++) {
        pq.enqueue(vals[i], prs[i]); //enqueue all the values
        map[prs[i]].push_back(vals[i]);
    }
    REQUIRE(pq.Size() == 12); //ensure size returns the correct number of elements
    stringstream ss;
    for (auto e: map) {
        int priority = e.first;
        vector <int> values = e.second;
        for (size_t j = 0; j < values.size(); j++){ //create a stringstream of the priority and value at each node
            ss << priority << " value: " << values[j] << endl;
        }
    }
    REQUIRE(pq.toString() == ss.str()); //ensure toString correctly prints the priority queue

}

// PRIORITY QUEUE TEST CASE 4 -- tests the default constructor, size, 
// toString, dequeue, and enqueue functions.
TEST_CASE("default constructor, enqueue, dequeue, toString, size") {
    map<int, vector<float> > map;
    int n = 9;
    float vals[] = {15, 16, 17, 6, 7, 8, 9, 2, 1};
    int prs[] = {1, 2, 3, 2, 2, 2, 2, 3, 3};
    priorityqueue<float> pq; //create a priority queue
    for (int i = 0; i < n; i++) {
        pq.enqueue(vals[i], prs[i]); //enqueue all the values
        map[prs[i]].push_back(vals[i]);
    }
    REQUIRE(pq.Size() == 9); //ensure size returns the correct number of elements
    stringstream ss;
    for (auto e: map) {
        int priority = e.first;
        vector <float> values = e.second;
        for (size_t j = 0; j < values.size(); j++){ //create a stringstream of the priority and value at each node
            ss << priority << " value: " << values[j] << endl;
            
        }
    }
    REQUIRE(pq.toString() == ss.str()); //ensure toString correctly prints the priority queue

    for (auto e: map) {
        vector <float> values = e.second;
        for (size_t j = 0; j < values.size(); j++){
            REQUIRE(pq.dequeue() == values[j]); //ensure dequeue returns the correct value
            
        }
    }
    REQUIRE(pq.Size() == 0); //ensure dequeue properly removes the nodes
}

// PRIORITY QUEUE TEST CASE 5 -- tests the default constructor, size, 
// toString, operator=, and enqueue functions.
TEST_CASE("default constructor, enqueue, operator=, toString, size") {
    map<int, vector<string>> map;
    priorityqueue<string> pq; //create a priority queue
    int n = 6;
    int prs[] = {5, 4, 2, 8, 11, 6};
    vector<string> vals = {"Dolores", "Bernard", "Ford", "Arnold", "Carlos", "Jasmine"};

    for (int i = 0; i < n; i++) {
        pq.enqueue(vals[i], prs[i]); //enqueue all the values
        map[prs[i]].push_back(vals[i]);
    }
    REQUIRE(pq.Size() == n); //ensure size returns the correct number of elements

    stringstream ss;
    for (auto e: map) {
        int priority = e.first;
        vector<string> values = e.second;
        for (int j = 0; j < values.size(); j++) { //create a stringstream of the priority and value at each node
            ss << priority << " value: " << values[j] << endl;
        }
    }
    REQUIRE(pq.toString() == ss.str()); //ensure toString correctly prints the priority queue

    priorityqueue<string> pq2;
    pq2 = pq; //make pq2 a deep copy of pq
    REQUIRE(pq2.toString() == pq.toString()); //ensure they hold the same priority's and values
    REQUIRE(pq2.Size() == pq.Size()); //ensure the size's match

    pq2.enqueue("Change", 5); //ensure pq2 is a deep copy by changing it and making sure the changes aren't reflected back
    REQUIRE(pq2.toString() != pq.toString());
    REQUIRE(pq2.Size() != pq.Size());

}

// PRIORITY QUEUE TEST CASE 6 -- tests the default constructor, size, 
// toString, operator=, operator==, and enqueue functions.
TEST_CASE("default constructor, enqueue, operator=, operator==, toString, size") {
    map<int, vector<string>> map;
    priorityqueue<string> pq; //create a priority queue
    int n = 12;
    int prs[] = {8, 1, 2, 6, 3, 10, 7, 5, 8, 10, 2, 3};
    vector<string> vals = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    for (int i = 0; i < n; i++) {
        pq.enqueue(vals[i], prs[i]); //enqueue the values
        map[prs[i]].push_back(vals[i]);
    }
    REQUIRE(pq.Size() == n); //ensure the size is correct

    stringstream ss;
    for (auto e: map) {
        int priority = e.first;
        vector<string> values = e.second;
        for (int j = 0; j < values.size(); j++) { //create a stringstream to hold the priority and value of each node
            ss << priority << " value: " << values[j] << endl;
        }
    }
    REQUIRE(pq.toString() == ss.str()); //ensure toString correctly prints the priority queue

    priorityqueue<string> pq2;
    pq2 = pq; //make pq2 a deep copy of pq
    REQUIRE(pq2.toString() == pq.toString()); //ensure they hold the same priority's and values
    REQUIRE(pq2.Size() == pq.Size()); //ensure the size's match
    REQUIRE(true == (pq2 == pq)); //use the operator== function to show they are equa;

    pq2.enqueue("New", 6); //ensure pq2 is a deep copy by changing it and making sure the changes aren't reflected back
    REQUIRE(pq2.toString() != pq.toString());
    REQUIRE(pq2.Size() != pq.Size());
    REQUIRE(false == (pq2 == pq)); //use the operator== to show the changes aren't reflected back

}

// PRIORITY QUEUE TEST CASE 7 -- tests the default constructor, size, 
// toString, begin, next, and enqueue functions.
TEST_CASE("default constructor, begin, next, enqueue, size, toString") {
    priorityqueue<char> pq; //create a priority queue
    map<int, vector<char>> map;
    int n = 10;
    int prs[] = {6, 4, 3, 8, 10, 12, 2, 3, 6, 8};
    vector<char> vals = {'c', 'z', 'x', 'd', 'a', 'd', 'x', 'a', 'y', 'd'};

    for (int i = 0; i < n; i++) {
        pq.enqueue(vals[i], prs[i]); //enqueue the values
        map[prs[i]].push_back(vals[i]);
    }
    REQUIRE(pq.Size() == n); //ensure the size is correct

    stringstream ss;
    for (auto e: map) {
        int priority = e.first;
        vector<char> values = e.second;
        for (int j = 0; j < values.size(); j++) { //create a stringstream to hold the priority and value of each node
            ss << priority << " value: " << values[j] << endl;
        }
    }
    REQUIRE(pq.toString() == ss.str()); //ensure toString correctly prints the priority queue

    stringstream ss2;
    int pris = 0;
    char value = ' ';
    pq.begin();
    while (pq.next(value, pris)) { //create a stringstream to hold the priority and value of each node when using begin and next
        ss2 << pris << " value: " << value << endl;
    }
    ss2 << pris << " value: " << value << endl;

    REQUIRE(pq.toString() == ss2.str()); //ensure begin and next properly traverse the tree in the correct order
}

// PRIORITY QUEUE TEST CASE 8 -- tests the default constructor, size, 
// toString, begin, next, dequeue, and enqueue functions.
TEST_CASE("default constructor, enqueue, dequeue, begin, next, size, toString") {
    priorityqueue<double> pq; //create a priority queue
    map<int, vector<double>> map;
    int n = 15;
    int prs[] = {6, 4, 3, 8, 10, 12, 2, 3, 6, 8, 2, 1, 6, 4, 8};
    vector<double> vals = {3.2, 2.6, 1.4, 12.9, 11.3, 14.5, 5.5, 4.6, 7.8, 8.7, 6.7, 4.3, 10.8, 11.6, 20.3};

    for (int i = 0; i < n; i++) {
        pq.enqueue(vals[i], prs[i]); //enqueue the values
        map[prs[i]].push_back(vals[i]);
    }
    REQUIRE(pq.Size() == n); //ensure the size is correct

    stringstream ss;
    for (auto e: map) {
        int priority = e.first;
        vector<double> values = e.second;
        for (int j = 0; j < values.size(); j++) { //create a stringstream to hold the priority and value of each node
            ss << priority << " value: " << values[j] << endl;
        }
    }
    REQUIRE(pq.toString() == ss.str()); //ensure toString prints the priority queue properly

    int pris = 0;
    double value = 0;
    pq.begin();
    while (pq.next(value, pris)) { //ensure that dequeue and next both traverse the tree correctly
        REQUIRE(pq.dequeue() == value);
    }
    REQUIRE(pq.dequeue() == value);
}

// PRIORITY QUEUE TEST CASE 9 -- tests the default constructor, size, 
// clear, dequeue, and enqueue functions.
TEST_CASE("default constructor, enqueue, dequeue, clear, size") {
    priorityqueue<int> pq; //create a priority queue
    map<int, vector<int>> map;
    int n = 200;
    int vals = 0;
    int prs = 0;

    for (int i = 0; i < n; i++) {
        vals = rand() % 10;
        prs = rand() % 10;
        pq.enqueue(vals, prs); //enqueue the values
        map[prs].push_back(vals);
    }
    REQUIRE(pq.Size() == n);

    for (auto e: map) {
        vector<int> values = e.second;
        for (int j = 0; j < values.size(); j++) { //ensure dequeue traverses the tree correctly
            REQUIRE(pq.dequeue() == values[j]);
        }
    }
    pq.clear(); //clear the tree, and ensure it becomes empty after this
    REQUIRE(pq.Size() == 0);
    REQUIRE(pq.getRoot() == 0);
}

// PRIORITY QUEUE TEST CASE 10 -- tests the default constructor, size, 
// clear, dequeue, peek, getRoot, and enqueue functions.
TEST_CASE("default constructor, enqueue, dequeue, peek, clear, size, getRoot") {
    priorityqueue<int> pq; //create a priority queue
    map<int, vector<int>> map;
    int n = 100;
    int vals = 0;
    int prs = 0;

    for (int i = 0; i < n; i++) {
        vals = rand() % 50;
        prs = rand() % 50;
        pq.enqueue(vals, prs); //enqueue the values
        map[prs].push_back(vals);
    }
    REQUIRE(pq.Size() == n); //ensure the size is correct

    for (auto e: map) {
        vector<int> values = e.second;
        for (int j = 0; j < values.size(); j++) {
            REQUIRE(pq.peek() == pq.dequeue()); //ensure that peek and dequeue return the correct values
        }
    }
    pq.clear(); //clear the tree, and ensure it becomes empty after this
    REQUIRE(pq.Size() == 0);
    REQUIRE(pq.getRoot() == 0);
}

// PRIORITY QUEUE TEST CASE 11 -- tests the default constructor, size, 
// toString, clear, operator=, operator==, getRoot, and enqueue functions.
TEST_CASE("default constructor, enqueue, toString, size, operator=, operator==, clear, getRoot") {
    priorityqueue<double> pq; //create a priority queue
    int n = 100;
    double vals = 0;
    int prs = 0;

    for (int i = 1; i < n; i++) {
        vals = rand() % 50;
        prs = rand() % 50;
        pq.enqueue(vals, prs); //enqueue the values
        REQUIRE(pq.Size() == i);
    }
    REQUIRE(pq.Size() == n-1); //ensure the size is correct

    priorityqueue<double> pq2; //create another queue
    int k = 50;
    for (int j = 1; j < k; j++) {
        vals = rand() % 50;
        prs = rand() % 50;
        pq2.enqueue(vals, prs); //enqueue the values
        REQUIRE(pq2.Size() == j);
    }
    REQUIRE(pq2.Size() == k-1); //ensure the size is correct
    REQUIRE(false == (pq == pq2)); //use operator== to show the tree's are not equal

    pq2 = pq; //make the tree's equal by using the operator=
    REQUIRE(pq.toString() == pq2.toString());
    REQUIRE(pq.Size() == pq2.Size());
    REQUIRE(true == (pq == pq2)); //use operator== to show the trees are equal

    string str = pq.toString(); //save the queue's toString for later
    pq.clear(); //clear the tree, and ensure it is empty
    REQUIRE(pq.Size() == 0);
    REQUIRE(pq.getRoot() == NULL);
    REQUIRE(pq2.toString() == str); //show that the operator= was unaffected by clear
    REQUIRE(pq2.toString() != pq.toString());
    REQUIRE(false == (pq == pq2)); //use operator== to show the trees are not equal
}

// PRIORITY QUEUE TEST CASE 12 -- tests the default constructor, size, 
// clear, dequeue, peek, getRoot, operator=, operator==, and enqueue functions.
TEST_CASE("default constructor, enqueue, dequeue, operator=, operator==, peek, clear, size, getRoot") {
    priorityqueue<int> pq; //create a priority queue
    map<int, vector<int>> map;
    int n = 300;
    int vals = 0;
    int prs = 0;

    for (int i = 0; i < n; i++) {
        vals = rand() % 15;
        prs = rand() % 10;
        pq.enqueue(vals, prs); //enqueue the values
        map[prs].push_back(vals);
    }
    REQUIRE(pq.Size() == n); //ensure the size is correct

    priorityqueue<int> pq2; //create another priority queue
    pq2 = pq; //use operator= to make pq2 a deep copy
    REQUIRE(pq.toString() == pq2.toString());
    REQUIRE(pq.Size() == pq2.Size());
    REQUIRE(true == (pq == pq2)); //use operator== to show the trees are equal

    for (auto e: map) {
        vector<int> values = e.second;
        for (int j = 0; j < values.size(); j++) {
            REQUIRE(pq.peek() == pq.dequeue()); //ensure that peek and dequeue return the correct values
        }
    }
    REQUIRE(false == (pq == pq2)); //use operator== to show the trees are not equal

    pq.clear(); //clear the tree, and ensure it becomes empty after this
    REQUIRE(pq.Size() == 0);
    REQUIRE(pq.getRoot() == NULL);
    REQUIRE((pq2.toString() != pq.toString())); //use toString to show pq2 was unaffected by clearing pq
    REQUIRE(pq.Size() != pq2.Size());
    REQUIRE(false == (pq == pq2)); //use operator== to show the trees are not equal after clearing one, proving pq2 to be a deep copy
}

