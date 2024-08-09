import sqlite3
import matplotlib.pyplot as figure


def general_stats(dbConn):
    ''' general_stats() prints out the # of stations, stops, ride entries, 
    total riders, as well as the first and last date data was entered '''

    dbCursor = dbConn.cursor()
    print("** Welcome to CTA L analysis app **" + "\n")
    print("General Statistics: ")

    # find the number of stations 
    stations = """select distinct count(Station_Name)
                from Stations
                """
    
    # find the number of stops 
    stops = """select distinct count(Stop_Name)
            from Stops
            """
    
    # find the number of ride entries
    ride_entries = """select distinct count(Ride_Date)
            from Ridership
            """
    
    # find first date entered into database
    start_date = """select date(Ride_Date)
            from Ridership
            order by Ride_Date asc
            limit 1
            """
    
    # find last date entered into database
    end_date = """select date(Ride_Date)
            from Ridership
            order by Ride_Date desc
            limit 1
            """
    
    # calculate total number of riders
    total_riders = """select sum(Num_Riders)
            from Ridership
            """
    
    dbCursor.execute(stations)
    num_stations = dbCursor.fetchone()

    dbCursor.execute(stops)
    num_stops = dbCursor.fetchone()

    dbCursor.execute(ride_entries)
    num_ride_entries = dbCursor.fetchone()

    dbCursor.execute(total_riders)
    num_total_riders = dbCursor.fetchone()

    dbCursor.execute(start_date)
    start = dbCursor.fetchone()

    dbCursor.execute(end_date)
    end = dbCursor.fetchone()

    # print out all the info found in SQL queries
    print("\t" + "# of stations: " + str(num_stations[0])) # may not need a tab in front, but idk
    print("\t" + "# of stops: " + str(num_stops[0])) # may not need a tab in front, but idk
    print("\t" + "# of ride entries: " + f"{num_ride_entries[0]:,}") # may not need a tab in front, but idk
    print("\t" + "date range: " + start[0] + " - " + end[0]) # may not need a tab in front, but idk
    print("\t" + "Total ridership: " + f"{num_total_riders[0]:,}") # may not need a tab in front, but idk


def command_1(dbConn):
    ''' command_1() takes a station name and finds all station names that
        match the input, including SQL wildcards. If not names match, an
        error message is displayed '''
    
    dbCursor = dbConn.cursor()
    print()

    # get name to search for in database
    partial_name = input("Enter partial station name (wildcards _ and %): ")

    station_names = """select Station_ID, Station_Name
                    from Stations
                    where Station_Name like ?
                    order by Station_Name asc;
                    """
    
    dbCursor.execute(station_names, [partial_name])
    names = dbCursor.fetchall()

    # if names is empty, then no stations matched the input name
    if (len(names) == 0):
        print("**No stations found...")
    else:
        # print all station names that matched the input name
        for name in names:
            print(str(name[0]) + " : " + str(name[1]))


