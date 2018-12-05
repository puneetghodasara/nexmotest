#!/bin/bash

apiKey=$1
apiSecret=$2

if [[ $apiKey == "" || $apiSecret == "" ]]
then
	echo "Provide api-key and api-secret as arguments."
	exit -1
fi


####################################
# Get All countries by one of the API
####################################
countries=$(curl -sG 'https://rest.nexmo.com/account/get-full-pricing/outbound/sms' -d "api_key=$apiKey" -d "api_secret=$apiSecret" -d "type=sms" | \
       	jq .countries[].countryCode | sed "s/\"//g" | \
	sort -ui)
echo "Country codes are " $countries

# Create Temp directory to store pricing JSON files
uuid=$(date +%s)
echo "Generated Temp Directory is " $uuid
mkdir -p $uuid

####################################
# Fetch Pricing JSON for each country
####################################
s=$(date +%s)
for country in $countries
do
	filename=$uuid/$country.json
	echo "Processing" $country "country"
	curl -o $filename -sG https://rest.nexmo.com/pricing/messaging/$country/jsonp
done

e=$(date +%s)
echo "Fetched prices for all countries"
echo "[Time Taken] $(($e-$s))"
echo ""



####################################
# Task 1 : Fetch Average Price
####################################
s=$(date +%s)
prices=$(cat $uuid/*.json | jq ".messaging.outbound.flatMobilePrice" | grep -v null | sed "s/\"//g")

sum=$(echo $prices | sed "s/ /+/g" | bc -l)
count=$(echo $prices | sed "s/ /\n/g" | wc -l)
average=$(echo "scale=8;$sum/$count" | bc -l)
echo "Average Price is " $average
e=$(date +%s)
echo "[Time Taken] $(($e-$s))"
echo ""

####################################
# Task 2 : Get Highest Rent a virtual phone number
####################################
s=$(date +%s)
expensiveRentPhoneCountry=$(cat $uuid/* | \
	jq "select(.messaging.inbound.numbers)"  | \
	jq "select(.messaging.inbound.numbers[].features == \"SMS,VOICE\")" |  \
	jq -s "sort_by(.messaging.inbound.numbers[].monthlyFee | tonumber)" | \
	jq ".[-1].name" | \
	sed "s/\"//g")
echo "Expensive Incoming SMS+VOICE is at " $expensiveRentPhoneCountry
e=$(date +%s)
echo "[Time Taken] $(($e-$s))"
echo ""

####################################
# Task 3 : Print where tollfree inbound is present
####################################
s=$(date +%s)
countryName=$(cat $uuid/* | \
	jq "select(.messaging.inbound.numbers)"  | \
	jq "select(.messaging.inbound.numbers[].type == \"tollfree\")" | \
	jq ".name" | \
	sed "s/\"//g")
echo "Country where inbound tollfree available"  $countryName
e=$(date +%s)
echo "[Time Taken] $(($e-$s))"


rm $uuid/*
rmdir $uuid
