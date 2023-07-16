/*-------------------------------------------
Program 3: TwentyFour Part Two
Course: CS 211, Spring 2023, UIC
System: Replit and VS Code
Student Author: Sammy Haddad
TA's that helped: Sumanth
------------------------------------------- */
#include <stdio.h>
#include <time.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

//struct that defines a node for our stack, has a variable to hold the data value, and a next pointer
struct NODE {
  char value;
  struct NODE* next;
};

//struct that defines our stack, only has a head, or top pointer
struct STACK {
  struct NODE* top;
};

/* precedenceOrder() -- takes two characters, one for the top char
*  at the top of the stack and the new char to be inserted, and
*  determines which has high precedence, following PEMDAS rules.
*  returns a boolean of true or false, depending if the char at the
*  top of the stack has a higher precedence or not.
*/
bool precedenceOrder(char stackTop, char element) {
  //if top of the stack is * or /, it will be of higher precedence regardless of what element is, so return true
  if (stackTop == '*' || stackTop == '/') {
    return true;
  }
  
  //if the above is false, then if element is * or /, it will be of higher precedence, so return true
  if (element == '*' || element == '/') {
    return false;
  }
  
  return true;
}

/* isParenthesis() -- takes a character for the top of the stack, 
*  and determines if that char is an opening parenthesis or not.
*  returns true if it is, false if not. 
*/
bool isParenthesis(char stackTop) {
  if (stackTop == '(') { //if top is an (, return true
    return true;
  }
  return false;
}

/* calculate() -- takes 3 characters, one operator, and two operands,
*  and performs the mathematical equivalent of whatever the operator
*  is on the two operands. Returns the result of this evaluation. 
*/
char calculate(char operation, char first, char second) {
  int result = 0;
  int numOne = first - '0';
  int numTwo = second - '0';

  //determine what the operator is, and perfrom the appropriate calculation between the two operators, and print out the calculation
  switch(operation) {
    case '+':
      result = numOne + numTwo;
      printf("%d %c %d = %d.\n", numOne, operation, numTwo, result);
      break;
    case '-':
      result = numOne - numTwo;
      printf("%d %c %d = %d.\n", numOne, operation, numTwo, result);
      break;
    case '*':
      result = numOne * numTwo;
      printf("%d %c %d = %d.\n", numOne, operation, numTwo, result);
      break;
    case '/':
      result = numOne / numTwo;
      printf("%d %c %d = %d.\n", numOne, operation, numTwo, result);
      break;
  }

  //return the result as a character, so we can add it to the stack
  return (result + '0');
}

/* push() -- used to add to the stack, which is represented as a 
*  linked list. Takes a stack and a character, and adds the char
*  to the top of the stack. 
*/
void push(struct STACK* stack, char element) {
  //allocate space for the new node
  struct NODE* temp = malloc(sizeof(struct NODE));
  temp->value = element;
  temp->next = NULL;

  //if top is NULL, then this is the first node on the stack
  if ((*stack).top == NULL) {
    (*stack).top = temp;
  }
  else { //if not, then make this the new first node on the stack
    temp->next = (*stack).top;
    (*stack).top = temp;
  }
}

/* pop() -- used to remove from the stack, which is represented as a 
*  linked list. Takes a stack, and removes and returns the char at the
*  top of the stack. 
*/
char pop(struct STACK* stack) {
  char element = ' ';
  struct NODE* temp = NULL;
  temp = (*stack).top;

  //store the char that is at the top of the stack, and then remove it from the stack by skipping over it in the linked list
  element = (*stack).top->value;
  (*stack).top = temp->next;
  temp->next = NULL;
  free(temp); //free the memory that held this node
  return element;
}

/* peek() -- used to see what is at the top of the stack without
*  altering the stack. Return the char at the top of the stack. 
*/
char peek(struct STACK* stack) {
  char element = ' ';
  element = (*stack).top->value;
  return element;
}

/* isEmpty() -- checks to see if a stack is empty or not. Returns true
*  is the stack is empty, else returns false. 
*/
bool isEmpty(struct STACK* stack) {
  if ((*stack).top == NULL) {
    return true;
  }
  return false;
}

/* readFile() -- fills the 2D array with all the puzzles corresponding
*  to a difficulty level of easy, medium, or hard. Read one of these
*  files, and fill the 2D array created in main. 
*/
void readFile(int puzzles[][4], char* difficultyLevel) {
  printf("Choose your difficulty level: E for easy, M for medium, and H for hard (default is easy). \nYour choice --> ");
  scanf(" %c", difficultyLevel);

  //determine the difficulty level, and open the corresponding file
  char filename[81];
  if (*difficultyLevel == 'E') {
    strcpy(filename, "easy.txt");
  }
  else if (*difficultyLevel == 'M') {
    strcpy(filename, "medium.txt");
  }
  else if (*difficultyLevel == 'H') {
    strcpy(filename, "hard.txt");
  }
  else {
    strcpy(filename, "easy.txt");
  }

  FILE *filePtr  = fopen(filename, "r"); // "r" means we open the file for reading
	// Check that the file was able to be opened
	if(filePtr == NULL) {
		printf("Error: could not open %s for reading\n", filename);
		exit(-1);
	}
  
  int num = 0;
  int row = 0;
  int col = 0;

  //read each number from the file and store them in the 2D array of puzzles
	while(fscanf(filePtr, "%d", &num) != EOF) {
    puzzles[row][col] = num;
    col++;

    if (col == 4) {
      row++;
      col = 0;
    }
	}

	// Close the file
	fclose(filePtr);
}

