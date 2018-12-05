# nexmotest

# Way - 1 (BASH)
##Pre Requisite
Bash script requires JQ (https://stedolan.github.io/jq/) which is JSON parser for BASH
JQ can be installed by sudo apt-get install jq

Script runs and outputs three statistics as asked.

# Way - 2 (Java 8)
Driver.java can be run standalone and outputs three statistics as asked.

## Understanding Output
- The average price to "Send a message"
Average of "outbound.flatMobilePrice" across countries
- Country where we offer the most expensive "Rent a virtual phone number"
Maximum of "inbound.number.monthlyFee" where both features SMS & Voice are available
- List of coutries where we offer tollfree Inbound Numbers
List of countries where inbound.tollfree number is available

