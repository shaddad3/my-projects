#
# objecttier
#
# Builds Lobbyist-related objects from data retrieved through 
# the data tier.
#
# Original author: Ellen Kidane
# Student author: Sammy Haddad
#
import datatier


##################################################################
#
# Lobbyist:
#
# Constructor(...)
# Properties:
#   Lobbyist_ID: int
#   First_Name: string
#   Last_Name: string
#   Phone: string
#
class Lobbyist:
   def __init__(self, id, first_name, last_name, phone_num):
      self._Lobbyist_ID = id
      self._First_Name = first_name
      self._Last_Name = last_name
      self._Phone = phone_num


   @property
   def Lobbyist_ID(self):
      return self._Lobbyist_ID
   
   @property
   def First_Name(self):
      return self._First_Name
   
   @property
   def Last_Name(self):
      return self._Last_Name
   
   @property
   def Phone(self):
      return self._Phone
   

##################################################################
#
# LobbyistDetails:
#
# Constructor(...)
# Properties:
#   Lobbyist_ID: int
#   Salutation: string
#   First_Name: string
#   Middle_Initial: string
#   Last_Name: string
#   Suffix: string
#   Address_1: string
#   Address_2: string
#   City: string
#   State_Initial: string
#   Zip_Code: string
#   Country: string
#   Email: string
#   Phone: string
#   Fax: string
#   Years_Registered: list of years
#   Employers: list of employer names
#   Total_Compensation: float
#
class LobbyistDetails:
   def __init__(self, id, sal, first_name, mid_initial, last_name, suffix, 
                add_1, add_2, city, state_initial, zip, country, email,
               phone_num,fax, years_list, employers_list, total_comp):
      self._Lobbyist_ID = id
      self._Salutation = sal
      self._First_Name = first_name
      self._Middle_Initial = mid_initial
      self._Last_Name = last_name
      self._Suffix = suffix
      self._Address_1 = add_1
      self._Address_2 = add_2
      self._City = city
      self._State_Initial = state_initial
      self._Zip_Code = zip
      self._Country = country
      self._Email = email
      self._Phone = phone_num
      self._Fax = fax
      self._Years_Registered = years_list
      self._Employers = employers_list
      self._Total_Compensation = total_comp


   @property
   def Lobbyist_ID(self):
      return self._Lobbyist_ID
   
   @property
   def Salutation(self):
      return self._Salutation
   
   @property
   def First_Name(self):
      return self._First_Name
   
   @property
   def Middle_Initial(self):
      return self._Middle_Initial
   
   @property
   def Last_Name(self):
      return self._Last_Name
   
   @property
   def Suffix(self):
      return self._Suffix
   
   @property
   def Address_1(self):
      return self._Address_1
   
   @property
   def Address_2(self):
      return self._Address_2
   
   @property
   def City(self):
      return self._City
   
   @property
   def State_Initial(self):
      return self._State_Initial
   
   @property
   def Zip_Code(self):
      return self._Zip_Code
   
   @property
   def Country(self):
      return self._Country
   
   @property
   def Email(self):
      return self._Email
   
   @property
   def Phone(self):
      return self._Phone
   
   @property
   def Fax(self):
      return self._Fax
   
   @property
   def Years_Registered(self):
      return self._Years_Registered
   
   @property
   def Employers(self):
      return self._Employers
   
   @property
   def Total_Compensation(self):
      return self._Total_Compensation
   

##################################################################
#
# LobbyistClients:
#
# Constructor(...)
# Properties:
#   Lobbyist_ID: int
#   First_Name: string
#   Last_Name: string
#   Phone: string
#   Total_Compensation: float
#   Clients: list of clients
#
class LobbyistClients:
   def __init__(self, id, first_name, last_name, phone_num, total_comp, client_list):
      self._Lobbyist_ID = id
      self._First_Name = first_name
      self._Last_Name = last_name
      self._Phone = phone_num
      self._Total_Compensation = total_comp
      self._Clients = client_list


   @property
   def Lobbyist_ID(self):
      return self._Lobbyist_ID
   
   @property
   def First_Name(self):
      return self._First_Name
   
   @property
   def Last_Name(self):
      return self._Last_Name
   
   @property
   def Phone(self):
      return self._Phone
   
   @property
   def Total_Compensation(self):
      return self._Total_Compensation
   
   @property
   def Clients(self):
      return self._Clients


##################################################################
# 
# num_lobbyists:
#
# Returns: number of lobbyists in the database
#           If an error occurs, the function returns -1
#
def num_lobbyists(dbConn):
   total_lobbyists = """select count(Lobbyist_ID)
                        from LobbyistInfo;
                        """
   
   result = datatier.select_one_row(dbConn, total_lobbyists)

   # if nothing is in result, return -1, else return the count of the lobbyist_id's
   if (result is None): return -1
   return result[0]


