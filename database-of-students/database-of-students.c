/*-------------------------------------------
Program 4: Database of Students
Course: CS 211, Spring 2023, UIC
System: Replit and VS Code
Student Author: Sammy Haddad
------------------------------------------- */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdbool.h>

// Student struct: name, id, gpa, creditHours
typedef struct {
	char* name;
	char* id;
	double gpa;
	int creditHours;
} Student;

// StudentNode struct: student pointer, next pointer
typedef struct StudentNode {
	Student* pStudent;
	struct StudentNode* pNext;
} StudentNode;

// Database struct: StudentNode pointer to all lists in the database
typedef struct {
	StudentNode* pIDList;
	StudentNode* pHonorRollList;
	StudentNode* pAcademicProbationList;
	StudentNode* pFreshmanList;
	StudentNode* pSophomoreList;
	StudentNode* pJuniorList;
	StudentNode* pSeniorList;
} Database;

/* addToHonors() -- add a student to the honor roll list in 
*  the database. Make a new StudentNode to point to this
*  student, and add them to the list of existing honor roll nodes.
*/
void addToHonors(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pHonorRollList == NULL) { //first student in the list
    database->pHonorRollList = newStudent;
  }
    
  else if (newStudent->pStudent->gpa <= database->pHonorRollList->pStudent->gpa) {
    //prepend to the pHonorRollList
    StudentNode* temp = database->pHonorRollList;
    database->pHonorRollList = newStudent;
    newStudent->pNext = temp;
  }
    
  else { //sort the pHonorRollList by GPA
    StudentNode* copy = database->pHonorRollList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (newStudent->pStudent->gpa <= copy->pStudent->gpa) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
}

/* addToProbation() -- add a student to the academic probation list in 
*  the database. Make a new StudentNode to point to this student,
*  and add them to the list of existing academic probation nodes.
*/
void addToProbation(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pAcademicProbationList == NULL) { //first student on list
    database->pAcademicProbationList = newStudent;
  }
    
  else if (newStudent->pStudent->gpa <= database->pAcademicProbationList->pStudent->gpa) { 
    //prepend to the pAcademicProbationList
    StudentNode* temp = database->pAcademicProbationList;
    database->pAcademicProbationList = newStudent;
    newStudent->pNext = temp;
  }
    
  else { //sort the pAcademicProbationList by GPA
    StudentNode* copy = database->pAcademicProbationList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (newStudent->pStudent->gpa <= copy->pStudent->gpa) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
}

/* addToFreshman() -- add a student to the freshman list in 
*  the database. Make a new StudentNode to point to this student,
*  and add them to the list of existing freshman nodes.
*/
void addToFreshman(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pFreshmanList == NULL) { //first student on list
    database->pFreshmanList = newStudent;
  }
    
  else if (strcmp(database->pFreshmanList->pStudent->name, newStudent->pStudent->name) > 0) { 
    //prepend to the pFreshmanList
    StudentNode* temp = database->pFreshmanList;
    database->pFreshmanList = newStudent;
    newStudent->pNext = temp;
  }
    
  else { //sort the pFreshmanList by name
    StudentNode* copy = database->pFreshmanList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (strcmp(copy->pStudent->name, newStudent->pStudent->name) > 0) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
}

/* addToSophmore() -- add a student to the sophmore list in 
*  the database. Make a new StudentNode to point to this student,
*  and add them to the list of existing sophmore nodes.
*/
void addToSophmore(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pSophomoreList == NULL) { //first student on list
    database->pSophomoreList = newStudent;
  }

  else if (strcmp(database->pSophomoreList->pStudent->name, newStudent->pStudent->name) > 0) {
    //prepend to the pSophomoreList
    StudentNode* temp = database->pSophomoreList;
    database->pSophomoreList = newStudent;
    newStudent->pNext = temp;
  }
  else { //sort the pSophomoreList by name
    StudentNode* copy = database->pSophomoreList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (strcmp(copy->pStudent->name, newStudent->pStudent->name) > 0) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
}

