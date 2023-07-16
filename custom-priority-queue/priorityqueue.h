/// @file priorityqueue.h
///
/// CS 251: Program 5
/// Student Author: Sammy Haddad
/// Short Description: create a priority queue class that
/// uses a BST as the underlying implementation. The 
/// BST is a custom BST, as it allows duplicates unlike 
/// a typical BST. The priority queue class allows the user
/// to perform basic queue functions, such as enqueue, dequeue, 
/// and other functions that are given in detail below. 
/// TA's that helped: Harshal, Nandana, Khushboo, Anurag, Allan, Sahas
///
///
/// Assignment details and provided code are created and
/// owned by Adam T Koehler, PhD - Copyright 2023.
/// University of Illinois Chicago - CS 251 Spring 2023
///

#pragma once

#include <iostream>
#include <sstream>
#include <set>

using namespace std;

template<typename T>
class priorityqueue {
private:
    struct NODE {
        int priority;  // used to build BST
        T value;  // stored data for the p-queue
        bool dup;  // marked true when there are duplicate priorities
        NODE* parent;  // links back to parent
        NODE* link;  // links to linked list of NODES with duplicate priorities
        NODE* left;  // links to left child
        NODE* right;  // links to right child
    };
    NODE* root;  // pointer to root node of the BST
    int size;  // # of elements in the pqueue
    NODE* curr;  // pointer to next item in pqueue (see begin and next)

    // 
    // convertValue() -- takes a templated value and converts it 
    // into a string, then returns this string to the helper 
    // function _toString()
    //
    string convertValue(T value) {
        string str;
        stringstream convert;
        convert << value;
        convert >> str;
        return str;
    }
    
    //
    // _toString() -- takes the root of the tree and a string that is
    // passed by reference and recursively concatenates the string in 
    // an in order traversal of the priority queue.
    //
    void _toString(NODE* curr, string &str) {
        if (curr == NULL) {
            return ;
        }
        else {
            //perform an in order traversal, and then concatenate the string until we are out of nodes
            _toString(curr->left, str);
            str += to_string(curr->priority) + " value: " + convertValue(curr->value) + "\n";
            _toString(curr->link, str);
            _toString(curr->right, str);
        }
    }

    //
    // _lowestPriority() -- takes the root of the tree and traverses
    // as far left in the tree as possible, and then returns a node
    // that points to this far left node.
    //
    NODE* _lowestPriority(NODE* curr) {
        while (curr->left != NULL) {
            curr = curr->left;
        }
        return curr;
    }

    //
    // _makeCopy() -- makes "this" tree a copy of the passed 
    // in tree by recursively traversing the "other" tree and 
    // calling enqueue() on "this" tree for every node in the
    // "other" tree. Enqueue() will make a new node for each
    // node in the tree, producing a tree which is equal to
    // "other" but that has its own memory allocations. 
    //
    void _makeCopy(NODE* curr) {
        if (curr == NULL) {
            return;
        }
        else {
            //create a copy of each node in the other tree by calling enqueue()
            //on "this" tree, and traversing the tree in a pre order fashion
            this->enqueue(curr->value, curr->priority);
            _makeCopy(curr->left);
            _makeCopy(curr->right);
            _makeCopy(curr->link);
        }
    }

    //
    // _clear() -- recursively deletes all the nodes in
    // the tree and makes the queue completely empty. 
    //
    void _clear(NODE* curr) {
        if (curr == NULL) {
            return;
        }
        else {
            //perform a post order traversal to recursively
            //delete each node from the tree
            _clear(curr->left);
            _clear(curr->right);
            _clear(curr->link);
            delete curr;
        }
    }

