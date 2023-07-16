/*-------------------------------------------
Program 2: Weaver
Course: CS 211, Spring 2023, UIC
System: Replit and VS Code
Student Author: Sammy Haddad
TA's that helped: Md Aminul
------------------------------------------- */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

/* freeArray() is used to clear the memory of the allocated 2D array. Since the 2D array is
*  an array of char array's, you must loop through the total size of the array, including 
*  uninitialized spots, and free each char array, then finally freeing the pointer to that array.
*  The function takes two parameters, both by reference, the 2D array and its max size, and its 
*  return type is void. 
*/
void freeArray(char ***wordsArray, int *maxSize) {
  for (int i = 0; i < (*maxSize); i++) { //go through each element in the array
    free((*wordsArray)[i]); 
  }
  free(*wordsArray); //after freeing each char array, free the array itself as well
}

/* fillArray() resizes the 2D array each time the current size reaches the array's max size. 
*  It resize's the array dynamically, so when resizing you must copy over the old array and its
*  contents, and then you must free the old array to ensure there are no memory leaks. The function
*  takes the parameters: 2D array (by reference), inputString, current size (by reference), max size
*  (by reference), and the number of letters in each word and its return type is void. 
*/
void fillArray(char ***wordsArray, char inputString[81], int *currentSize, int *maxSize, int numLetterWords) { //seems to be filling array properly
  char **newArray = NULL;
  if ((*maxSize) == (*currentSize)) { //resize the array when it hits capacity
    newArray = malloc(sizeof(char *) * ((*maxSize) * 2));

    for (int i = 0; i < (*maxSize) * 2; i++) {
      newArray[i] = malloc(sizeof(char) * (numLetterWords + 1));
    }
    for (int i = 0; i < (*maxSize); i++) { //copy over all the old elements into the new array
      strcpy((newArray)[i], (*wordsArray)[i]);
    }
    for (int i = 0; i < *maxSize; i++) {
      free((*wordsArray)[i]); 
    }
    free(*wordsArray); //after freeing each char array, free the old array itself as well
    (*wordsArray) = (newArray);
    (*maxSize) = (*maxSize) * 2; 
  }

  strcpy((*wordsArray)[(*currentSize)], inputString); 
  (*currentSize)++;
}

/* readFile() reads in the words.txt file and makes a call to fillArray() to add each 
*  word to the dynamically allocated array if it is of the right size. readFile() also
*  prompts the user for how many letters are in each word, which is a determining factor
*  for the rest of the game. It takes the following parameters by reference: 2D array, 
*  number of letters in each word, and the max size of the array. It returns the number 
*  of words that are of the right size, as this number is used in calculations later in the
*  game. Thus, its return type is an int.
*/
int readFile(char ***wordsArray, int *numLetterWords, int *maxSize) {
  printf("How many letters do you want to have in the words? ");
  scanf("%d", numLetterWords);
  int count = 0;
  int currentSize = 0;
  (*maxSize) = 10;

  (*wordsArray) = malloc(sizeof(char *) * ((*maxSize))); //allocate the 2D array
  for (int i = 0; i < (*maxSize); i++) {
    (*wordsArray)[i] = malloc(sizeof(char) * ((*numLetterWords) + 1)); //allocate each index to be a char array
  }
  /* 
  This code demonstrates how to read in from a file.
	*/
	char filename[] = "words.txt";
	FILE *filePtr  = fopen(filename, "r"); // "r" means we open the file for reading
	// Check that the file was able to be opened
	if(filePtr == NULL) {
		printf("Error: could not open %s for reading\n", filename);
		exit(-1);
	}
	// Read each word from file, and print them one by one
	char inputString[ 81];
	while(fscanf(filePtr, "%s", inputString) != EOF) {
    if (strlen(inputString) == (*numLetterWords)) { //if the word is of the right size
      fillArray((wordsArray), inputString, &currentSize, maxSize, (*numLetterWords)); //add it to the array
      count++;
    }
	}
	// Close the file
	fclose(filePtr);
  printf("Number of %d-letter words found: %d. \n\n", (*numLetterWords), count);

  return count; //return the number of words of the specified size
}