/* addToJunior() -- add a student to the junior list in 
*  the database. Make a new StudentNode to point to this student,
*  and add them to the list of existing junior nodes.
*/
void addToJunior(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pJuniorList == NULL) { //first student on list
    database->pJuniorList = newStudent;
  }
    
  else if (strcmp(database->pJuniorList->pStudent->name, newStudent->pStudent->name) > 0) {
    //prepend to the pJuniorList
    StudentNode* temp = database->pJuniorList;
    database->pJuniorList = newStudent;
    newStudent->pNext = temp;
  }
    
  else { //sort the pJuniorList by name
    StudentNode* copy = database->pJuniorList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (strcmp(copy->pStudent->name, newStudent->pStudent->name) > 0) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
}

/* addToSenior() -- add a student to the senior list in 
*  the database. Make a new StudentNode to point to this student,
*  and add them to the list of existing senior nodes.
*/
void addToSenior(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pSeniorList == NULL) { //first student on list
    database->pSeniorList = newStudent;
  }
    
  else if (strcmp(database->pSeniorList->pStudent->name, newStudent->pStudent->name) > 0) {
    //prepend to the pSeniorList
    StudentNode* temp = database->pSeniorList;
    database->pSeniorList = newStudent;
    newStudent->pNext = temp;
  }
    
  else { //sort the pSeniorList by name
    StudentNode* copy = database->pSeniorList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (strcmp(copy->pStudent->name, newStudent->pStudent->name) > 0) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
}

