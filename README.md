## About

Self-host service for accounting personal financial transactions.

It operates with two concepts - nodes and transactions.
The node is an essence that accumulates funds, for example a card, cash, a wallet, debt/credit balance.
The transaction is a record of movement funds from node to another.
Such a simple model allows you to store all information about personal finances as flexibly as possible.

You can see example how one can use it in demo: http://crynick.ru (demo/demo credentials).

It should be noted that this is not a program for taking into account all small expenses
(although you can use it like that) but rather to store the overall picture of your finances on a wider scale.
The recording of all small expenses can be tiring, therefore consider to store only big amount transactions,
and squashing small ones.

## How to run

Download ```docker-compose.yml``` file from this repository, and run```docker-compose up -d```.
After that you should have access to service in browser by address <your local ip>:80 or just <your local ip>.