def command_2(dbConn):
    ''' command_2() finds the total number of riders for weekdays, 
        Saturdays, and Sunday/holidays, and total riders. Then,
        it shows the percantage that each category makes of the total '''
    
    dbCursor = dbConn.cursor()
    print()

    # get station name
    name = input("Enter the name of the station you would like to analyze: ")

    station_exists = """select Station_Name
                    from Stations
                    where Station_Name like ?;
                    """
    
    dbCursor.execute(station_exists, [name])
    names = dbCursor.fetchone()
    
    # if names is empty, then the input name did not match any stations in database
    if (names is None):
        print("**No data found...")
        return

    # get total number of riders from input station
    total = """select sum(Num_Riders)
                from Ridership
                join Stations
                on Stations.Station_ID = Ridership.Station_ID
                where Station_Name like ?;
                """

    # get number of weekday riders from input station
    weekday = """select sum(Num_Riders)
                from Ridership
                join Stations
                on Stations.Station_ID = Ridership.Station_ID
                where (Station_Name like ?) and (Type_of_Day = 'W');
                """
    
    # get number of Saturday riders from input station
    saturday = """select sum(Num_Riders)
                from Ridership
                join Stations
                on Stations.Station_ID = Ridership.Station_ID
                where (Station_Name like ?) and (Type_of_Day = 'A');
                """
    
    # get number of sunday/holiday riders from input station
    sunday_holiday = """select sum(Num_Riders)
                from Ridership
                join Stations
                on Stations.Station_ID = Ridership.Station_ID
                where (Station_Name like ?) and (Type_of_Day = 'U');
                """
    
    dbCursor.execute(total, [name])
    total_ridership = dbCursor.fetchone()

    dbCursor.execute(weekday, [name])
    weekday_count = dbCursor.fetchone()

    dbCursor.execute(saturday, [name])
    saturday_count = dbCursor.fetchone()

    dbCursor.execute(sunday_holiday, [name])
    sunday_holiday_count = dbCursor.fetchone()

    # print out info from above SQL queries
    print("Percentage of ridership for the Clark/Lake station:")
    print("\t" + "Weekday ridership: " + f"{weekday_count[0]:,}" + " " + f"({((weekday_count[0] / total_ridership[0]) * 100):.2f}%)")
    print("\t" + "Saturday ridership: " + f"{saturday_count[0]:,}" + " " + f"({((saturday_count[0] / total_ridership[0]) * 100):.2f}%)")
    print("\t" + "Sunday/holiday ridership: " + f"{sunday_holiday_count[0]:,}" + " " + f"({((sunday_holiday_count[0] / total_ridership[0]) * 100):.2f}%)")
    print("\t" + "Total ridership: " + f"{total_ridership[0]:,}")


def command_3(dbConn):
    ''' command_3() finds the toal ridership on weekdays for each station, 
        and calculates the percent each station accounts for from the total
        number of riders across all stations '''
    
    dbCursor = dbConn.cursor()
    print()

    # find total ridership on weekdays
    total_ridership = """select sum(Num_Riders)
                from Ridership
                join Stations
                on Stations.Station_ID = Ridership.Station_ID
                where Type_of_Day = 'W';
                """
    
    # find number of weekday riders from each individual station 
    station_ridership = """select Station_Name, sum(Num_Riders)
                from Ridership
                join Stations
                on Stations.Station_ID = Ridership.Station_ID
                where Type_of_Day = 'W'
                group by Station_Name
                order by sum(Num_Riders) desc;
                """
    
    dbCursor.execute(total_ridership)
    total = dbCursor.fetchone()

    dbCursor.execute(station_ridership)
    stations = dbCursor.fetchall()

    print("Ridership on Weekdays for Each Station")

    # print total number of riders for each station and the 
    # percent each station makes of the total ridership
    for s in stations:
        print("\t" + str(s[0]) + " : " + f"{s[1]:,}" + " " + f"({((s[1] / total[0]) * 100):.2f}%)")


def command_4(dbConn):
    ''' command_4() takes a line color and direction and finds all the 
        stops in that direction for the chosen line color. The direction
        and color and case-insensitive and printed in ascending order '''
    
    dbCursor = dbConn.cursor()
    print()

    # get color to search for
    input_color = input("Enter a line color (e.g. Red or Yellow): ")
    input_color = input_color.capitalize()

    color_check = """select Color
                from Lines
                where Color = ?;
                """

    dbCursor.execute(color_check, [input_color])
    color = dbCursor.fetchone()

    # see if the color is a valid line color
    if (color is None):
        print("**No such line...")
        return
    
    # get direction to search for 
    input_direction = input("Enter a direction (N/S/W/E): ")
    input_direction = input_direction.capitalize()

    stations_direction = """select Stop_Name, ADA
                        from Stops
                        join StopDetails
                        on Stops.Stop_ID = StopDetails.Stop_ID
                        join Lines
                        on Lines.Line_ID = StopDetails.Line_ID
                        where (Color = ?) and (Direction = ?)
                        order by Stop_Name asc;
                        """
    
    dbCursor.execute(stations_direction, [input_color, input_direction])
    result = dbCursor.fetchall()

    # if the query is empty, then the line color doesn't run in
    # that direction or the given direction was not N/E/W/S
    if (len(result) == 0):
        print("**That line does not run in the direction chosen...")
        return
    
    for res in result:
        handicap = ""
        # see if the stop is handicap accessible or not
        if (res[1] == 1):
            handicap = "(handicap accessible)"
        else:
            handicap = "(not handicap accessible)"

        # print out info from above query
        print("\t" + str(res[0]) + " : " + "direction = " + input_direction + " " + handicap)


