# Benchmark
Benchmark script for Titan and S2Graph

## Test Data
To make fair comparison between two solution, we will use following synthetic matrix data.


| Set Name | # of rows | # of cols |
| -- | -- | -- |
| **S3** | 10000000 | 10 | 


column will be chose from randint(1 ~ row size)  

following is pseodo code
```
for row in range(ROWS):
  for col in range(COLS):
    edge = (row, 'friend', Random.randint(0, ROWS))
```

## Test Criteria 
1. write performance: TBA
	2. Insert time.
	3. Delete time.
	4. Update time.
	5. Total data size.
2. read performance: TBA
	3. one step query
	4. two step query
		5. friend_of_friends: 10 -> 10 
	5. three step query
	6. four step query
	7. five step query
3. read while write
	4. read vs write : 90 vs 10
	5. read vs write : 80 vs 20
 	5. read vs write : 60 vs 40


## Test Environment
To be fair, we use same HBase cluster(5 region server) both for S2Graph and Titan. 
For Rest server, single instance of s2rest_play(s2graph) and rexster-server-2.6.0(titan-0.5.4) is used.

### Read Test Result
all single machine.

| Solution | Set Name | # of rows | # of cols | query | mean response time | query per second |
| -- | -- | -- | -- | -- | -- | -- |
| Titan | **S3** | 10000000 | 10 | friends(10) of friends(10) | 61.48ms | 246.5 |


### S2Graph

### Titan
![screen shot 2016-02-12 at 3 35 40 pm](https://cloud.githubusercontent.com/assets/1264825/13000624/a4ea2bc4-d19f-11e5-9767-bdbfc762d9a1.png)




