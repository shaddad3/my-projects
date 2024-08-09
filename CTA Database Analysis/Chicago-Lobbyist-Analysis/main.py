#
# Program to retrieve and analyze data about Chicago
# Lobbyists. Built keeping Object Relational Mapping
# in mind by seperating the program into 3 parts, the
# presentation tier, data tier, and object tier.
#
# Author: Sammy Haddad
#
import sqlite3
import objecttier


##################################################################  
#
# general_stats
#
# Uses functions from the object tier to get the number of
# lobbyists, employers, and clients in the database and
# display them for the user at the start of the program.
#
def general_stats(dbConn):
    print()
    print("General Statistics:")

    # get the total # of lobbyists, employers, clients from objecttier
    lobbyists = objecttier.num_lobbyists(dbConn)
    employers = objecttier.num_employers(dbConn)
    clients = objecttier.num_clients(dbConn)

    # print these totals to the user
    print("\t", "Number of Lobbyists: " + f"{lobbyists:,}")
    print("\t", "Number of Employers: " + f"{employers:,}")
    print("\t", "Number of Clients: " + f"{clients:,}")


##################################################################  
#
# command_1
#
# Asks the user to input an lobbyist name, and retrieves the
# info about any lobbyists that match the name (wildcards are
# allowed so many lobbyists can be printed). If more then 100
# lobbyists are retrieved, instead of printing all their info
# a message displays asking the user to narrow their search.
#
def command_1(dbConn):
    print()
    # get the name to search for
    name = input("Enter lobbyist name (first or last, wildcards _ and % supported):")
    print()

    # use the objecttier to retrieve the info about all lobbyists matching the given name
    result = objecttier.get_lobbyists(dbConn, name)
    print("Number of lobbyists found: " + str(len(result)))
    print()

    # if there are > 100 lobbyists, display an "error" like message 
    if len(result) > 100:
        print("There are too many lobbyists to display, please narrow your search and try again...")
        return
    # if there is no info, print nothing
    elif len(result) == 0:
        return
    
    # for each lobbyist in the query, print their info
    for res in result:
        print(res.Lobbyist_ID, ":", res.First_Name, res.Last_Name, "Phone:", res.Phone)


##################################################################  
#
# command_2
# 
# Ask the user to input an id corresponding to some lobbyist.
# if this id does not exist in the database, print that none
# match this id and do nothing. If the id is in the database,
# retrieve all the info regarding this lobbyist and display it
# to the user. 
#
def command_2(dbConn):
    print()
    # get an id to search for
    id = input("Enter Lobbyist ID: ")
    print()

    # use the objecttier to retrieve the info about all lobbyists matching the given id
    result = objecttier.get_lobbyist_details(dbConn, id)

    # if there are no lobbyists with id, print an "error" like message
    if (result is None):
        print("No lobbyist with that ID was found.")
        return
    
    # print all the info about the lobbyist if the id was found in the database
    print(result.Lobbyist_ID, ":", "\n\tFull Name:", result.Salutation, result.First_Name, result.Middle_Initial,
            result.Last_Name, result.Suffix, "\n\tAddress:", result.Address_1, result.Address_2, ",", result.City, 
            ",", result.State_Initial, result.Zip_Code, result.Country, "\n\tEmail:", result.Email, 
            "\n\tPhone:", result.Phone, "\n\tFax:", result.Fax)
    print("\tYears Registered:", end=" ")
    for year in result.Years_Registered: # print out all the years in the years_registered list
        print(year, end=", ")
    print("\n\tEmployers:", end=" ")
    for emp in result.Employers: # print out all the employers in the employers list
        print(emp, end=", ")
    print("\n\tTotal Compensation:", f"${result.Total_Compensation:,.2f}")


##################################################################  
#
# command_3
#
# Ask the user to input a number, and this will correspond to the
# number of the top lobbyists our query will retrieve. Also ask for 
# a year. This query will display the top N lobbyists from the given
# year, and their associated info and clients from that year. 
# The input number must be positive, and the year must be in the
# database, or else we will not print the info
#
def command_3(dbConn):
    print()
    # get the number of lobbyists to display
    num = input("Enter the value of N: ")

    # make sure the inputted num is positive
    if int(num) <= 0:
        print("Please enter a positive value for N...")
        return
    
    # get the year to display the top lobbyists from this year
    year = input("Enter the year: ")

    # get the results and lobbyist info given the limit and year to search
    result = objecttier.get_top_N_lobbyists(dbConn, int(num), year)
    
    # if the query is empty, do nothing
    if len(result) == 0:
        return
    
    print()
    count = 1

    # for each lobbyist in the result, print out their info and their clients from that year
    for res in result:
        print(count, ".", res.First_Name, res.Last_Name, "\n\tPhone:", res.Phone, 
              "\n\tTotal Compensation:", f"${res.Total_Compensation:,.2f}")
        print("\tClients:", end=" ")
        for client in res.Clients: # go through the list of clients and print them out
            print(client, end=", ")
        print()
        count += 1


##################################################################  
#
# command_4
#
# Ask the user for a year and id, and for the lobbyist corresponding
# to the id given, add the year to the list of years registered. If
# no lobbyist has that id, let the user know, otherwise display a 
# success message
#
def command_4(dbConn):
    print()
    # get the year to add and the id corresponding to a lobbyist
    year = input("Enter year: ")
    id = input("Enter the lobbyist ID: ")

    # update the database based on the year and id given
    result = objecttier.add_lobbyist_year(dbConn, id, year)

    print()
    # if the database was no successfully updated, let the user know
    if result == 0 or result == -1:
        print("No lobbyist with that ID was found.")
    # otherwise, dispay a sucess message
    else:
        print("Lobbyist successfully registered.")


##################################################################  
#
# command_5
#
# Ask the user for a salutation and id, and for the lobbyist
# corresponding to the id given, update that users salutation. If
# no lobbyist has that id, let the user know, otherwise display a 
# success message
#
def command_5(dbConn):
    print()
    # get the salutation to add and the id corresponding to a lobbyist
    id = input("Enter the lobbyist ID: ")
    salutation = input("Enter the salutation: ")
    print()

    # update the database based on the salutation and id given
    result = objecttier.set_salutation(dbConn, id, salutation)

    # if the database was no successfully updated, let the user know
    if result == 0 or result == -1:
        print("No lobbyist with that ID was found.")
    # otherwise, dispay a sucess message
    else:
        print("Salutation successfully set.")


##################################################################  
#
# main
# 
# Run the program, first displaying the stats from the database
# and then entering a loop that continues prompting the user for
# a command (1-5) or x to exit.
#
print('** Welcome to the Chicago Lobbyist Database Application **')

dbConn = sqlite3.connect("Chicago_Lobbyists.db")
# display the general stats
general_stats(dbConn)

# continue looping until the user enters "x"
while (True):
    print()
    # get the user's input
    choice = input("Please enter a command (1-5, x to exit): ")

    if choice == "x": # exit the program if "x" is entered
        break
    elif choice == "1": # execute commmand_1()
        command_1(dbConn)
    elif choice == "2": # execute commmand_2()
        command_2(dbConn)
    elif choice == "3": # execute commmand_3()
        command_3(dbConn)
    elif choice == "4": # execute commmand_4()
        command_4(dbConn)
    elif choice == "5": # execute commmand_5()
        command_5(dbConn)
    else: # display an error message if something else is inputted
        print("**Error, unknown command, try again...")
#
# done
#
