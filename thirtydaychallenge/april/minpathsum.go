package april

import (
	"errors"
	"math"
)

type coordinate struct {
	row, col int
}

type node struct {
	location coordinate
	weight   int
}

type graph map[node][]node

type nodeQueue struct {
	data []node
	size int
}

func (n *nodeQueue) enqueue(value node) {
	n.data = append(n.data, value)
	n.size++
}

func (n *nodeQueue) dequeue() (node, error) {
	if n.size > 0 {
		value := n.data[0]
		n.size--
		n.data = n.data[1:]

		return value, nil
	}

	return node{}, errors.New("No Such Element")
}

func (n *nodeQueue) peek() (node, error) {
	if n.size > 0 {
		value := n.data[0]
		return value, nil
	}

	return node{}, errors.New("No Such Element")
}

func minPathSum(grid [][]int) int {
	g := deriveGraph(grid)

	start := coordinate{0, 0}
	end := coordinate{len(grid) - 1, len(grid[0]) - 1}
	source := node{start, grid[start.row][start.col]}
	destination := node{end, grid[end.row][end.col]}

	minDistancesByNode := map[node]int{}

	minDistancesByNode[source] = source.weight

	visitedNodes := map[node]struct{}{}
	q := nodeQueue{data: []node{}}

	q.enqueue(source)

	for q.size > 0 {
		c, _ := q.dequeue()

		if c == destination {
			break
		}

		visitedNodes[c] = struct{}{}
		adjacents := g[c]

		for _, n := range adjacents {
			if _, visited := visitedNodes[n]; !visited {
				q.enqueue(n)
				visitedNodes[n] = struct{}{}
			}

			var distance int

			if d, present := minDistancesByNode[n]; !present {
				distance = math.MaxInt64
			} else {
				distance = d
			}

			alternate := minDistancesByNode[c] + n.weight

			if alternate < distance {
				minDistancesByNode[n] = alternate
			}
		}
	}

	return minDistancesByNode[destination]
}

func deriveGraph(grid [][]int) graph {
	nodesByCoordinate := map[coordinate]node{}

	g := map[node][]node{}

	for row := 0; row < len(grid); row++ {
		for col := 0; col < len(grid[row]); col++ {
			var n node
			var present bool
			coord := coordinate{row, col}
			if n, present = nodesByCoordinate[coord]; !present {
				n = node{coord, grid[row][col]}
				nodesByCoordinate[coord] = n
			}

			var adjacents []node

			if col+1 <= len(grid[row])-1 {
				var right node
				c := coordinate{row, col + 1}
				if right, present = nodesByCoordinate[c]; !present {
					right = node{c, grid[c.row][c.col]}
					nodesByCoordinate[c] = right
				}

				adjacents = append(adjacents, right)
			}

			if row+1 <= len(grid)-1 {
				var bottom node
				c := coordinate{row + 1, col}
				if bottom, present = nodesByCoordinate[c]; !present {
					bottom = node{c, grid[c.row][c.col]}
					nodesByCoordinate[c] = bottom
				}

				adjacents = append(adjacents, bottom)
			}

			g[n] = adjacents
		}
	}

	return g
}