##################################################################
# 
# num_employers:
#
# Returns: number of employers in the database
#           If an error occurs, the function returns -1
#
def num_employers(dbConn):
   total_employers = """select count(Employer_ID)
                        from EmployerInfo;
                        """
   
   result = datatier.select_one_row(dbConn, total_employers)
   
   # if nothing is in result, return -1, else return the count of the employer_id's
   if (result is None): return -1
   return result[0]


##################################################################
# 
# num_clients:
#
# Returns: number of clients in the database
#           If an error occurs, the function returns -1
#
def num_clients(dbConn):
   total_clients = """select count(Client_ID)
                        from ClientInfo;
                        """
   
   result = datatier.select_one_row(dbConn, total_clients)
   
   # if nothing is in result, return -1, else return the count of the client_id's
   if (result is None): return -1 
   return result[0]


##################################################################
#
# get_lobbyists:
#
# gets and returns all lobbyists whose first or last name are "like"
# the pattern. Patterns are based on SQL, which allow the _ and % 
# wildcards.
#
# Returns: list of lobbyists in ascending order by ID; 
#          an empty list means the query did not retrieve
#          any data (or an internal error occurred, in
#          which case an error msg is already output).
#
def get_lobbyists(dbConn, pattern):
   sql_lobbyists = """select Lobbyist_ID, First_Name, Last_Name, Phone
                  from LobbyistInfo
                  where First_Name like ? or Last_Name like ?
                  order by Lobbyist_ID asc;
                  """
   
   result = datatier.select_n_rows(dbConn, sql_lobbyists, [pattern, pattern])

   # if sql query is empty, return an empty list
   if result is None:
      return []

   # create a list to store the lobbyist objects
   lobbyists = []
   for res in result:
      # for each row from the result, create a lobbyist object and store the info
      lob = Lobbyist(res[0], res[1], res[2], res[3])
      lobbyists.append(lob)
   
   # return the list of lobbyist objects
   return lobbyists


##################################################################
#
# get_lobbyist_details:
#
# gets and returns details about the given lobbyist
# the lobbyist id is passed as a parameter
#
# Returns: if the search was successful, a LobbyistDetails object
#          is returned. If the search did not find a matching
#          lobbyist, None is returned; note that None is also 
#          returned if an internal error occurred (in which
#          case an error msg is already output).
#
def get_lobbyist_details(dbConn, lobbyist_id):
   sql_lob_details = """select LobbyistInfo.Lobbyist_ID, Salutation, First_Name, Middle_Initial, 
                        Last_Name, Suffix, Address_1, Address_2, City,
                        State_Initial, ZipCode, Country, Email, Phone, Fax, sum(Compensation_Amount)
                        from LobbyistInfo
                        left join Compensation
                        on Compensation.Lobbyist_ID = LobbyistInfo.Lobbyist_ID
                        where LobbyistInfo.Lobbyist_ID = ?;
                        """
   
   lob_details = datatier.select_one_row(dbConn, sql_lob_details, [lobbyist_id])
   
   # if sql query is empty, return None
   if ((lob_details[0] is None) or (lob_details[0] == ())): 
      return None
   
   sql_years = """select Year
                  from LobbyistYears
                  where Lobbyist_ID = ?
                  order by Year asc;
                  """
   
   lob_years = datatier.select_n_rows(dbConn, sql_years, [lobbyist_id])

   # create a list of the years for each lobbyist
   years = []
   for year in lob_years:
      # for each row in the years query, store it in the years list
      years.append(year[0])

   sql_employers = """select distinct(Employer_Name)
                  from EmployerInfo
                  join LobbyistAndEmployer
                  on LobbyistAndEmployer.Employer_ID = EmployerInfo.Employer_ID
                  where Lobbyist_ID = ?
                  order by Employer_Name asc;
                  """
   
   lob_employers = datatier.select_n_rows(dbConn, sql_employers, [lobbyist_id])
   
   # create a list of the employers for each lobbyist
   employers = []
   for employer in lob_employers:
      # for each row in the employers query, store it in the employers list
      employers.append(employer[0])


   sql_total_comp = """select sum(Compensation_Amount)
                     from Compensation
                     where Lobbyist_ID = ?;
                     """
   
   lob_total_comp = datatier.select_one_row(dbConn, sql_total_comp, [lobbyist_id])

   total_comp =  0
   # if the lobbyist has no compensation, set total comp to 0
   if (lob_total_comp[0] is None or lob_total_comp[0] == ()): 
      total_comp = 0
   # otherwise, set the total comp to the result of the compensation query
   else: 
      total_comp = lob_total_comp[0]
   
   # after getting all the results from each query, create a lobbyist details object 
   # and store the info in the correct spot according to the constructor
   lobbyistObject = LobbyistDetails(lob_details[0], lob_details[1], lob_details[2], lob_details[3],
                                    lob_details[4], lob_details[5], lob_details[6], lob_details[7], 
                                    lob_details[8], lob_details[9], lob_details[10], lob_details[11],
                                    lob_details[12], lob_details[13], lob_details[14], years, employers, 
                                    total_comp)
   
   # return the lobbyist details object
   return lobbyistObject
   

