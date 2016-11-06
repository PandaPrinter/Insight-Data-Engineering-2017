#!/usr/bin/env bash

cd src
javac -cp 'common-lang3.jar:opencsv-3.8.jar' *.java
java -cp '.:common-lang3.jar:opencsv-3.8.jar' Antifraud ../paymo_input/batch_payment.txt ../paymo_input/stream_payment.txt ../paymo_output/output1.txt ../paymo_output/output2.txt ../paymo_output/output3.txt