    //
    // _equalTrees() -- takes "this" root node and the "other" tree's 
    // root node, and check if theyre equal by seeing if all nodes hold 
    // the same value, are the same size, and are the same shape. If so,
    // returns true, otherwise returns false.
    //
    bool _equalTrees(NODE* thisTree, NODE* otherTree) const {
        if (thisTree == NULL && otherTree == NULL) { //base case: both nodes empty
            return true;
        }
        else if (thisTree == NULL) {//base case: "this" tree empty, but "other" is not
            return false;
        }
        else if (otherTree == NULL) {//base case: "other" tree empty, but "this" is not
            return false;
        }
        else { //perform a pre order traversal of the tree to see if the trees are equal
            if ((thisTree->value == otherTree->value) && 
            (_equalTrees(thisTree->left, otherTree->left)) && 
            (_equalTrees(thisTree->right, otherTree->right)) &&
            (_equalTrees(thisTree->link, otherTree->link))) {
                return true;
            }
            else {
                return false;
            }
        }
    }

public:
    //
    // default constructor:
    //
    // Creates an empty priority queue.
    // O(1)
    //
    // priorityqueue() -- default constructor for our 
    // priority queue class, which makes a queue that is 
    // empty, with both the root and curr nodes being set
    // to NULL, and the size being 0, as the tree has no nodes. 
    //
    priorityqueue() {

        //set all private variables to their initial values
        this->root = NULL;
        this->curr = NULL;
        this->size = 0;

    }
    
    //
    // operator=
    //
    // Clears "this" tree and then makes a copy of the "parent" tree.
    // Sets all member variables appropriately.
    // O(n), where n is total number of nodes in custom BST
    //
    // operator= -- creates a copy of the priority queue that is passed
    // in as a parameter. First we must clear "this" tree before making
    // the copy, and then we can create the copy by sending the passed
    // in tree to a helper function called _makeCopy(), which makes "this" 
    // tree a deep copy of the passed in tree. 
    //
    priorityqueue& operator=(const priorityqueue& parent) {
        
        clear(); //clear "this" tree
        this->root = NULL;
        _makeCopy(parent.root); //make "this" a deep copy of "parent"
        this->size = parent.size;
        return *this;
        
    }
    
    //
    // clear:
    //
    // Frees the memory associated with the priority queue but is public.
    // O(n), where n is total number of nodes in custom BST
    //
    // clear() -- the public member function that allows the user to 
    // empty the priority queue, deleting all the nodes in the tree and 
    // making the queue completely empty. 
    //
    void clear() {

        //clear "this" tree
        _clear(this->root);
        //set all private variables to their initial values
        this->root = NULL;
        this->curr = NULL;
        this->size = 0;
        
    }
    
    //
    // destructor:
    //
    // Frees the memory associated with the priority queue.
    // O(n), where n is total number of nodes in custom BST
    //
    // ~priorityqueue() -- the destructor for our priority queue
    // class, where we delete all the nodes from the tree and make
    // the queue completely empty.
    //
    ~priorityqueue() {
        
        clear(); //clear "this" tree

    }
    
