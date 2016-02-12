import os, sys
import time
from random import randint
import httplib

ROWS = 10000000
COLS = 10

import httplib
import os, sys
import time
import random
import json
from threading import Thread

headers = {"content-type": "text/plain"}
host = sys.argv[1]
port = 9000

def send(httpServ, payload):
    if payload:
        httpServ.request('POST', '/graphs/edges/bulkWithWait', payload, headers)
        response = httpServ.getresponse()
        response.read()
        httpServ.close()

        print response.status


def connect(url, port):
    httpServ = httplib.HTTPConnection(url, port)
    httpServ.connect()
    return httpServ

def process():
    st = time.time()
    idx = 0
    bs = 1000
    thread_count = 4
    buf = []
    conns = [connect(host, port) for i in range(thread_count)]
    for row in range(ROWS):
        for col_idx in range(COLS):
            idx = idx + 1
            col = random.randint(1, ROWS)
            ts = long(1000 * time.time())
            edge = "%d\tinsertBulk\tedge\t%d\t%d\ts2graph-friends\n" % (ts, row, col)
            buf.append(edge)

            if len(buf) % (bs * thread_count) == 0:
                #print "%s, %s" % (len(buf), [(i*bs, i*bs+bs-1) for i in range(thread_count)])
                threads = [Thread(target=send, args=(c, "".join(buf[(i*bs):(i*bs+bs-1)]))) for i, c in enumerate(conns)]

                [th.start() for th in threads]
                [th.join() for th in threads]

                print "%s: lines processed, time elapsed: %s" % (idx, time.time() - st)
                buf = []

    print "%s: process last buffer, time elapsed: %s" % (len(buf), time.time() - st)
    send(conns[0], "".join(buf))


print "start with:"
print time.time()
process()


print "finish"
print time.time()