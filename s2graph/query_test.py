import os, sys
import time
from random import randint
import httplib

ROWS = 10000
COLS = 10

label = "s2graph-friends"
host = "localhost"
route = "/graphs/getEdges"
headers = {"content-type": "application/json"}
batch_size = 1000

query = """
{
	"srcVertices": [{
		"serviceName": "s2graph-test",
		"columnName": "user_id",
		"id": %d
	}],
	"steps": [
      {
		"step": [{
			"label": "s2graph-friends",
			"direction": "out",
			"offset": 0,
			"limit": 10
		}]

	  }, 
      {
        "step": [
          {
            "label": "s2graph-friends",
            "direction": "out", 
            "offset": 0,
            "limit": 10
          }
         ]
      }, 
      {
        "step": [
          {
            "label": "s2graph-friends",
            "direction": "out", 
            "offset": 0,
            "limit": 10
          }
         ]
      }, 
      {
        "step": [
          {
            "label": "s2graph-friends",
            "direction": "out", 
            "offset": 0,
            "limit": 10
          }
         ]
      }
    ]
}
"""
def send(httpServ, payload):
        httpServ.request('POST', route, payload, headers)
        response = httpServ.getresponse()
        #print response.read()
        return response.status
        
httpServ = httplib.HTTPConnection(host, 9000)
httpServ.connect()
payload = query % int(sys.argv[1])
send(httpServ, payload)
httpServ.close()    
    
