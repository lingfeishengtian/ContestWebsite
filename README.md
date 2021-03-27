# Contest Website
The contest website is an updated UIL website that allows admin input of written scores and auto generates a score report based on the data provided and scraped results from PC^2.

## TODO
The website isn't perfect and has a many flaws.

- The security is ok for now, but can be reconsidered
- The method of returning html data is not ideal and should be redesigned
- Fix spelling errors, html errors, and minor details (for example, extrraneous commmas in the team page if there are less than 3 team members)
- Add more configuration options that would allow for configurations of the scoreboard and what information can be released
- Tighter integration with PC^2 (maybe starting the PC^2 timer automatically) (maybe display countdown to contest end on the website)
- Update the admin page to be able to edit configurations (possibly restrict admin to only a single session)
- Add a contest timer to the admin page
- Add a log off button

# Usage
The compiled file is a WAR file that should be exploded to modify configurations and use a different passwords file.

## Configurations
All configuration files are located in WEB-INF/data.

### config
- Administrator Password: set password for team0
- Local PC2 Location: the path of the pc2 location on the computer
- Contest Name: the name of the contest that should be displayed on the homepage

### contestStartDate.txt
This file contains date and time for the start of the competition, end of the competition, and end of the appeal date.

There is no start of the appeal date since the website starts accepting appeals right when the competition ends, all other appeals during the contest can be sent through PC^2.

### passwords.txt
This file contains the list of passwords for each team. The amount of accounts will be based on how many lines are in this file which corresponds to each team number.

## Downloads
In order to save space, all downloads are located in the downloads folder, but will not be provided. The downloads/downloads.txt file displays the files that are necessary to provide those without java installed a way to compete.

## Images
Replace the logo.png file with the logo of the school you are hosting the competition at.

## Secure Downloads
Make sure to change the ip of pc^2.

# Running the Competition
Ensure that you have everything in the Configurations section configured and ensure that you have tomcat configured. Then, you should enter written scores in the 0th team admin page.
