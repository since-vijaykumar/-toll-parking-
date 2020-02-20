Toll Parking Library
====================
What we need :
We would like you to build a Java API that meets the following requirements :

A toll parking contains multiple parking slots of different types :
 - the standard parking slots for sedan cars (gasoline-powered)
 - parking slots with 20kw power supply for electric cars
 - parking slots with 50kw power supply for electric cars

20kw electric cars cannot use 50kw power supplies and vice-versa.

Every Parking is free to implement is own pricing policy :
 - Some only bills their customer for each hour spent in the parking (nb hours * hour price)
 - Some other bill a fixed amount + each hour spent in the parking (fixed amount + nb hours * hour
	price)
In the future, there will be others pricing policies.


Cars of all types come in and out randomly, the API must :
 - Send them to the right parking slot or     refuse them if there is no slot (of the right type) left.
 - Mark the parking slot as Free when the car leaves it
 - Bill the customer when the car leaves.

What we want :
 - A Java API ready to use, tested, documented (how to build from sources, how to use the API).

