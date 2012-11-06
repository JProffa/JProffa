#!/usr/bin/python
import subprocess as SP
import os
import sys

agent_jar = os.path.join(os.path.dirname(__file__), 'Profiler', 'target', 'Profiler-1.0-SNAPSHOT.jar')
target_jar = os.path.join(os.path.dirname(__file__), 'TestProject', 'target', 'TestProject-1.0-SNAPSHOT.jar')
command = ['java', '-javaagent:' + agent_jar, '-jar', target_jar]

print ' '.join(command)
print
SP.call(command)

if len(sys.argv) > 1:
	if sys.argv[1] == "True":
		sys.exit(0)
if os.name == "nt":
	print "Push ENTER to close."
	raw_input()
