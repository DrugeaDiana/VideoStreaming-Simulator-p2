# Project POO: POOTV / OOPTV part 2
* Drugea Diana Ioana - 323CA

#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2)

## File Structure

* src/
    * inputclasses/ - cointains all the classes that are used to parse the input from the json
        * ActionInput -> retains the information for an action
        * ContainsInput -> retains the information of a Cointains field from the Filter section of an
                            action
        * CredentialsInput -> retains all the information about the credentials of the user
        * FiltersInput -> saves the information that's stored in the Filter field of an action
        * InputData -> class that parses the entire input
        * MovieInput -> saves the information about each film
        * SortingInput -> retains the information of a Sort field from the Filter section of an action
        * UserInput -> saves the information given at input about an User
    * movieclasses/ - contains all the classes that are used for Movies
        * Genre -> saves the name of each genre and how many times it appears in liked movies of an user
                    used for creating recommendations
        * Movies -> the class used to save the information about each movie and modify where is the case
    * tvclasses/ - classes that are used for the flow of the program
        * TV -> the core class of the program, which controls every other class and executes the main loop
                of the program
        * actionclasses/ - classes that are used to do and parse actions
            * ActionHandler -> parses the action from the ActionInput given and calls the specific visitor based
                                on its type.
            * ChangePageVisitor -> does every "change page" action on a given page (Uses Visitor pattern)
                                    while transfering/saving any information needed to be displayed as well
            * OnPageVisitor -> does every "on page" action on a given page (Uses Visitor pattern)
            * PageVisitor -> the interface used by ChangePageVisitor and OnPageVisitor (Uses Visitor pattern)
            * Visitable -> interface used by every class in the Page class family (Uses Visitor pattern)
            * Command -> interface used by the ChangePageCommand class (uses Command pattern)
            * ChangePageCommand -> does the actual changing of pages and implements the back function (called undo)
                                    as well.
            * PageHandler -> calls every ChangePageCommand to either execute or undo it, saves a history of all the
                            succesful "change page" actions done (uses Command pattern). Ignores every changes in pages
                            that are done because of wrong actions used on some pages/changes done when the user is not
                            logged in.
            * DatabaseActions -> handles every action (add/delete) done towards the MovieDatabase.
        * databaseclasses/ - classes that save all the users and movies
            * MovieCompDecrIncr -> comparator created for sorting movies in a decresing order rating wise and
                                    increasing order duration wise
            * MovieCompIncrDecr -> comparator created for sorting movies in an increasing order rating wise and
                                    decreasing order duration wise
            * MovieCompRatingDecr -> comparator created for sorting movies in a decresing order rating wise (bugs appeared
                                        from using lambda function because of conversion to int)
            * MovieDatabase -> saves all the movies in the program while also giving specific Movies/List of movies
                                depending on the method used (Similar to a mySQL database)
                                (Uses Singleton pattern)
            * UsersDatabase -> saves all the users in the program and gives specific users depending
                                on the method used (Similar to a mySQL database)
                                (Uses Singleton pattern)
            * Observer -> interface used by AddOberver and RemoveObserver (Observer pattern)
            * AddObserver -> Observer class that gets updated every time a new movie is added (handles the ADD notifications)
                                (Observer pattern)
            * RemoveObserver -> Observer class that gets updated every time a movie is deleted (handles the DELETE notifications)
                                    (Observer pattern)
        * pageclasses/ - classes that are representing each page of the program
            * Page -> the parent class of the family of classes
            * HomepageAuth -> the homepage when the user is logged in
            * HomepageNon -> the homepage when there's no active user logged in
            * LoginPage -> the Login page
            * MoviesPage -> the page that displays all the movies
            * RegisterPage -> the Register page
            * SeeDetailsPage -> the page that displays information about a specific movie
            * UpgradesPage -> the page that lets the user buy upgrades
    * userclasses/ - contains classes that deal with the users
        * User -> class that represents a user in the program
        * Credentials -> class that saves the information about an user's name, password, country and other
                            stuff.
        * Notification -> class that saves the information each notification has.
  * Main

## The program logic

* This part is from the README from the first part of the project, can be skipped

Once the program starts, it saves all the input information from the given json file into the
specific input classes. Afterwards, tv.Initialize() is called to save all the data from input
into the databases. Then, the main loop of the program (ie tv.mainloop()) starts, which goes
through every action given at input and calls the ActionHandler to them. This class then decides
which visitor to call for the specific action and, based on the activePage and activeUser variables
in the tv class, either executes the action or not.
For example, if we're at the start of the program, we would be on HomepageNon aka the homepage for when
there's no user logged in. Then, let's say we tell the program to change the page to login. ActionHandler
would call the ChangePage visitor for the HomepageNon and see wether or not we can actually move to the
login page. For this case, we can and it will change the activePage variable to the LoginPage saved in
the TV class. If we told the program, for example, to go from HomepageNon to MoviesPage, the
ChangePageVisitor would call the createOutputError method for the HomepageNon to display the specific
error in the output file.
The program basically just follows certain steps:
    -> Get the type of action
    -> Call the specific PageVisitor class
    -> Either do the action or create the output for the error

* Newly added things:

Whenever there's a change page action while the user is logged in, the PageHandler is called and saves every page
that has visited in such way in its history, so that we can go back between the pages that have been visited.
(PageHandler then calls ChangePageCommand)
When there's an action that modifies the database, the DatabaseAction class is called, which then will handle
everything concerning adding or removing a movie. If there's a new movie added, it will check if it exists, add it
to the movie database and then send a notification (By calling the AddObservers list from the MovieDatabase) to send
notification to every user that's subscribed. If a movie is removed, it will check if it exists, remove it, update
every purchased/watched/liked/rated lists of each user, gives back the tokens/free premium movies
and then notifies each of them (by calling RemoveObservers) that the movie has been removed.
For the subcribe action, the program basically creates a new AddObservers that has the user saved and adds it to the list
of them so it can be updated every time a movie in that genre is added.
At the end, for the recommendation part, the program makes a top for the premium user logged in with each genres they've
watched. Afterwards, it takes the list of movies that they haven't watched it yet, sort them based on their number of likes
and then grabs the most liked genre of the user, checks every non-watched movie and returns the first one it finds with the
highest likes. The process continues 'till we're out of genres to check.
Everything else is still the same from the first part, just that now the ChangePageVisitor calls the PageHandler instead
of doing the tv.setActivePage(x page) itself.

## Flow scheme

Main -> TV -> ActionHandler -> ChangePageVisitor -> every child of Page class
                                                 -> MovieDatabase (when moving to a MoviePage/SeeDetails page)
                                                 -> PageHandler -> ChangePageCommand -> every child of Page class
                            -> OnPageVisitor -> every child of Page class
                                            -> UsersDatabase
                                            -> MovieDatabase
                            -> DatabaseAction -> MovieDatabase -> Observer Classes
                            -> PageHandler (for back commands) -> ChangePageCommand -> every child of Page class
"->" = x calls y class

## Problems encountered

Surprisingly, this time it went quite smooth. There were some small difficulties with figuring out how
the back command works exactly and when to show and not show errors, but that was maybe like an extra 15 minutes
of changing some lines of code here and there.
