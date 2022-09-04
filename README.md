# Study project "Island"

Tasks for this project you can see: [here](src/main/resources/tasks.html).

#### In this project used follow technologies:

* Java core
* Maven
* Log4j
* Junit
* MySQL
* GitHub
* HTML
* JSON
* XML
* MarkDown

This program is a console application.  

**Project launch:**
```
$ java -jar ./build/Island-.jar
```

The basic logic was formed in the follow classes:   

* [Animal](src/main/java/ru/javarush/sergeyivanov/island/content_of_island/fauna/Animal.java)
* [ProcessorParam](src/main/java/ru/javarush/sergeyivanov/island/inicialization/ProcessorParam.java)
* [DataBaseProcessor](src/main/java/ru/javarush/sergeyivanov/island/inicialization/DataBaseProcessor.java)

Settings(data for default initialisation) can to storage in the:
1. Local database
2. JSON files
Database MySQL used for targets of study, upgrade skills.

In time starting program user choosing source getting data for default initialisation parameters. But runtime user can to change any parameter manually.

For demonstrations used screenshots from IDE Intellij IDEA.

**Inside, the database consists from table with settings of objects and table probability of eating animals 
and plants:**

<img src="src/main/resources/skrin_database_settings.png" alt="table of settings">

<img src="src/main/resources/skrin_database_probability.png" alt="table of probability">

In time launch application settings is assigning to fields.
User can change any settings from console API and affect to runtime app.

<img src="src/main/resources/communication_user.png" alt="user_communication">

Island represents class, what include two - dimensional array of locations.
All animals and plants are objects, what inheritance from abstracted classes
and implements interfaces.

After initialisation of settings from database, animals and plants randomly 
allocate to different locations.
All animals and plants storage inside these locations.
Each animal is performs all actions in its own separate thread, asynchronously.
Also this program use thread pool for restore amount plants in cycles.

**Result running threads for animals are reflecting in the log:**

<img src="src/main/resources/example_log.png" alt="Output to the log">


**Each animal has four behaviors:**
* to eat

>This behavior assumes random search animals and plants from its own ration in current location.
>If animal found food (object another animal), is calculating 
>random probability: will be eaten the animal or no. A probably eating plants 
>is 100 percent if present.
>Eating means increase satiety animal, object another animal(plant)
>is removing from location.

* to multiply;

>Each animal, if it hasn't multiplied in current cycle, is finding 
>a pair - another animal one type represent inside  its location. 
>And if it is another gender, is calculating random value - amount 
>children within set value for this animal. And new animals add to 
>current location, if don't exceeded limit of amount this type.  

* to change locations;

>In the end each cycle animals can to change whose locations.
>In this stage happens calculation indexes new location for each
>animal. Animal can move: up, down, left, right regarding its location,
>but can  to stay inside it's location.
>Animals move to new location, if limit storage animals it's type in the location is not exceed.

* to die.

>Animal can to die(to be removed from storage), if it's amount of cycles life 
>or satiety will be zero. Also another animal can to eat it's.

The specified behavior is described in the Animal class - [by reference](src/main/java/ru/javarush/sergeyivanov/island/content_of_island/fauna/Animal.java).

After request from user, statistic can to reflect to the console. 
It may by data about: ration of animals, settings, results of launches cycles, 
numbers animals:  

<img src="src/main/resources/output_result.jpg" alt="output info to console">

**Result of running program reflected to the console like statistic data.**

<img src="src/main/resources/result-running.png" alt="output statistic data">