/* addStudent() -- add a student to the ID list in the database. 
*  Make a new StudentNode to point to this student, and add them
*  to the list of existing ID nodes. Then, add them to the other
*  list the student belongs on, such as honor roll, freshman, etc.
*/
void addStudent(Database* database, Student* student) {
  StudentNode* newStudent = malloc(sizeof(StudentNode));
  newStudent->pStudent = student;
  newStudent->pNext = NULL;

  if (database->pIDList == NULL) { //first student on list
    database->pIDList = newStudent;
  }
    
  else if (strcmp(database->pIDList->pStudent->id, newStudent->pStudent->id) > 0) {
    //prepend to the pSeniorList
    StudentNode* temp = database->pIDList;
    database->pIDList = newStudent;
    newStudent->pNext = temp;
  }
    
  else { //sort the pIDList alphanumerically
    StudentNode* copy = database->pIDList;
    StudentNode* prev = NULL;
    
    while (copy != NULL) {
      if (strcmp(copy->pStudent->id, newStudent->pStudent->id) > 0) {
        prev->pNext = newStudent;
        newStudent->pNext = copy;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }

    if (copy == NULL) { //reached end, so append newStudent
      prev->pNext = newStudent;
    }
  }
  
  if (student->gpa >= 3.5) { //honor roll list
    addToHonors(database, student);
  }
  
  if (student->gpa < 2.0) { //academic probation list
    addToProbation(database, student);
  }
  
  if (student->creditHours < 30) { //freshman list
    addToFreshman(database, student);
  }
  
  if (student->creditHours >= 30 && student->creditHours < 60) { //sophmore list
    addToSophmore(database, student);
  }
  
  if (student->creditHours >= 60 && student->creditHours < 90) { //junior list
    addToJunior(database, student);
  }
  
  if (student->creditHours >= 90) { //senior list
    addToSenior(database, student);
  }
}

/* readFile() -- read from a csv file which contains info on 
*  a student: name, id, gpa, and credit hours. Make space 
*  for this student in memory, and add them to the database.
*/
void readFile(char filename[81], Database* database) {
  
  FILE *filePtr  = fopen(filename, "r"); // "r" means we open the file for reading
	// Check that the file was able to be opened
	if (filePtr == NULL) {
		printf("Error: could not open %s for reading\n", filename);
		exit(-1);
	}

  char line[81] = "";
  int field = 0;
  bool skip = true;
  char* token = NULL;
  
  while (feof(filePtr) != true) { //read until the end of file
    
    fgets(line, 81, filePtr);
    if (skip) {
      skip = false;
      continue;
    }

    //make space for the student, and intialize default values
    Student* student = malloc(sizeof(Student));
    student->name = malloc(sizeof(char) * 81);
    student->id = malloc(sizeof(char) * 81);
    student->gpa = 0;
    student->creditHours = 0;
    token = strtok(line, ",");

    //fill the student's values appropriately
    while (token != NULL) {
      
      if (field == 0) { //name
        strcpy(student->name, token);
        field++;
      }
        
      else if (field == 1) { //ID
        strcpy(student->id, token);
        field++;
      }
        
      else if (field == 2) { //GPA
        student->gpa = atof(token);
        field++;
      }
        
      else if (field == 3) { //Credit Hours
        student->creditHours = atoi(token);
        field++;
      }
        
      else { //when field == 4
        field = 0;
        continue;
      }
      
      token = strtok(NULL, ",");
    }

    //add the student to the database
    addStudent(database, student);
  }
}

/* createStudent() -- prompt the user for info on a student: 
*  name, id, gpa, credit hours. Then, make space for
*  this student in memory and add them to the database.
*/
void createStudent(Database* database) {
  char name[81] = "";
  char ID[81] = "";
  double gpa = 0;
  int creditHours = 0;
  Student* student = malloc(sizeof(Student));
  student->name = malloc(sizeof(char) * 81);
  student->id = malloc(sizeof(char) * 81);
  student->gpa = 0;
  student->creditHours = 0;

  //prompt for name, and add to student's name value
  printf("Enter the name of the new student: ");
  fgets(name, 80, stdin);
  if (strcmp(name, "\n") == 0) {
    fgets(name, 80, stdin);
  }

  strtok(name, "\n");
  strcpy(student->name, name);

  //prompt for ID, and add to student's ID value
  printf("Enter the ID of the new student: ");
  fgets(ID, 80, stdin);

  strtok(ID, "\n");
  strcpy(student->id, ID);

  //prompt for gpa, and add to student's gpa value
  printf("Enter the GPA of the new student: ");
  scanf("%lf", &gpa);
  student->gpa = gpa;

  //prompt for credit hours, and add to student's creditHours value
  printf("Enter the credit hours of the new student: ");
  scanf("%d", &creditHours);
  student->creditHours = creditHours;

	printf("Successfully added the following student to the database!\n");
  printf("%s:\n", student->name);
  printf("        ID - %s\n", student->id);
  printf("        GPA - %.2f\n", student->gpa);
  printf("        Credit Hours - %d\n", student->creditHours);

  //add the student to the database
  addStudent(database, student);
}

/* displayList() -- display one of the database's list.
*/
void displayList(Database* database, StudentNode* list) {
  while (list != NULL) {
    printf("%s:\n", list->pStudent->name);
    printf("        ID - %s\n", list->pStudent->id);
    printf("        GPA - %.2f\n", list->pStudent->gpa);
    printf("        Credit Hours - %d\n", list->pStudent->creditHours);
    list = list->pNext;
  }
}

/* searchForStudent() -- search for a ID in the database,
*  and if found, output the info of that student. If not 
*  found, output a message that no student matches the ID.
*/
void searchForStudent(Database* database) {
  char studentID[81] = "";
  printf("Enter the id of the student to find: ");
  scanf("%s", studentID);

  StudentNode* search = database->pIDList;
  //search through the ID list, and if found,
  //print the student with that ID
  while (search != NULL) {
    if (strcmp(search->pStudent->id, studentID) == 0) {
      printf("%s:\n", search->pStudent->name);
      printf("        ID - %s\n", search->pStudent->id);
      printf("        GPA - %.2f\n", search->pStudent->gpa);
      printf("        Credit Hours - %d\n", search->pStudent->creditHours);
      break;
    }
    search = search->pNext;
  }
  if (search == NULL) { //if not found, output error message
    printf("Sorry, there is no student in the database with the id %s.\n", studentID);
  }
}

/* readDatabase() -- prompt the user for a selection of 
*  1-8, and perform the action corresponding with the 
*  inputted number. If a number other than 1-8 is inputted
*  output an error message and prompt for another number. 
*/
void readDatabase(Database* database) {
  bool validChoice = false;
  int choice = 0;
  
  printf("Select one of the following: \n");
	printf("\t1) Display the head (first 10 rows) of the database\n");
	printf("\t2) Display students on the honor roll, in order of their GPA\n");
	printf("\t3) Display students on academic probation, in order of their GPA\n");
	printf("\t4) Display freshmen students, in order of their name\n");
	printf("\t5) Display sophomore students, in order of their name\n");
	printf("\t6) Display junior students, in order of their name\n");
	printf("\t7) Display senior students, in order of their name\n");
	printf("\t8) Display the information of a particular student\n");

  //continue prompting for a number until 1-8 is chosen
  while (!validChoice) {
    printf("Your choice --> ");
    scanf("%d", &choice);
    
    if (choice == 1) { //display first 10 ID's
      StudentNode* list = database->pIDList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      for (int i = 0; i < 10; i++) {
        if (list != NULL) {
          printf("%s:\n", list->pStudent->name);
          printf("        ID - %s\n", list->pStudent->id);
          printf("        GPA - %.2f\n", list->pStudent->gpa);
          printf("        Credit Hours - %d\n", list->pStudent->creditHours);
          list = list->pNext;
        }
        else {
          break;
        }
      }
    }
      
    else if (choice == 2) { //display honor roll list
      StudentNode* list = database->pHonorRollList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      displayList(database, list);
    }
      
    else if (choice == 3) { //display academic probation list
      StudentNode* list = database->pAcademicProbationList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      displayList(database, list);
    }
      
    else if (choice == 4) { //display freshman list
      StudentNode* list = database->pFreshmanList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      displayList(database, list);
    }
      
    else if (choice == 5) { //display sophmore list
      StudentNode* list = database->pSophomoreList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      displayList(database, list);
    }
      
    else if (choice == 6) { //display junior list
      StudentNode* list = database->pJuniorList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      displayList(database, list);
    }
      
    else if (choice == 7) { //display senior list
      StudentNode* list = database->pSeniorList;
      if (list == NULL) {
        printf("There are no students matching that criteria.\n\n");
        break;
      }
      displayList(database, list);
    }
      
    else if (choice == 8) { //search for a specific student's ID
      searchForStudent(database);
    }
      
    else { //not 1-8, so ouput an error and prompt for input again
      printf("Sorry, that input was invalid. Please try again.\n");
      continue;
    }
    validChoice = true;
  }
}

/* deleteFromList() -- given a database list and a student, 
*  remove this student's StudentNode from the database list
*  and free the memory associated with that StudentNode. 
*/
void deleteFromList(StudentNode** list, Student* student) {

  StudentNode* temp = NULL;
  StudentNode* prev = NULL;
  StudentNode* copy = (*list);
  
  if (strcmp((*list)->pStudent->id, student->id) == 0) { //first node is removed
    temp = (*list);
    (*list) = (*list)->pNext;
  }

  //remove from either the middle or end (remove from anywhere other then the head)
  else {
    while (copy != NULL) {
      if (strcmp(copy->pStudent->id, student->id) == 0) {
        temp = copy;
        prev->pNext = copy->pNext;
        break;
      }
      prev = copy;
      copy = copy->pNext;
    }
  }
  
  temp->pNext = NULL;
  free(temp); //free the node
}

/* deleteStudent() -- remove a student from the database by
*  removing them from every database list they are in and 
*  freeing the associated node's memory. Then, free the
*  student's memory from the program as well. 
*/
void deleteStudent(Database* database, Student** student) {

  //remove from the ID list
  deleteFromList(&(database->pIDList), (*student));

  if ((*student)->gpa >= 3.5) { //remove from the honor roll list
    deleteFromList(&(database->pHonorRollList), (*student));
  }
  
  if ((*student)->gpa < 2.0) { //remove from the academic probation list
    deleteFromList(&(database->pAcademicProbationList), (*student));
  }
  
  if ((*student)->creditHours < 30) { //remove from the freshman list
    deleteFromList(&(database->pFreshmanList), (*student));
  }
  
  if ((*student)->creditHours >= 30 && (*student)->creditHours < 60) { //remove from the sophmore list

    deleteFromList(&(database->pSophomoreList), (*student));
  }
  
  if ((*student)->creditHours >= 60 && (*student)->creditHours < 90) { //remove from the junior list
    deleteFromList(&(database->pJuniorList), (*student));
  }
  
  if ((*student)->creditHours >= 90) { //remove from the senior list
    deleteFromList(&(database->pSeniorList), (*student));
  }

  //free the student's memory from the program
  free((*student)->name);
  free((*student)->id);
  free((*student));
}

/* clearDatabase() -- free all the memory in the database, which
*  is all the node's in every list and all the students.
*/
void clearDatabase(Database* database) {
  StudentNode* list = database->pIDList;
  StudentNode* next = NULL;
  Student* remove = NULL;

  //free all the memory by calling delete student on every student
  while (list != NULL) {
    next = list->pNext;
    remove = list->pStudent;
    deleteStudent(database, &remove);
    list = next;
  }
}

/* menuOptions() -- prompts the user to either create a new
*  student, read from the database, delete a student, or exit
*  the program. Calls all needed functions such as addStudent(),
*  readDatabase(), deleteStudent(), clearDatabase(), etc.
*/
void menuOptions(Database* database) {
  char letter = ' ';
  bool end = false;

  //continue to prompt the user for C, R, or D until X is inputted
  while (!end) {
    printf("\nEnter: \tC to create a new student and add them to the database,\n");
  	printf("\tR to read from the database,\n");
  	printf("\tD to delete a student from the database, or\n");
  	printf("\tX to exit the program.\n");
  	printf("Your choice --> ");
    scanf(" %c", &letter);
    
    if (letter == 'C') { //create a new student
      createStudent(database);
      continue;
    }
      
    else if (letter == 'R') { //read from the database
      readDatabase(database);
      continue;
    }
      
    else if (letter == 'D') { //delete a student 
      char deleteID[81] = "";
      printf("Enter the id of the student to be removed: ");
      scanf("%s", deleteID);
      
      bool foundStudent = false;
      StudentNode* list = database->pIDList;
      Student* student = NULL;

      //search for the student to delete's ID
      while (list != NULL) {
        if (strcmp(list->pStudent->id, deleteID) == 0) {
          student = list->pStudent;
          foundStudent = true;
          break;
        }
        list = list->pNext;
      }

      //if not found, output an error message, else delete the student
      if (!foundStudent) {
        printf("Sorry, there is no student in the database with the id %s.", deleteID);
      }
        
      else {
        deleteStudent(database, &student);
      }

      continue;
    }
      
    else if (letter == 'X') { //exit the program and clear the database
      end = true;
      printf("\nThanks for playing!\n");
	    printf("Exiting...\n");
      clearDatabase(database);
      break;
    }
      
    else {
      printf("Invalid option. Try again.\n");
      continue;
    }
  }
}

/* main() -- runs the program by prompting for a file to read
*  to fill the database or start with an empty database, and
*  then call menuOptions() to run the rest of the program. 
*/
int main() {
  Database database;
  char letter = ' ';
  char filename[81] = "";
  bool validLetter = false;

  //initialize an empty database
  database.pIDList = NULL;
  database.pHonorRollList = NULL;
  database.pAcademicProbationList = NULL;
  database.pFreshmanList = NULL;
  database.pSophomoreList = NULL;
  database.pJuniorList = NULL;
  database.pSeniorList = NULL;
  
	printf("CS 211, Spring 2023\n");
	printf("Program 4: Database of Students\n\n");
	printf("Enter E to start with an empty database, \n");
	printf("or F to start with a database that has information on students from a file.\n");

  //prompt the user, if not E or F continue to prompt for E or F
  while (!validLetter) {
    printf("Your choice --> ");
    scanf(" %c", &letter);
    
    if (letter == 'E') { //start with an empty database
      validLetter = true;
    }
      
    else if (letter == 'F') { //read from a csv file
      printf("Enter the name of the file you would like to use: ");
      scanf("%s", filename);
      readFile(filename, &database);
      validLetter = true;
    }
      
    else { //if not E or F, output an error message and ask for input again
      printf("Sorry, that input was invalid. Please try again.\n");
    }
  }

  //call menuOptions() to run the rest of the program
  menuOptions(&database);
	return 0;
}