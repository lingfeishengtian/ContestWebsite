# Version 1.0
Initial features added:

Homepage, Login Page, Admin, Team (Registration, Apepeals, Downloads, etc), Scoreboard

# Version 1.1
Additions

- Removed the PC2 from the WAR package to reduce space and increase security (moved to the configuration file as a path)
- Added many options to the configurations file
- Optimizations and Cleanup
- Moved project to Maven

# Version 1.2
Changes

- Changed the security system so that it longer logs IP
- There is no longer a need for the session tracker folder as sessions are stored in memory

## Revision 1.2.1
Changes and Bugfix

- Moved registration to a specific webpage that must be completed before accessing main pages
- Admin can now be accessed

## Revision 1.2.2
Slight Modifications and Bugfix

- Fixed the scoreboard not finding the index.html page
- Fixed slight error in the jsp page of scoreboard
- Removed JUnit dependency