Objective :-
•  Build an availability manager that monitors the health of virtual machines and restarts any failed virtual machines on alternate host using an earlier cached version of the virtual machine.

Requirements
	The datacenter manager program should implement the following functionality
1. Collect statistics of each virtual machine and display them.
2. When a virtual machine fails, restart it on an alternate host. The virtual machine should be cloned from a snapshot. Periodically refresh the snapshots.
3. Setup alarms on virtual machine for power down. The alarms should signal virtual machine power downs.