    //
    // enqueue:
    //
    // Inserts the value into the custom BST in the correct location based on
    // priority.
    // O(logn + m), where n is number of unique nodes in tree and m is number 
    // of duplicate priorities
    //
    // enqueue() -- add a new node to the priority queue with the value and 
    // priority given. Based on the priority value given, this node will be 
    // inserted in the way a regular BST is created, with the addition of
    // duplicate nodes. If we have duplicates, we create a linked list of these
    // duplicates and add them to the tree, with the first occurrence of the
    // duplicates acting as the "head" of this linked list.
    // 
    void enqueue(T value, int priority) {

        NODE* prev = NULL;
        NODE* copy = root;

        //allocate the node to be inserted, and set its values
        NODE* add = new NODE();
        add->priority = priority;
        add->value = value;
        add->left = NULL;
        add->link = NULL;
        add->right = NULL;

        //search for where to insert the new node in the tree
        while (copy != NULL) {
            if (priority == copy->priority) { //new node is a duplicate priority value
                add->parent = copy->parent;
                add->dup = true;
                while (copy->link != NULL) {
                    copy = copy->link;
                }
                copy->link = add;
                this->size++;
                return; 
            }
            
            if (priority < copy->priority) { //new node has lower priority value
                prev = copy;
                copy = copy->left;
            }

            else { //new node has higher priority value
                prev = copy;
                copy = copy->right;
            }
        }

        if (prev == NULL) { //no nodes in the tree yet
            this->root = add;
        }

        else if (priority < prev->priority) { //new node has lower priority value
            prev->left = add;
        }

        else { //new node has lower priority value
            prev->right = add;
        }

        add->parent = prev;
        add->dup = false;
        this->size++;
        
    }
    //
    // dequeue:
    //
    // returns the value of the next element in the priority queue and removes
    // the element from the priority queue.
    // O(logn + m), where n is number of unique nodes in tree and m is number 
    // of duplicate priorities
    //
    // dequeue() -- returns the value of the next in order node in the tree, 
    // and then removes this node from the tree. Before removing the node, we
    // re-connect the tree by making sure to keep track of all the nodes that
    // this node is connected to.
    //
    T dequeue() {
        
        T valueOut;
        NODE* temp = this->root;
        NODE* prev = NULL;

        if (temp == NULL) { //no nodes in the tree
            return T{};
        }

        while (temp->left != NULL) { //get the lowest priority node
            prev = temp;
            temp = temp->left;
        }

        valueOut = temp->value;

        //delete the node from the tree, but re-connect the tree properly beforehand
        if (temp->link != NULL) {  //if duplicate node exists, this will replace the temp node
            if (temp == this->root) { //the temp is the root, so set the root to hold all values of the temp's link
                this->root = temp->link;
                this->root->parent = NULL;
                this->root->left = temp->left;
                this->root->right = temp->right;
                if (this->root->link == NULL) {
                    this->root->dup = false;
                }
            }
            else { //set the next duplicate to hold all values of the temp node
                temp->link->left = temp->left;
                temp->link->right = temp->right; 
                prev->left = temp->link;
                if (temp->link->link == NULL) {
                    temp->link->dup = false;
                }
            }
        }

        
        else { //if there are no duplicates
            if (temp == this->root) { //if we are at the root, set the new root to be the right child
                this->root = temp->right;
            }
            else { //set the prev node (which is temp's parent) to have a left child of temp's right child
                prev->left = temp->right;
            }
        }

        //completely detach temp from the tree by setting all its pointers to NULL, then delete it
        temp->link = NULL;
        temp->left = NULL;
        temp->right = NULL;
        temp->parent = NULL;
        delete temp;
        this->size--;
        return valueOut;

    }
    
    //
    // Size:
    //
    // Returns the # of elements in the priority queue, 0 if empty.
    // O(1)
    //
    // Size() -- return the size of our priority queue, which is the 
    // number of elements, or nodes, in our tree. 
    //
    int Size() {
        
        
        return this->size;
        
        
    }
    
    //
    // begin
    //
    // Resets internal state for an inorder traversal.  After the
    // call to begin(), the internal state denotes the first inorder
    // node; this ensure that first call to next() function returns
    // the first inorder node value.
    //
    // O(logn), where n is number of unique nodes in tree
    //
    // Example usage:
    //    pq.begin();
    //    while (tree.next(value, priority)) {
    //      cout << priority << " value: " << value << endl;
    //    }
    //    cout << priority << " value: " << value << endl;
    //
    // begin() -- we set the curr pointer of our tree to be the 
    // first in order node in our tree, meaning the node as far 
    // left in the tree as we can go using a helper function 
    // _lowestPriority(). This function sets up the function next().
    //
    void begin() {
        
        //get the farthest left node, and set it equal to curr
        NODE* temp = _lowestPriority(this->root);
        this->curr = temp;
        
    }
    
