# Distributed-Automation

## Summary 

This set of applications is for a system made of up hosts connected to a centralized system for automation purposes. 
This contains the libraries and the basic framework. 

This botnet isn't a traditional style one. This isn't desgined to run on host located all around the world.
It is designed to virtualize thousands of hosts distributem them all around the globe and run tasks.

This botnet is not designed to be hidden on a computer. Disguising the traffic running between hosts and applications is not a priority. However, disguising outbound traffic heading to external destations does need to be distributed to between many differnet vpns. The name "Distributed Automation" comes from the fact that:
1. Bots will be <b>distributed</b> between different external IP addresses using VPN Gateways. See: [VPN Gateway](vpn-gateway/) for more details
2. Tasks will be <b>distributed</b> to these bots to run a different times throughout a set period (usually 24hrs). 
3. The bots run time will be <b>distributed</b> throught 24 hour intervals
4. All of the trafic comes from many different virtualized hosts <b>distributed</b>
5. This has some <b>distribution</b>/decentralization where different node groups (called franchises)

<I>Please Use Ethically</I>

## Definitions

> - <b>Framework:</b> Structure for the botnet and the base plugins run on top
> - <b>Plugin:</b> The applications that run on top of the bots in the botnet
> - <b>Franchise:></b> A subsystem node that can be functionally independent. 
## Folder Structure

### Source
- [Overall](DA2/) Source code for the project

### Applications
- [Director Server](DA2/src/com/jtelaa/da2/director/) CLI Interface into the network.
- [Director Client](da2_tools/director_interface/) Connect to the director
- [Scheduler](DA2/src/com/jtelaa/da2/scheduler/) Used to distribute vms over time. Also holds logging info
### Deployments
- [Server Config](deployments/) The deployment/configuration scripts
- [Database](database/) Structure for the database
- [VPN Gateways](vpn-gateway/) VPN Gateway Scripts
- [Franchisee Webserver](franchisee_webserver/) Server with info for the franchise. 
- [DA2 Tools](da2_tools/) Misc tools and programs to assist the botnet
### Documentation
- [Project Info](documentation/) Misc information on the project


## Instructions

Please see [deployment guide](DeploymentGuide.md) for detailed instructions

## Diagrams

### Adminstrative Structure
<img src='documentation/DA2 Administrative Structure.svg'>

### Franchise Structure
<img src='documentation/DA2 Franchise Structure.svg'>

## Misc

> Checkout Branch: ```git checkout -B <wip> main```