/* checkDictionary() goes through the 2D array of words and searches for the given word.
*  If it finds the word, it returns true, which allows the function to leave early. Otherwise,
*  it iterates through all the words, and returns false if the word is not in the array. It takes
*  the parameters: word to be checked for in the array, number of words in the array, 2D array
*  (by reference). Its return type is a boolean. 
*/
bool checkDictionary(char checkWord[81], int count, char ***wordsArray) {
  for (int i = 0; i < count; i++) {
    if (strcmp(checkWord, (*wordsArray)[i]) == 0) { //when strcmp() == 0, it means the two strings are equal
      return true;
    }
  }
  return false;
}

/* gameWords() asks the user for a starting or ending word, and goes through a series of checks
*  to determine if those starting and ending words are valid or not. It will continue to prompt for
*  starting and ending words until a valid pair is given, or until r is inputted, which will give a 
*  random starting and ending word. It takes the following parameters: number of letters in each word, 
*  number of words in the array, the starting word and ending word, and the 2D array (by reference).
*  It is a return type of void.
*/
void gameWords(int numLetterWords, int count, char startingWord[81], char endingWord[81], char ***wordsArray) {
  bool foundStart = false;
  bool foundEnd = false;
  int random = 0;

  while (!(foundStart) || !(foundEnd)) { //both foundStart and foundEnd must be true to leave the loop
    foundStart = false;
    foundEnd = false;
    printf("Enter starting and ending words, or 'r' for either for a random word: ");
    scanf("%s%s", startingWord, endingWord);

    if (strcmp(startingWord, "r") == 0) { //if r, give a random starting word
      random = rand() % count;
      strcpy(startingWord, (*wordsArray)[random]);
      foundStart = true;
    }
    if (strlen(startingWord) != numLetterWords) { //checks if the word is of the right length
      printf("Your word, '%s', is not a %d-letter word. Try again.\n", startingWord, numLetterWords);
      continue;
    }
    if (!(checkDictionary(startingWord, count, wordsArray))) { //checks if the word is in the 2D array
      printf("Your word, '%s', is not a valid dictionary word. Try again.\n", startingWord);
      continue;
    }
    if (strcmp(endingWord, "r") == 0) { //if r, give a random ending word
      random = rand() % count;
      strcpy(endingWord, (*wordsArray)[random]);
      foundEnd = true;
    }
    if (strlen(endingWord) != numLetterWords) { //checks if the word is of the right length
      printf("Your word, '%s', is not a %d-letter word. Try again.\n", endingWord, numLetterWords);
      continue;
    }
    if (!(checkDictionary(endingWord, count, wordsArray))) { //checks if the word is in the 2D array
      printf("Your word, '%s', is not a valid dictionary word. Try again.\n", endingWord);
      continue;
    }

    //if we get down to here, the starting and ending words passed all the checks, so set the found variables to true
    foundStart = true; 
    foundEnd = true;
  }
}

/* validMove() goes through and compares the previous and next word's
*  characters to each other, and determines if they are exactly one
*  character different or not. If so, return true, and otherwise, return
*  false. The function takes the following paramters: number of letters
*  in each word, the next word, and the previous word.
*  It is a return type of a boolean.
*/
bool validMove(int numLetterWords, char nextWord[81], char previousWord[81]) {
  int difference = 0;
  for (int i = 0; i < numLetterWords; i++) { //go through the size of each word
    if (nextWord[i] != previousWord[i]) {
      difference++; //if the character is different, increment our counter
    }
  }
  if (difference == 1) { //if they are exactly one different, return true
    return true;
  }
  return false;
}

