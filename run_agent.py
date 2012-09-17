import subprocess as SP
import os

agent_jar = os.path.join(os.path.dirname(__file__), 'Profiler', 'target', 'Profiler-1.0-SNAPSHOT.jar')
target_jar = os.path.join(os.path.dirname(__file__), 'Example', 'target', 'Example-1.0-SNAPSHOT.jar')
command = ['java', '-javaagent:' + agent_jar, '-jar', target_jar]

print ' '.join(command)
print
SP.call(command)

print "Push ENTER to close."
raw_input()
