# gbot
This is a light TeamspeakServer 3 bot with preconfigurated scripts, wich helps to manage your TS-server.

---

### Currently implemented scripts:

* #### WelcomeMessageScript
Pokes each user with a welcome message, which belongs to a certain server group (usually guest) and has just connected for the first time. 
* #### UprankScript
The connection time of each user of a certain server group (usually guest) is stored in a sqllite database. As soon as the user has reached a certain time, a new server group (usually with more rights than guests) is assigned to him.
* #### FileDeleter
Every X hours all files of the file browser of a certain channel older than x hours will be deleted.

---

### Features to be implemented:

* #### Configurable scripts
Currently all available scripts are hard configured. In the future the scripts should be adjustable by the user via config files. On the same way further instances of the scripts should be easily realizable. 

* #### Teamspeak client as new interface
The teamspeak client will be implemented as a new interface for the bot besides to the server query. This should make it possible to send commands directly to the bot via the client, which in turn requires a parser.In addition, this interface should provide the basis for a future soundbot.