def command_5(dbConn):
    ''' command_5() finds the number of stops for each line color,
        seperated by the direction they are going in, and the percent
        that each color and direction accounts for out of the total
        number of stops '''
    
    dbCursor = dbConn.cursor()
    print()

    # get total number of stops
    total_stops = """select distinct count(Stop_Name)
                    from Stops;
                    """

    dbCursor.execute(total_stops)
    total = dbCursor.fetchone()

    # find the number of stops for each color seperated by direction
    color_and_direction_stops = """select Color, Direction, count(Stops.Stop_ID)
                                from Stops
                                join StopDetails
                                on Stops.Stop_ID = StopDetails.Stop_ID
                                join Lines
                                on Lines.Line_ID = StopDetails.Line_ID
                                group by Color, Direction
                                order by Color asc, Direction asc;
                                """

    dbCursor.execute(color_and_direction_stops)
    result = dbCursor.fetchall()

    print("Number of Stops For Each Color By Direction")
    # print out info from above query
    for res in result:
        print("\t" + str(res[0]) + " going " + str(res[1]) + " : " + str(res[2]) + " " + f"({((res[2] / total[0]) * 100):.2f}%)")


def station_in_data(dbConn, station_name):
    dbCursor = dbConn.cursor()

    name_check = """select distinct Station_Name
                    from Stations
                    where Station_Name like ?;
                    """
    
    dbCursor.execute(name_check, [station_name])
    names = dbCursor.fetchall()

    if (len(names) == 0):
        print("**No station found...")
        return False
    elif (len(names) > 1):
        print("**Multiple stations found...")
        return False
    else:
        return True


def command_6(dbConn):
    ''' command_6() finds the total ridership for each year for the
        station that is inputted. After this, the user can plot the
        data as well and if they choose to, the data gets put onto a
        graph '''
    
    dbCursor = dbConn.cursor()
    print()

    ### MAYBE MAKE THIS A FUNCTION ###########################################
    name = input("Enter a station name (wildcards _ and %): ")

    # check to see if the input name exists in database
    if (not station_in_data(dbConn, name)):
        return
    # name_check = """select distinct Station_Name
    #                 from Stations
    #                 where Station_Name like ?;
    #                 """
    
    # dbCursor.execute(name_check, [name])
    # names = dbCursor.fetchall()

    # if (len(names) == 0):
    #     print("**No station found...")
    #     return
    # elif (len(names) > 1):
    #     print("**Multiple stations found...")
    #     return
    ### MAYBE MAKE THIS A FUNCTION ###########################################


    total_ridership = """select strftime('%Y', Ride_Date) as Year, sum(Num_Riders), Station_Name
                        from Ridership
                        join Stations
                        on Stations.Station_ID = Ridership.Station_ID
                        where Station_Name like ?
                        group by Year
                        order by Year asc;
                        """

    dbCursor.execute(total_ridership, [name])
    result = dbCursor.fetchall()
    
    print("Yearly Ridership at " + str(result[0][2]))
    # print the info from above query
    for res in result:
        print("\t" + str(res[0]) + " : " + f"{res[1]:,}")

    plot_choice = input("Plot? (y/n) ")

    if (plot_choice == "y"): # only lower case "y", but this could be upper case maybe too
        # create x and y coordinate points
        x = []
        y = []

        # append year to x axis, total riders to y axis
        for r in result:
            x.append(r[0])
            y.append(r[1])

        # label the axes and title
        figure.xlabel("Year")
        figure.ylabel("Number of Riders")
        figure.title("Yearly Ridership at " + str(result[0][2]) + " Station")

        # turn off interactive graph and plot the data
        figure.ioff()
        figure.plot(x, y)
        figure.show()