/* playGame() -- plays the game with the puzzles corresponding to 
*  a difficulty level. Pick a random puzzle from the chosen difficulty
*  and prompt the user for an expression. If a valid expression is 
*  given, evaluate it using a stack, which is implemented with a 
*  linked list. 
*/
void playGame(int puzzles[][4], char* difficultyLevel) {
  //determine the number of available puzzles to choose from
  int puzzleSize = 0;
  if (*difficultyLevel == 'E') {
    puzzleSize = 12;
  }
  else if (*difficultyLevel == 'M') {
    puzzleSize = 24;
  }
  else if (*difficultyLevel == 'H') {
    puzzleSize = 11;
  }
  else {
    puzzleSize = 12;
  }
  
  int random = rand() % puzzleSize; //pick a random puzzle
  char expression[81];
  int operandCount = 0;
  bool notNumber = false;
  bool notOperator = false;
  printf("The numbers to use are: %d, %d, %d, %d.", puzzles[random][0], puzzles[random][1], puzzles[random][2], puzzles[random][3]);
  
  printf("\nEnter your solution: ");
  fgets(expression, 81, stdin);
  if (strcmp(expression, "\n") == 0) {
    fgets(expression, 81, stdin);
  }

  int arr[4] = {puzzles[random][0], puzzles[random][1], puzzles[random][2], puzzles[random][3]};

  //go through the inputted expression and determine if the correct numbers were used, and if valid operators were given, if not, print an error message corresponding to the issue with the expression
  for (int i = 0; i < strlen(expression); i++) {
    
    if (isdigit(expression[i])) {
      operandCount++;
      //if there are more then 4 numbers, set an error flag for later
      if (operandCount > 4) {
        notNumber = true;
        break;
      }
      for (int j = 0; j < 4; j++) {
        //determine if all 4 numbers were used, and only used once
        if (arr[j] == atoi(&expression[i])) {
          arr[j] = -1;
          break;
        } 
      }
    }
      
    else {
      //determine if valid operators were inputted
      switch(expression[i]) {
        case '+': 
          break;
        case '-': 
          break;
        case '*': 
          break;
        case '/': 
          break;
        case '(': 
          break;
        case ')': 
          break;
        case ' ':
          break;
        case '\n':
          break;
        default:
          notOperator = true;
          break;
      }
    }
  }

  //if an invalid operator was given, output the error message and play the game again with the same difficulty
  if (notOperator) {
    printf("Error! Invalid symbol entered. Please try again.\n\n");
    playGame(puzzles, difficultyLevel);
    return;
  }

  //if an invalid operand was given, output the error message and play the game again with the same difficulty
  for (int i = 0; i < 4; i++) {
    if (notNumber) {
      printf("Error! You must use all four numbers, and use each one only once. Please try again.\n\n");
      playGame(puzzles, difficultyLevel);
      return;
    }
    if (arr[i] != -1) {
      printf("Error! You must use all four numbers, and use each one only once. Please try again.\n\n");
      playGame(puzzles, difficultyLevel);
      return;
    }
  }

  //make a stack to evaluate the expression
  struct STACK outputStack;
  outputStack.top = NULL;
  char string[81] = "";
  bool invalidParenthesis = false;

  //go through the entire expression, and convert the expression given in infix notation to an expression in postfix notation, and in the correct order based on PEMDAS rules
  for (int i = 0; i < strlen(expression); i++) {
    if (expression[i] == ' ') {
      continue;
    }

    //if the character is an operator, determine the precedence, if there is an opening parenthesis, and if the stack is empty or not, and push or pop the character accordingly
    if (expression[i] == '+' || expression[i] == '-' || expression[i] == '*' || expression[i] == '/') {
      
      while (!isEmpty(&outputStack) && !isParenthesis(outputStack.top->value) && precedenceOrder((peek(&outputStack)), expression[i])) {
        char stackTop = peek(&outputStack);
        strncat(string, &stackTop, 1);
        strncat(string, " ", 1);
        pop(&outputStack);
      }
      push(&outputStack, expression[i]);
    }

    //if the character is an opening parenthesis, push it onto the stack
    else if (expression[i] == '(') {
      push(&outputStack, expression[i]);
    }

    //if the character is a closing parenthesis, pop all the operators until the next opening parenthesis and add them to the postfix string
    else if (expression[i] == ')') {
      while (!isEmpty(&outputStack) && !isParenthesis(outputStack.top->value)) {
        char stackTop = peek(&outputStack);
        strncat(string, &stackTop, 1);
        strncat(string, " ", 1);
        pop(&outputStack);
      }

      //if there are too many closing parenthesis, set a flag to use later to output an error message
      if (isEmpty(&outputStack)) {
        invalidParenthesis = true;
        break;
      }
      
      pop(&outputStack);
    }

    //if the character is an operand, add it to the postfix string
    else if (isdigit(expression[i])) {
      strncat(string, &(expression[i]), 1);
      strncat(string, " ", 1);
    }
  }

  //after going through the entire expression, add all remaining operators to the postfix expression
  while (!isEmpty(&outputStack)) {
    char stackTop = peek(&outputStack);
    strncat(string, &stackTop, 1);
    strncat(string, " ", 1);
    pop(&outputStack);
  }

  //go through the postfix expression and evaluate it using a stack, and see if the expression yields a winning result
  for (int i = 0; i < strlen(string); i++) {
    if (string[i] == ' ') {
      continue;
    }

    //if the character is an operand, add it to the stack
    if (isdigit(string[i])) {
      push(&outputStack, string[i]);
    }

    //if the character is an operator, use it to calculate the postfix expression at this point in the string
    else if ((string[i] == '+' || string[i] == '-' || string[i] == '*' || string[i] == '/')) {
      char second = pop(&outputStack);
      char first = pop(&outputStack);
      
      //pop the two operands, and then calculate the result of these operands with the character at this index, which is an operator
      char result = calculate(string[i], first, second);
      push(&outputStack, result);
    }
  }

  //if a valid expression was given, the stack should only have one character left, the result of the expression, so check to see if this character is a winning result
  int win = 0;
  while (!isEmpty(&outputStack)) {
    //if the expression had too many closing braces, output the error message
    if (invalidParenthesis) {
      printf("Error! Too many closing parentheses in the expression.\n\n");
      while(!isEmpty(&outputStack)) { //free stack before returning
        pop(&outputStack);
      }
        return;
    }

    //convert the char at the end of the stack into an int
    char stackTop = pop(&outputStack);
    win = stackTop - '0';

    //if the stack is not empty after poping once, there are too many values in the expression, so output the following error message
    if (!isEmpty(&outputStack)) {
      char invalid = pop(&outputStack);
      if (isdigit(invalid)) {
        while(!isEmpty(&outputStack)) { //free stack before returning
          pop(&outputStack);
        }
        printf("Error! Too many values in the expression.\n\n");
        return;
      }
    }
  }

  if (win == 24) { //if win == 24, victory!
    printf("Well done! You are a math genius.\n\n");
  }
  else { //if not, failure :(
    printf("Sorry. Your solution did not evaluate to 24.\n\n");
  }
}

