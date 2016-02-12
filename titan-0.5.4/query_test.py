import os, sys
import urllib2

host = sys.argv[1]
user_id = sys.argv[2]
response = urllib2.urlopen("http://%s:8182/graphs/titan/tp/gremlin?script=f_of_f(%d)&load=[f_of_f]" % (host, int(user_id)))
print response.read()