def command_7(dbConn):
    ''' command_7() finds the total ridership for each month for a
        given station and year. After this, the user can plot the
        data as well and if they choose to, the data gets put onto a
        graph '''
    
    dbCursor = dbConn.cursor()
    print()

    ### MAYBE MAKE THIS A FUNCTION ###########################################
    name = input("Enter a station name (wildcards _ and %): ")

    # check that the inputted station exists 
    if (not station_in_data(dbConn, name)):
        return
    # name_check = """select distinct Station_Name
    #                 from Stations
    #                 where Station_Name like ?;
    #                 """
    
    # dbCursor.execute(name_check, [name])
    # names = dbCursor.fetchall()

    # if (len(names) == 0):
    #     print("**No station found...")
    #     return
    # elif (len(names) > 1):
    #     print("**Multiple stations found...")
    #     return
    ### MAYBE MAKE THIS A FUNCTION ###########################################

    year = input("Enter a year: ")

    monthly_ridership = """select strftime('%m', Ride_Date) as Month, strftime('%Y', Ride_Date) as Year ,sum(Num_Riders), Station_Name
                        from Ridership
                        join Stations
                        on Stations.Station_ID = Ridership.Station_ID
                        where (Station_Name like ?) and (Year like ?)
                        group by Month
                        order by Ride_Date asc;
                        """
    
    dbCursor.execute(monthly_ridership, [name, year])
    result = dbCursor.fetchall()

    print("Monthly Ridership at " + str(result[0][3]) + " for " + year)
    # print the info from above query 
    for res in result:
        print("\t" + str(res[0]) + "/" + str(res[1]) + " : " + f"{res[2]:,}")

    plot_choice = input("Plot? (y/n) ")

    if (plot_choice == "y"): # only lower case "y", but this could be upper case maybe too
        # create x and y coordinate points
        x = []
        y = []

        # append Month to x axis, total monthly riders to y axis
        for r in result:
            x.append(r[0])
            y.append(r[2])

        # add labels to axes and title
        figure.xlabel("Month")
        figure.ylabel("Number of Riders")
        figure.title("Monthly Ridership at " + str(result[0][3]) + " Station" + " " + "(" + year + ")")

        # turn off interactive graph and plot the data
        figure.ioff()
        figure.plot(x, y)
        figure.show()


def command_8(dbConn):
    ''' command_8() takes 2 station names and a year and finds the 
        total ridership for each day during that year for each of the 
        stations. After this, the user can plot the data as well and 
        if they choose to, the data gets put onto a graph '''
    
    dbCursor = dbConn.cursor()
    print()

    year = input("Year to compare against? ")
    print()

    ### MAYBE MAKE THIS A FUNCTION ###########################################
    name1 = input("Enter station 1 (wildcards _ and %): ")

    # check to make sure the first input name exists
    if (not station_in_data(dbConn, name1)):
        return
    
    # name_check1 = """select distinct Station_Name
    #                 from Stations
    #                 where Station_Name like ?;
    #                 """
    
    # dbCursor.execute(name_check1, [name1])
    # names1 = dbCursor.fetchall()

    # if (len(names1) == 0):
    #     print("**No station found...")
    #     return
    # elif (len(names1) > 1):
    #     print("**Multiple stations found...")
    #     return
    ### MAYBE MAKE THIS A FUNCTION ###########################################

    print()

    ### MAYBE MAKE THIS A FUNCTION ###########################################
    name2 = input("Enter station 2 (wildcards _ and %): ")

    # check to make sure the second input name exists
    if (not station_in_data(dbConn, name2)):
        return
    
    # name_check2 = """select distinct Station_Name
    #                 from Stations
    #                 where Station_Name like ?;
    #                 """
    
    # dbCursor.execute(name_check2, [name2])
    # names2 = dbCursor.fetchall()

    # if (len(names2) == 0):
    #     print("**No station found...")
    #     return
    # elif (len(names2) > 1):
    #     print("**Multiple stations found...")
    #     return
    ### MAYBE MAKE THIS A FUNCTION ###########################################

    print()

    # get the total riders for each day for the first station
    station_1_sum = """select Ridership.Station_ID, Station_Name, Num_Riders, date(Ride_Date)
                    from Ridership
                    join Stations
                    on Stations.Station_ID = Ridership.Station_ID
                    where (strftime('%Y', Ride_Date) like ?) and (Station_Name like ?)
                    order by Ride_Date asc;
                    """

    dbCursor.execute(station_1_sum, [year, name1])
    result1 = dbCursor.fetchall()
    
    # get the total riders for each day for the second station
    station_2_sum = """select Ridership.Station_ID, Station_Name, Num_Riders, date(Ride_Date)
                    from Ridership
                    join Stations
                    on Stations.Station_ID = Ridership.Station_ID
                    where (strftime('%Y', Ride_Date) like ?) and (Station_Name like ?)
                    order by Ride_Date asc;
                    """

    dbCursor.execute(station_2_sum, [year, name2])
    result2 = dbCursor.fetchall()

    print("Station 1: " + str(result1[0][0]) + " " + str(result1[0][1]))
    i = 0
    for res1 in result1:
        # print the first 5 and last 5 entries for the first station query
        if ((i < 5) or (i > (len(result1) - 6))):
            print("\t" + str(res1[3]) + " " + str(res1[2]))
        i += 1

    print("Station 2: " + str(result2[0][0]) + " " + str(result2[0][1]))
    i = 0
    for res2 in result2:
        # print the first 5 and last 5 entries for the second station query
        if ((i < 5) or (i > (len(result2) - 6))):
            print("\t" + str(res2[3]) + " " + str(res2[2]))
        i += 1

    print()
    plot_choice = input("Plot? (y/n) ")

    if (plot_choice == "y"): # only lower case "y", but this could be upper case maybe too
        # create x and y coordinate points for both data sets
        x1 = []
        y1 = []

        x2 = []
        y2 = []

        day = 0
        # append the number of days to the x axis, and the total riders
        # total riders each day to the y axis for both data sets 
        for r1 in result1:
            x1.append(day)
            y1.append(r1[2])
            day += 1

        day = 0
        for r2 in result2:
            x2.append(day)
            y2.append(r2[2])
            day += 1


        # label the axis and title
        figure.xlabel("Day")
        figure.ylabel("Number of Riders")
        figure.title("Ridership Each Day of " + year)

        # turn off interactive graph and plot both data sets
        figure.ioff()
        figure.plot(x1, y1)
        figure.plot(x2, y2)

        # create a legend in the top right corner to denote which data is which
        figure.legend([str(result1[0][1]), str(result2[0][1])])
        figure.show()


