# Jobcoin Mixer Specifications
The Mixer is responsible for distributing a User's coins across multiple addresses by generating temporary Mixer addresses assigned to User addresses for the purpose of processing deposit transactions and funnelling the User's BTC through a Mixer address to be pooled with other BTC to the Big House address. The original amount of coins deposited to a given Mixer address must then be redistributed and deposited back to the User's addresses provided.

The Mixer serves 3 primary functions:
1. Input list of User address and return new temp Mixer generated address. Mixer will assign and store the Mixer address to the associated User addresses to be referenced during the Mixer distribution process whenever a new deposit transaction is detected. The Mixer address must persist until after an actual deposit is made to the address and fully processed. 
   1. Note: If the Mixer addresses are not persisted, then any coin deposits made to an address are at risk of loss beyond recovery if Mixer service related issues occur before a deposit distribution process completes. 
   2. Likewise, the User addresses must be stored and assigned to the Mixer address in the event of a service failure occurring to ensure they can be correctly distributed from the Big House address to the User's addresses after a deposit has completed prior to distribution. 
2. Mixer polls the BTC network for new deposit transactions made to Mixer address(es). When a deposit to a Mixer address is detected, the Mixer will deposit the coins from the Mixer address to the designated Big House pool address. 
3. Mixer will then initiate the Jobcoin distribution process. The original User addresses will be retrieved using the associated Mixer address. The deposit amount recorded in the transaction will be transferred from the Big House address to the current Mixer address then incrementally distributed to the User's original accounts. Distribution will be optimized for speed and efficiency over cost in fees.


### Implementation Notes
* The Jobcoin Mixer was implemented via Java SpringBoot using Maven with JSON Object handling for REST calls and dependency injection for Mock unit testing.
* Due to limitations in Jobcoin API, any Big House related functionality is assumed to be handled outside the scope of the Mixer service.
* Jobcoin Mixer service consists of 3 main components:
  1. Mixer
  2. JobcoinHttpClient
  3. AddressGenerator
* Polling is emulated through MixerScannerTask(cronjob task) that queries the Jobcoin API - preset to every 15 seconds. Each scanner task checks each Jobcoin Address generated for any balance > 0, indicating a deposit has been made.
* The Mixer distribution method takes the initial deposited amount detected and arbitrarily divides the amount into smaller increments to be deposited across each address provided over multiple transactions based on the number of addresses divided by a preset "distributionInterval".
* A debug mode is available to generate *N* number of deposit addresses specified. 
  * This is enabled via debug set to true in the main application class and must be recompiled for changes to take effect.
* Unit testing has been setup and outlined for classes and methods that were considered relevant.
  * JobcoinHttpClientTest test methods can be referenced for working JUnit Mock test examples.
  * All other tests have been noted with TODO comment descriptions and would be implemented in a similar manner.
