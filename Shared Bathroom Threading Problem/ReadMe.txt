** Run BathroomProblem.java to see the action

** I still contend that even with this very careful algorithm, this problem is susceptible to very close or identical inter-arrival times swapping entry orders.

** This implementation guards against that as much as it can without introducing artificial delay on the total order. 
This is done by controlling the wait time of each new user externally, and then starting them
after they have waiting. Each thread then calls enter. This reduces race conditions, and has the added benefit of reducing the overall number of concurrent
threads when measured instantaneously.

** Bathroom1.java will allow cutting of any arriving gender in front of the queue if they are not gender restricted from entering.
This means that zombies always cut and go directly in. men and woem will cut in front of each other
of the same sex user is holding up the opposite sex.  It uses locks and a male and a female condition for coordination.

** Bathroom2.java uses LockSupport park() and unpark() calls. No locks required. Returns in FIFO order

** Bathroom3.java  uses thread wait() and notify() which requires no locks. Returns in FIFO order

** Bathroom4.java  uses a re-entrant lock and a condition for each thread. (this is why signal() is adequate and signalAll() is not necessary Returns in FIFO order

** BathroomInterface.java enforces enter() and leave()
** Bathroom.java has all the mumboJumbo that would otherwise muddle up the implementations and also reduces duplication
 
** All strings are abstracted out into static strings class. (or as much as possible)

** Growler.java is a simple and convenient way of communicating to the user without having to worry about cleanup. I have used it over
the yeas in a number of assignments with not issues. It cleans intself up.

** Debug mode will open a panel with the exact order of service for all users in the same format as the input file. This facilitates testing using a file diffing 
tool to verify FIFO compliance.  It will also write a file out in this same format.

** View supoorts drag and drop file input.

** I am verifying the input as much as possible, and also do a check for legality whenever a user enters the restroom to verify
that illegal gender co-mingling is not occurring.
 