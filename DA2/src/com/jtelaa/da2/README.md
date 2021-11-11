# Bing Rewards Search System
This set of applications builds on top of the bot framework to add new functionality
<p> Requires the use of at least two additional servers (Query Generation & Management)

### Details

* Bot
    * Local client application
* Director
    * Gateway to access the bot net
    * Accessed via a CLI application (director interface)
* Hypervisor Interface
    * Handles communcation to the hypervisor
* Scheduler
    * Schedules the bots on/off time

### Folder Structure

- [Bot](bot/) Individual bot
- [Director](director/) Manager for the DA2 system
- [Director Interface](director_interface/) CLI application to access the botnet
- [Query Generator](querygen/) Query Generator for the bots
- [Libraries](bwlib/) Bing Rewards Libraries