# Context:
This project aim to model the movement of a crowd in a simplified manner using parallel execution flows. 
Since this project is part of the concurrent programming module, we primarily focused our efforts on identifying synchronization constraints and how to resolve them.

## Summary of Release 1:
Step one of the project (iterative version) served as the foundation for the subsequent steps. Firstly, it served as a point of comparison with the following steps that required the use of threads. More importantly, it prepared us to handle the main deadlock situation we might encounter, which is the inability to share a grid cell among people. If this situation arises, the older person takes the place, and the younger one restarts from their initial position.

## Summary of Release 2:
Step two involved transforming the initial solution into a multithreaded model where each person was represented by a different thread. 
To synchronize these threads, we used the locking principle to protect our critical section (access to the shared grid) with the synchronized keyword.
We also employed the monitor principle with its wait and notifyAll primitives to manage the deadlock situation. Indeed, when a cell is desired by two different threads, two scenarios are possible:
- The older thread settled in first, so the younger thread seeks to take the place. By comparing their respective "ages," the younger thread will go into a wait state using the wait primitive (inside a while loop, as recommended by the Oracle documentation due to the "spurious wakeup" problem that can occur) until any movement occurs on the grid, leading to the invocation of the notifyAll primitive. 
- The younger thread will then recheck if it is capable of taking that place.The younger thread settled in first, so the older one calls the method of the shared object (the grid), which resets the younger thread to the cell and then settles in.

## Summary of release 3:
Comparing the previous approaches (as discussed in the previous report), we observed that the multithreaded approach is more appealing when the risk of deadlock is low, meaning the grid is sufficiently large compared to the number of people occupying it. 
As the number of people increases while the grid size remains constant, the iterative model proves to be significantly more efficient than the multithreaded approach in terms of execution time.
Memory consumption, on the other hand, remains relatively equivalent.
Step three emerges as a compromise between these two approaches. It combines the strengths of each approach, mitigating deadlocks with the iterative model and harnessing parallelization with the multithreaded model.
This approach involves dividing the grid into four equivalent quarters (our current solution supports cases where the grid can indeed be divided into four equal parts). Each quarter is managed by a "Handler" thread responsible for moving people within it iteratively.
A challenge arises: how can the four Handlers exchange people when they need to move from one grid section to another?
> spoiler we used some LinkedBlockingQueue! 





