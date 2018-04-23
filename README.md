# shortestMapRoute
It's a Google Map-like program (of-course nothing near it) using the learned shortest path algorithms.

<b>The map:</b> the map is a non-directed graph G = (V, E). Each vertex v ∈ V represents a traffic intersection and an edge (u, v) ∈ E represents the two-way street (or road) from interactions u to v. The weight on every edge (u,v), denoted with τ(u,v), is the time to travel between u and v.

<b>The problem:</b> Without doubt, shortest path algorithms can be used to find a “static” shortest path from starting position s to destination t. But this Google Map-like algorithm takes real- time traffic information into consideration and to dynamically choose a shortest path from the current position p to the destination t. That is, at the current position p, the algorithm checks if a travel time change on the street (u, v) and makes an adjustment to the current path.

<b>The program accomplishes the following tasks.</b>
It consists of a loop such that at each iteration of the loop (which can be control manually or by a system timer), (1) the vehicle moves from interaction p to the next interaction q on the identified shortest path p 􏰀 t, (2) at position q, it checks for travel time changes on streets, and (3) adjust path q 􏰀 t based on the time changes.
The time change information is available by calling a subroutine that randomly identifies one or more streets (u,v) on the map and randomly changes the travel time of these streets. The loop repeats until the vehicle arrives at the destination t. The program should output the shortest path at every iteration together with the relevant travel time change information.

<b>A more realistic situations to consider would be:</b> While the above situation assumes that travel time changes on streets are synchronized with the time the vehicle arrives at an intersection, in reality, the algorithm should checks periodically traffic situations while on the street as well as well just at intersections. We may assume the period to be some time unit and travel time is measured with such unit. So the subroutine used to issue travel time change should also be synchronized with the time period. For example, if the travel time from interaction p to intersection q is τ(p,q) = 10 units, the program should check traffic and adjust path 10 times on the street (p,q). To deal with these situations, an iteration of the loop in your program should correspond to one time unit. <b>(This is not yet implemented)</b>
<br>
<b><u>Format of the input graph</u> (small example):</b>

n=5 <br>
s=1 <br>
t=5 <br>
<br>
0,  23, 8,  35, 90 <br>
23, 0,  15, 7,  10 <br>
8,  15, 0,  28, 17 <br>
35, 7,  28, 0,  6 <br>
90,  10, 17, 6,  0 <br>

<b>Generate javadoc for more information about individual components of the code.</b>