def command_9(dbConn):
    dbCursor = dbConn.cursor()
    print()

    latitude = input("Enter a latitude: ")
    latitude = float(latitude)
    latitude = round(latitude, 3)

    if (latitude < 40 or latitude > 43):
        print("**Latitude entered is out of bounds...")
        return
    
    longitude = input("Enter a longitude: ")
    longitude = float(longitude)
    longitude = round(longitude, 3)

    if (longitude < -88 or longitude > -87):
        print("**Longitude entered is out of bounds...")
        return
    
    square_mile = """select Stop_Name, Latitude, Longitude
                    from Stops
                    where ((Latitude - (1/69) > ?) or (Latitude + (1/69) < ?)) and ((Longitude - (1/51) > ?) or (Longitude + (1/51) < ?))
                    order by Stop_Name asc;
                    """

    dbCursor.execute(square_mile, [latitude, latitude, longitude, longitude])
    result = dbCursor.fetchall()

    print("List of Stations Within a Mile")
    for res in result:
        print("\t" + str(res[0]) + " : " + "(" + str(res[1]) + ", " + str(res[2]) + ")")



def main():
    dbConn = sqlite3.connect("CTA_L_daily_ridership.db")
    general_stats(dbConn)

    done = False
    while (not done):
        print() # might need a print to seperate stats from command loop
        choice = input("Please enter a command (1-9, x to exit): ")

        if (choice == "x"):
            done = True
        elif (choice == '1'):
            #print("In command 1")
            command_1(dbConn)
        elif (choice == '2'):
            #print("In command 2")
            command_2(dbConn)
        elif (choice == '3'):
            #print("In command 3")
            command_3(dbConn)
        elif (choice == '4'):
            #print("In command 4")
            command_4(dbConn)
        elif (choice == '5'):
            #print("In command 5")
            command_5(dbConn)
        elif (choice == '6'):
            #print("In command 6")
            command_6(dbConn)
        elif (choice == '7'):
            #print("In command 7")
            command_7(dbConn)
        elif (choice == '8'):
            #print("In command 8")
            command_8(dbConn)
        elif (choice == '9'):
            #print("In command 9")
            command_9(dbConn)
        else:
            print("**Error, unknown command, try again...")

    print()
    #print("main function\n")

main()