##################################################################
#
# get_top_N_lobbyists:
#
# gets and returns the top N lobbyists based on their total 
# compensation, given a particular year
#
# Returns: returns a list of 0 or more LobbyistClients objects;
#          the list could be empty if the year is invalid. 
#          An empty list is also returned if an internal error 
#          occurs (in which case an error msg is already output).
#
def get_top_N_lobbyists(dbConn, N, year):
   # if N is not postive, return an empty list
   # if (N <= 0): 
   #    return []

   sql_lob_info = """select LobbyistInfo.Lobbyist_ID, First_Name, Last_Name, Phone, sum(Compensation_Amount)
                     from LobbyistInfo
                     join Compensation
                     on Compensation.Lobbyist_ID = LobbyistInfo.Lobbyist_ID
                     where strftime('%Y', Period_Start) like ?
                     group by LobbyistInfo.Lobbyist_ID
                     order by sum(Compensation_Amount) desc
                     limit ?;
                     """
   
   lob_info = datatier.select_n_rows(dbConn, sql_lob_info, [year, N])

   # if the above query is empty, return an empty list
   if (lob_info is None):
      return []

   # create a list to store the lobbyist client objects
   lobbyistClient = []
   for lob in lob_info:

      sql_client_id = """select ClientInfo.Client_ID
                        from ClientInfo
                        join Compensation
                        on Compensation.Client_ID = ClientInfo.Client_ID
                        where strftime('%Y', Period_Start) like ? and
                        Compensation.Lobbyist_ID like ?
                        group by ClientInfo.Client_ID;
                        """
      
      client_IDs = datatier.select_n_rows(dbConn, sql_client_id, [year, lob[0]])

      # create a list to store the clients for each lobbyist
      clients = []
      for id in client_IDs:

         sql_clients = """select Client_Name
                        from ClientInfo
                        where Client_ID = ?;
                        """
         
         # add each client name to the client list for that lobbyist for each client_id 
         # associated with that lobbyist in the year specified before 
         client = datatier.select_one_row(dbConn, sql_clients, [id[0]])
         clients.append(client[0])

      # once all the clients for the lobbyist are in the list, sort it
      clients.sort()

      # create a lobbyist clients object and store the info from the above queries
      lob_client = LobbyistClients(lob[0], lob[1], lob[2], lob[3], lob[4], clients)
      lobbyistClient.append(lob_client)

   # return the list of lobbyist client objects
   return lobbyistClient


##################################################################
#
# add_lobbyist_year:
#
# Inserts the given year into the database for the given lobbyist.
# It is considered an error if the lobbyist does not exist (see below), 
# and the year is not inserted.
#
# Returns: 1 if the year was successfully added,
#          0 if not (e.g. if the lobbyist does not exist, or if
#          an internal error occurred).
#
def add_lobbyist_year(dbConn, lobbyist_id, year):
   sql_check_lobbbyist = """select Lobbyist_ID
                           from LobbyistInfo
                           where Lobbyist_ID = ?;
                           """
   
   lob_exists = datatier.select_one_row(dbConn, sql_check_lobbbyist, [lobbyist_id])
   
   # if the above query is empty, return 0 since the year was not successfully added
   if lob_exists is None or lob_exists == ():
      return 0

   sql_insert_year = """insert into LobbyistYears(Lobbyist_ID, Year)
                     values(?, ?);
                     """
   
   rows_modified = datatier.perform_action(dbConn, sql_insert_year, [lobbyist_id, year])

   # if the # of rows modified is not 1, return 0 because some error occured 
   if rows_modified != 1:
      return 0
   
   # otherwise, return the # of rows modified, which will be 1
   return rows_modified


##################################################################
#
# set_salutation:
#
# Sets the salutation for the given lobbyist.
# If the lobbyist already has a salutation, it will be replaced by
# this new value. Passing a salutation of "" effectively 
# deletes the existing salutation. It is considered an error
# if the lobbyist does not exist (see below), and the salutation
# is not set.
#
# Returns: 1 if the salutation was successfully set,
#          0 if not (e.g. if the lobbyist does not exist, or if
#          an internal error occurred).
#
def set_salutation(dbConn, lobbyist_id, salutation):
   sql_check_lobbbyist = """select Lobbyist_ID
                           from LobbyistInfo
                           where Lobbyist_ID = ?;
                           """
   
   lob_exists = datatier.select_one_row(dbConn, sql_check_lobbbyist, [lobbyist_id])
   
   # if the above query is empty, return 0 since the salutation was not successfully set
   if lob_exists is None or lob_exists == ():
      return 0
   
   sql_update_salutation = """update LobbyistInfo
                              set Salutation = ?
                              where Lobbyist_ID = ?;
                              """
   
   rows_modified = datatier.perform_action(dbConn, sql_update_salutation, [salutation, lobbyist_id])

   # if the # of rows modified is not 1, return 0 because some error occured
   if rows_modified != 1:
      return 0
   
   # otherwise, return the # of rows modified, which will be 1
   return rows_modified