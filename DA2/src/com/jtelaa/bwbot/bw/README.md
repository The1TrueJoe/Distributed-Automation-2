# Bing Rewards Client Bot
This is the bot used to run the searches
### Details
* Obfuscated IP
* Mobile & PC Search
* Centralized query generation
* Automatic redemptions

### Structure

- [Main](main.java) Main
- [Utilities](util\) Utilities folder
    - [Remote Command Line Interface](RemoteCLI.java) CLI Process
    - [Web Tags](BWWebTages.java) Stores web tags for web scraper
    - [Controls](BWControls.java) System control this is Bing Rewards specific
- [System](sys\) Subsystems\Processes
    - [Account Info](AccInfo.java) Account information methods
    - [Redemption](Redeem.java) Redemption utility methods
    - [Searches](SearchSystem.java) Searching utility methods

