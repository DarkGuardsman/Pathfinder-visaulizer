# Pathfinder-visaulizer
Java swing application used to visualize pathing algs. The core usage of this application is to test algs for use with gaming. In the context of searches that spread out without a real end goal. This is not really meant for navigation thus there is no start and end goals. Instead tests are setup with a center point and expected to fill the map.

# How it works
The application features a simple UI with controls. From the list of controls a user can trigger an alg to run. Then either using the play or next/prev buttons show the results. 

The algs themselfs are setup to run on a 2D grid instead of nodes with paths. This is done due to the context of the tests. However, can be thought of no different than nodes all containing 4 edges. Rxcluding extremes of the grid which have 3 edges.

# Algs
Several algs are included even if the results are no different from each other.

## DFS (Depth first search)
One of the main algs used for pathing. Will go as deep as possible before exploring other paths. Uses a stack to meet this goal. Due to the stack last node added is the first pathed (last in first out). This results in the path going right first before changing direction.

## BFS (Breadth first search)
One of the main algs used for pathing. Will path nodes in the order they are found. Meaning it will path closer to the center spreading out evenly. This is done via a queue resulting in first in first out.

## BFS Box
BFS that starts with a box shape. Not really an alg but different starting data. Used to see if starting condition matters when pathing. Results show its only a minor effect of chaning the edges every so slightly.

## BFS Quick Start
BFS that starts with 4 nodes (one per direction). Also not an alg but starting data. Has no difference compared to a normal BFS alg.

## BFS Sorted Quick Start
BFS with a quick start condition but queue is sorted by distance. Has almost no effect at all, likely do to implemention. 

## BFS Sorted Box
Same as the above but with box start.

## Shell Box & circle
Version of BFS that limits the search to a range until it runs out of nodes to path. Then previous nodes found out of the range are dumped into the queue. This results in a layer/shell like effect. In which the BFS moves out in a more event path compared to the traditional diamond shape.

Box version produces a square shape. Uses a x & y distance check that compared each variable indepent from each other. 

Circle version produces a circle shape. Uses a 2D distance check from center.