/* playGame() asks the user for the next word, and goes through a series of checks to determine 
*  if that word is a valid move or not. It will continue to prompt for a next word until a valid 
*  word is given, and this will continue until either the game is won, or the user inputs 'q' to quit.
*  It takes the following parameters: number of letters in each word, number of words in the array, 
*  the starting word and ending word, and the 2D array (by reference). It is a return type of void.
*/
void playGame(int numLetterWords, int count, char startingWord[81], char endingWord[81], char ***wordsArray) {
  printf("Your starting word is: %s.\n", startingWord);
	printf("Your ending word is: %s.\n", endingWord);
  printf("On each move enter a word of the same length that is at most 1 character different and is also in the dictionary.\n");
  printf("You may also type in 'q' to quit guessing.\n");

  char previousWord[81];
  char nextWord[81];
  int moveCount = 1;
  strcpy(previousWord, startingWord);
  
  while (true) { //this while condition is always true, as we only stop playing once we win or quit, in which we return at that moment
    printf("\n%d. Previous word is '%s'. Goal word is '%s'. Next word: ", moveCount, previousWord, endingWord);
    scanf("%s", nextWord);

    if (strcmp(nextWord, "q") == 0) { //quit the game and return if q is entered
      return;
    }
    if (strlen(nextWord) != numLetterWords) { //checks if the word is of the right length
      printf("Your word, '%s', is not a %d-letter word. Try again.\n", nextWord, numLetterWords);
      continue;
    }
    if (!(checkDictionary(nextWord, count, wordsArray))) { //checks if the word is in the 2D array
      printf("Your word, '%s', is not a valid dictionary word. Try again.\n", nextWord);
      continue;
    }
    if (!(validMove(numLetterWords, nextWord, previousWord))) { //checks if the word is exactly one character different
      printf("Your word, '%s', is not exactly 1 character different. Try again.\n", nextWord);
      continue;
    }
    if (strcmp(nextWord, endingWord) == 0) { //if the next word is the end word, print out victory message and return
      printf("Congratulations! You changed '%s' into '%s' in %d moves.\n", startingWord, endingWord, moveCount);
      return;
    }

    //if we get here, the next word passed all the checks but did not win the game, so increase our move count and make the next word the new previous word
    moveCount++;
    strcpy(previousWord, nextWord);
  }
}

/* menuOptions() allows the user a few different choices after playing the game once. 
*  You can either: play again with the same number of letters in each word, 
*  play again with a different number of letters in each word, or exit the game.
*  It takes the following parameters: number of letters in each word,
*  number of words in the array, the starting word and ending word, the 2D array
*  (by reference), and the max size. It is a return type of void.
*/
void menuOptions(int numLetterWords, int count, char startingWord[81], char endingWord[81], char ***wordsArray, int *maxSize) {

  int command;
  while (true) { //this while condition is always true, as we return right when the user inputs the exit command
    printf("\nEnter: \t1 to play again,\n");
    printf("\t2 to change the number of letters in the words and then play again, or\n");
    printf("\t3 to exit the program.\n");
    printf("Your choice --> ");
    scanf("%d", &command);

    if (command == 1) { //play the game again with the same number of letters in each word
      gameWords(numLetterWords, count, startingWord, endingWord, (wordsArray));
      playGame(numLetterWords, count, startingWord, endingWord, (wordsArray));
      continue;
    }
    if (command == 2) { //play the game again, but with a different number of letters in each word
      freeArray(wordsArray, maxSize); //we must first free the current 2D array before filling a new one up
      count = readFile((wordsArray), &numLetterWords, maxSize);
      gameWords(numLetterWords, count, startingWord, endingWord, (wordsArray));
      playGame(numLetterWords, count, startingWord, endingWord, (wordsArray));
      continue;
    }
    if (command == 3) { //exit the game
      printf("\nThanks for playing!\nExiting...\n\n");
      freeArray(wordsArray, maxSize); //when we exit the game, we must free our 2D array
      return;
    }
  }
}

/* main() runs the entire game once at the start, and then from there menuOptions() takes over and
*  continues to prompt the user about whether or not they want to play the game again or exit. Here,
*  we also create: the pointer to our 2D array, the starting and ending words, the number
*  of letters in each word, and the max size of the 2D array. main() takes no parameters, and returns
*  0 at the end to signify the end of the program. 
*/
int main() {
  srand(1); //for the autograder
  printf("Weaver is a game where you try to find a way to get from the starting word to the ending word.\n");
	printf("You can change only one letter at a time, and each word along the way must be a valid word.\n");
	printf("Enjoy!\n\n");

  //create the varaibles for: the pointer to our 2D array, the starting and ending words, the number
  //of letters in each word, and the max size of the 2D array.
  char **wordsArray = NULL;
  int numLetterWords = 0;
  int maxSize = 0;
  char startingWord[81];
  char endingWord[81];

  //we run through the game once at the beginning, making the following calls to create the 2D array and play the game
  int count = readFile(&wordsArray, &numLetterWords, &maxSize);
  gameWords(numLetterWords, count, startingWord, endingWord, &wordsArray);
  playGame(numLetterWords, count, startingWord, endingWord, &wordsArray);
  menuOptions(numLetterWords, count, startingWord, endingWord, &wordsArray, &maxSize);
  
	return 0;
}