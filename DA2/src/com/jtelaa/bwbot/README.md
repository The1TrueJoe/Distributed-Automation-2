# Bing Rewards Search System
This set of applications builds on top of the bot framework to add new functionality
<p> Requires the use of at least two additional servers (Query Generation & Management)

### Details

* Bot
    * Local client application
    * Requests and runs search queries
* Rewards Manager
    * Keeps track of all points
    * Handles redemption requests
* Query
    * Generates queries
    * Handles query requests

### Folder Structure

- [Bot Plugin](bw\) Individual bot plugin
- [Bing Rewards Manager](bw_manager\) Manager for the Bing Rewards system
- [Query Generator](querygen\) Query Generator for the bots
- [Libraries](bwlib\) Bing Rewards Libraries