    //
    // next
    //
    // Uses the internal state to return the next inorder priority, and
    // then advances the internal state in anticipation of future
    // calls.  If a value/priority are in fact returned (via the reference
    // parameter), true is also returned.
    //
    // False is returned when the internal state has reached null,
    // meaning no more values/priorities are available.  This is the end of the
    // inorder traversal.
    //
    // O(logn), where n is the number of unique nodes in tree
    //
    // Example usage:
    //    pq.begin();
    //    while (pq.next(value, priority)) {
    //      cout << priority << " value: " << value << endl;
    //    }
    //    cout << priority << " value: " << value << endl;
    //
    // next() -- using our curr pointer in our priority queue, we go through 
    // the tree and return the priority and value of the next in order node
    // through reference parameters, and then look for the next in order node
    // in the tree after this one. If we find this next in order node, we set
    // curr to point to it and return true, as we still have more nodes in the
    // tree, so a successive call to next will give the priority and value of the
    // next in order node. We continue until we can not set curr to another node,
    // in which case we return false. This does not alter the tree at all, as curr
    // has no effect on the root node. 
    //
    bool next(T& value, int &priority) {
        
        //if curr exists, get the value and priority of the curr node
        if (curr == NULL) {
            return false;
        }
        value = curr->value;
        priority = curr->priority;

        //find the next in order node, and set it equal to curr
        if (curr->link != NULL) { //if there are duplicates, the duplicate is next in order
            this->curr = curr->link;
            return true;
        }

        if (curr->parent == NULL) { //if curr->parent is NULL, set curr to the root node
            this->curr = this->root;

        }

        if (curr->dup) { //if we are at a duplicate node
            NODE* parentCurr = this->curr->parent;
            if (parentCurr->left != NULL) {
                if (parentCurr->left->priority == curr->priority) { //if the parent->left matches the curr->priority, set curr here
                this->curr = parentCurr->left;
                }
            }

            if (parentCurr->right != NULL) {
                if (parentCurr->right->priority == curr->priority) { //if the parent->right matches the curr->priority, set curr here
                this->curr = parentCurr->right;
                }
            }
        }

        if (curr->right != NULL) { //next node is the furthest left node in curr->right subtree
            NODE* temp = curr->right;
            while (temp->left != NULL) {
                temp = temp->left;
            }
            this->curr = temp;
        }

        else { //next in order node is an ancestor of curr
            NODE* temp = curr;
            while (temp->parent != NULL) { //traverse up the tree using parent until we find the next in order node
                NODE* parent = temp->parent;
                if (temp->priority < parent->priority) {
                    this->curr = parent;
                    return true;
                }
                temp = temp->parent;
            }
            //if temp->parent hits NULL, then there is no next in order node
            this->curr = NULL;
            return false;
        }

        return true; //return true if we found the next in order node

    }
    
    //
    // toString:
    //
    // Returns a string of the entire priority queue, in order.  Format:
    // "1 value: Ben
    //  2 value: Jen
    //  2 value: Sven
    //  3 value: Gwen"
    //
    // toString() -- we convert our priority queue into a string that contains
    // both the priority of each node, as well as its value. We make the string 
    // in a helper function called _toString() which takes the root of the tree
    // and the string that is going to represent the priority queue in order.
    //
    string toString() {
        
        string str = "";
        //recursively build a string equal to the in order traversal of the tree
        _toString(this->root, str);
        return str;
        
        
    }
    
    //
    // peek:
    //
    // returns the value of the next element in the priority queue but does not
    // remove the item from the priority queue.
    // O(logn + m), where n is number of unique nodes in tree and m is number 
    // of duplicate priorities
    //
    // peek() -- we go through our tree and return the value in the node that
    // is next in the priority queue, which means it is as far left in the tree
    // as we can go using a helper function _lowestPriority(). We do not remove 
    // anything or change the tree at all in this function.
    //
    T peek() {
        
        T valueOut;
        //go as far left in the tree as possible
        NODE* temp = _lowestPriority(this->root);
        valueOut = temp->value;
        return valueOut;
        
    }
    
    //
    // ==operator
    //
    // Returns true if this priority queue as the priority queue passed in as
    // parent.  parentwise returns false.
    // O(n), where n is total number of nodes in custom BST
    //
    // operator== -- we compare both trees by sending them to a recursive 
    // helper functon called _equalTrees(), which takes "this" root node and
    // the passed in tree's root node, and check if theyre equal by seeing if
    // all nodes hold the same value, are the same size, and are the same shape.
    //
    bool operator==(const priorityqueue& parent) const {
        
        //determine if two trees are equal based on if they hold same value, are the same size, and are the same shape.
        return (_equalTrees(this->root, parent.root));

    }
    
    //
    // getRoot - Do not edit/change!
    //
    // Used for testing the BST.
    // return the root node for testing.
    //
    void* getRoot() {
        return root;
    }
};
