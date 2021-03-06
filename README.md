# Benchmark
Benchmark script for Titan and S2Graph

## Test Data
To make fair comparison between two solution, we will use following synthetic matrix data.

| Set Name  | number of rows | number of cols |
| ------------- | ------------- | ------------- |
| s1  | 1000000  | 10 |
| s2  | 1000000  | 20 |
| s3  | 1000000  | 40 |
| s4  | 10000000  | 10 |



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

## Read Test Result
all single rest server instance.

### Titan(one server, 2 rexster instance, vuser 10)

| query | mean response time(ms) | query per second |
| ------------- | ------------- | ------------- |
| friends(10) | 63.4 | 317 |
| friends(10) -> friends(10) | 90.38 | 219.6 |
| friends(20) -> friends(10) |  |  |
| friends(40) -> friends(10) |  |  |



### S2Graph(one server, 1 rest instance, vuser 10)

| query | mean response time(ms) | query per second |
| ------------- | ------------- | ------------- |
| friends(10) | 4.5 | 4,352.6 |
| friends(10) -> friends(10) | 14.1 | 1,407.6 |
| friends(20) -> friends(10) | 22.1 | 898.1 |
| friends(40) -> friends(10) | 39.6 | 501 |


