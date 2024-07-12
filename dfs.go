package leetcode

import "errors"

var errAllLandVisited = errors.New("AllLandVisited")

type coordinate struct {
	row, col int
}

type island struct {
	coordinates []coordinate
}

// https://leetcode.com/problems/number-of-islands/
func numIslands(grid [][]byte) int {
	islands := discoverIslands(grid)
	return len(islands)
}

func discoverIslands(grid [][]byte) []island {
	var islands []island

	visited := map[coordinate]struct{}{}

	unvisitedSpot, err := findFirstUnvisitedLandMass(grid, visited)

	for err != errAllLandVisited {
		coordinates := discoverContiguousLand(unvisitedSpot, grid, visited)
		islands = append(islands, island{coordinates: coordinates})
		markVisited(coordinates, visited)
		unvisitedSpot, err = findFirstUnvisitedLandMass(grid, visited)
	}

	return islands
}

func findFirstUnvisitedLandMass(grid [][]byte, seen map[coordinate]struct{}) (coordinate, error) {
	for row := 0; row < len(grid); row++ {
		for col := 0; col < len(grid[row]); col++ {
			c := coordinate{row, col}

			isLand := grid[row][col] == '1'
			_, visited := seen[c]

			if isLand && !visited {
				return c, nil
			}
		}
	}

	return coordinate{}, errAllLandVisited
}

func markVisited(coordinates []coordinate, visited map[coordinate]struct{}) {
	for _, c := range coordinates {
		visited[c] = struct{}{}
	}
}

func discoverContiguousLand(c coordinate, grid [][]byte, seen map[coordinate]struct{}) []coordinate {
	var coordinates []coordinate

	q := NewQueue[coordinate]()

	q.Enqueue(c)

	for q.Size() > 0 {
		current, _ := q.Dequeue()
		seen[current] = struct{}{}

		coordinates = append(coordinates, current)

		hasLandAbove := current.row-1 >= 0 && grid[current.row-1][current.col] == '1'
		hasLandRight := current.col+1 < len(grid[c.row]) && grid[current.row][current.col+1] == '1'
		hasLandBelow := current.row+1 < len(grid) && grid[current.row+1][current.col] == '1'
		hasLandLeft := current.col-1 >= 0 && grid[current.row][current.col-1] == '1'

		if hasLandAbove {
			above := coordinate{current.row - 1, current.col}
			if _, visited := seen[above]; !visited {
				seen[above] = struct{}{}
				q.Enqueue(above)
			}
		}

		if hasLandRight {
			right := coordinate{current.row, current.col + 1}
			if _, visited := seen[right]; !visited {
				seen[right] = struct{}{}
				q.Enqueue(right)
			}
		}

		if hasLandBelow {
			below := coordinate{current.row + 1, current.col}
			if _, visited := seen[below]; !visited {
				seen[below] = struct{}{}
				q.Enqueue(below)
			}
		}

		if hasLandLeft {
			left := coordinate{current.row, current.col - 1}
			if _, visited := seen[left]; !visited {
				seen[left] = struct{}{}
				q.Enqueue(left)
			}
		}
	}

	return coordinates
}