/* menuOptions() -- used to continue playing the game after one 
*  round. Allow the user of the choice to play the game again at
*  the same difficulty, play again at a different difficulty, or 
*  stop playing and exit the game.
*/
void menuOptions(int puzzles[][4], char* difficultyLevel) {
  int command = 0;
  while (true) {
    printf("Enter: \t1 to play again,\n");
  	printf("\t2 to change the difficulty level and then play again, or\n");
  	printf("\t3 to exit the program.\n");
  	printf("Your choice --> ");
    scanf("%d", &command);
    if (command == 1) { //play again at the same difficulty
      playGame(puzzles, difficultyLevel);
      continue;
    }
    if (command == 2) { //play again with a different difficulty
      readFile(puzzles, difficultyLevel);
      playGame(puzzles, difficultyLevel);
      continue;
    }
    if (command == 3) { //stop playing and exit the game
      printf("\nThanks for playing!\n");
	    printf("Exiting...\n");
      return;
    }
  }
}

/* main() -- used to run the game the first time. Output some info
*  about the game, and begin to play through the game by calling
*  readFile() to get the difficulty and corresponding puzzles, 
*  playGame() to get an expression and evaluate it to see if it is
*  a winning expression, and menuOptions() to allow the user to 
*  play the game as many times as they wish.
*/
int main() {
	srand(1); //for the autograder

  //print some info about the game and how it works to the user
	printf("Welcome to the game of TwentyFour Part Two!\n");
	printf("Use each of the four numbers shown exactly once, \n");
	printf("combining them somehow with the basic mathematical operators (+,-,*,/) \n");
	printf("to yield the value twenty-four.\n");
  
  char difficultyLevel;
  int puzzles[24][4];

  //play through the game once by calling readFile(), playGame(), and menuOptions()
  readFile(puzzles, &difficultyLevel);
  playGame(puzzles, &difficultyLevel);
  menuOptions(puzzles, &difficultyLevel);
	
	return 0;
}