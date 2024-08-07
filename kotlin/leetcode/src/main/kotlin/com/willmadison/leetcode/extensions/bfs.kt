package com.willmadison.leetcode.extensions

import com.willmadison.leetcode.Location
import java.util.*

// https://leetcode.com/problems/number-of-islands/?envType=daily-question&envId=2024-04-30
fun numIslands(grid: Array<CharArray>): Int {
    var islands = 0

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == '1') {
                islands++
                grid[row][col] = '0'

                val neighbors = ArrayDeque<Int>()
                neighbors.add(row * grid[row].size + col)

                while (neighbors.isNotEmpty()) {
                    val id = neighbors.remove()

                    val r = id / grid[row].size
                    val c = id % grid[row].size

                    if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                        neighbors.add((r - 1) * grid[row].size + c)
                        grid[r - 1][c] = '0'
                    }

                    if (r + 1 < grid.size && grid[r + 1][c] == '1') {
                        neighbors.add((r + 1) * grid[row].size + c)
                        grid[r + 1][c] = '0'
                    }

                    if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                        neighbors.add(r * grid[row].size + c - 1)
                        grid[r][c - 1] = '0'
                    }

                    if (c + 1 < grid[row].size && grid[r][c + 1] == '1') {
                        neighbors.add(r * grid[row].size + c + 1)
                        grid[r][c + 1] = '0'
                    }
                }
            }
        }
    }

    return islands
}

// https://leetcode.com/problems/find-all-groups-of-farmland/?envType=daily-question&envId=2024-04-30
private data class Landmass(val locations: MutableCollection<Location>)

fun findFarmland(land: Array<IntArray>): Array<IntArray> {
    val farmland = mutableListOf<IntArray>()

    for (row in land.indices) {
        for (col in land[row].indices) {
            if (land[row][col] == 1) {
                var x = row
                var y = col

                while (x < land.size && land[x][col] == 1) {
                    y = col
                    while (y < land[row].size && land[x][y] == 1) {
                        land[x][y] = 0
                        y++
                    }
                    x++
                }

                val arr = intArrayOf(row, col, x - 1, y - 1)
                farmland.add(arr)
            }
        }
    }

    return farmland.toTypedArray()
}

@Suppress("unused")
private fun discoverFarmland(grid: Array<IntArray>): Collection<Landmass> {
    val landmasses = mutableSetOf<Landmass>()

    val locations = mapOutAllLocations(grid)

    val visited = mutableSetOf<Location>()

    var unvisitedLocation = locations.firstOrNull()

    while (unvisitedLocation != null) {
        val coordinates = discoverContiguousLand(unvisitedLocation, grid, visited)
        landmasses.add(Landmass(coordinates))
        visited.addAll(coordinates)
        locations.removeAll(visited)
        unvisitedLocation = locations.firstOrNull()
    }

    return landmasses
}

private fun mapOutAllLocations(grid: Array<IntArray>): MutableCollection<Location> {
    val allLocations = mutableListOf<Location>()

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == 1) {
                allLocations.add(Location(row, col))
            }
        }
    }

    return allLocations
}

private fun discoverContiguousLand(
    location: Location,
    grid: Array<IntArray>,
    visited: MutableSet<Location>
): MutableCollection<Location> {
    val locations = mutableListOf<Location>()

    val q = ArrayDeque<Location>()

    if (!visited.contains(location)) {
        q.add(location)
    }

    while (q.isNotEmpty()) {
        val current = q.remove()

        visited.add(current)
        locations.add(current)

        val above = Location(current.row - 1, current.col)
        val right = Location(current.row, current.col + 1)
        val below = Location(current.row + 1, current.col)
        val left = Location(current.row, current.col - 1)

        val hasLandAbove = current.row - 1 >= 0 && grid[current.row - 1][current.col] == 1
        val hasLandRight = current.col + 1 < grid[current.row].size && grid[current.row][current.col + 1] == 1
        val hasLandBelow = current.row + 1 < grid.size && grid[current.row + 1][current.col] == 1
        val hasLandLeft = current.col - 1 >= 0 && grid[current.row][current.col - 1] == 1

        if (hasLandAbove) {
            if (!visited.contains(above)) {
                visited.add(above)
                q.add(above)
            }
        }

        if (hasLandRight) {
            if (!visited.contains(right)) {
                visited.add(right)
                q.add(right)
            }
        }

        if (hasLandBelow) {
            if (!visited.contains(below)) {
                visited.add(below)
                q.add(below)
            }
        }

        if (hasLandLeft) {
            if (!visited.contains(left)) {
                visited.add(left)
                q.add(left)
            }
        }
    }

    return locations
}

// https://leetcode.com/problems/find-if-path-exists-in-graph/?envType=daily-question&envId=2024-04-30
@Suppress("unused")
fun validPath(n: Int, edges: Array<IntArray>, source: Int, destination: Int): Boolean {
    val adjacencyList = mutableMapOf<Int, MutableCollection<Int>>()

    for (edge in edges) {
        var adjacents = adjacencyList.getOrPut(edge.first()) { mutableListOf() }
        adjacents.add(edge.last())

        adjacents = adjacencyList.getOrPut(edge.last()) { mutableListOf() }
        adjacents.add(edge.first())
    }

    val q = ArrayDeque<Int>()
    q.add(source)

    val visited = mutableSetOf<Int>()

    while (q.isNotEmpty()) {
        val current = q.remove()

        if (current == destination) {
            return true
        }

        visited.add(current)

        q.addAll(adjacencyList[current]!!.filter { !visited.contains(it) })
    }

    return false
}

// https://leetcode.com/problems/open-the-lock/?envType=daily-question&envId=2024-04-30
fun openLock(deadends: Array<String>, combination: String): Int {
    val nextValues = mapOf(
        '0' to '1',
        '1' to '2',
        '2' to '3',
        '3' to '4',
        '4' to '5',
        '5' to '6',
        '6' to '7',
        '7' to '8',
        '8' to '9',
        '9' to '0',
    )

    val previousValues = mutableMapOf<Char, Char>()

    for (entry in nextValues) {
        previousValues[entry.value] = entry.key
    }

    val seen = mutableSetOf<String>()
    seen.addAll(deadends)

    val combosToTry = ArrayDeque<String>()

    var turns = 0

    if (seen.contains("0000")) return -1

    combosToTry.add("0000")
    seen.add("0000")

    while (combosToTry.isNotEmpty()) {
        val combosAtLevel = combosToTry.size

        for (i in 0..<combosAtLevel) {
            val current = combosToTry.remove()

            if (current == combination) {
                return turns
            }

            for (wheel in 0..3) {
                var comboSb = StringBuilder(current)
                comboSb.setCharAt(wheel, nextValues[comboSb[wheel]]!!)

                var combo = comboSb.toString()

                if (!seen.contains(combo)) {
                    combosToTry.add(combo)
                    seen.add(combo)
                }

                comboSb = StringBuilder(current)
                comboSb.setCharAt(wheel, previousValues[comboSb[wheel]]!!)

                combo = comboSb.toString()

                if (!seen.contains(combo)) {
                    combosToTry.add(combo)
                    seen.add(combo)
                }
            }
        }

        turns++
    }

    return -1
}

// https://leetcode.com/problems/n-th-tribonacci-number/?envType=daily-question&envId=2024-04-30
fun findMinHeightTrees(n: Int, edges: Array<IntArray>): List<Int> {
    val roots = mutableListOf<Int>()

    if (n < 2) {
        for (i in 0 until n) {
            roots.add(i)
        }

        return roots
    }

    val adjacencyList = mutableMapOf<Int, MutableCollection<Int>>()

    for (edge in edges) {
        val adjacentsA = adjacencyList.getOrPut(edge[0]) { mutableListOf() }
        val adjacentsB = adjacencyList.getOrPut(edge[1]) { mutableListOf() }

        adjacentsA.add(edge[1])
        adjacentsB.add(edge[0])
    }

    var leaves = mutableListOf<Int>()

    for (entry in adjacencyList) {
        if (entry.value.size == 1) {
            leaves.add(entry.key)
        }
    }

    var remainingNodes = n

    while (remainingNodes > 2) {
        remainingNodes -= leaves.size
        val newLeaves = mutableListOf<Int>()

        for (leaf in leaves) {
            val neighbor = adjacencyList[leaf]!!.first()

            adjacencyList[neighbor]!!.remove(leaf)

            if (adjacencyList[neighbor]!!.size == 1) {
                newLeaves.add(neighbor)
            }
        }

        leaves = newLeaves
    }

    return leaves
}

// https://leetcode.com/problems/path-with-maximum-gold/?envType=daily-question&envId=2024-05-14
fun getMaximumGold(grid: Array<IntArray>): Int {
    var maximumGold = 0

    val numRows = grid.size
    val numColumns = grid[0].size

    var totalGoldAvailable = 0

    for (row in grid) {
        for (gold in row) {
            totalGoldAvailable += gold
        }
    }

    for (row in 0 until numRows) {
        for (col in 0 until numColumns) {
            if (grid[row][col] != 0) {
                maximumGold = maxOf(maximumGold, bfsBacktracking(grid, numRows, numColumns, row, col))

                if (maximumGold == totalGoldAvailable) {
                    return maximumGold
                }
            }
        }
    }

    return maximumGold
}

data class State(val location: Location, val goldCollected: Int, val visited: MutableSet<Location>)

fun bfsBacktracking(grid: Array<IntArray>, rows: Int, columns: Int, currentRow: Int, currentColumn: Int): Int {
    var maxGold = 0

    val visited = mutableSetOf<Location>()
    visited.add(Location(currentRow, currentColumn))

    val q = ArrayDeque<State>()
    q.add(State(Location(currentRow, currentColumn), grid[currentRow][currentColumn], visited))

    while (q.isNotEmpty()) {
        val current = q.remove()
        val currentLocation = current.location

        maxGold = maxOf(maxGold, current.goldCollected)

        for (neighbor in currentLocation.neighbors()) {
            if (neighbor.isValid(rows, columns)      &&
                grid[neighbor.row][neighbor.col] > 0 &&
                !current.visited.contains(neighbor)) {
                current.visited.add(neighbor)
                val deepCopyVisited = HashSet(current.visited)
                q.add(State(neighbor, grid[neighbor.row][neighbor.col] + current.goldCollected, deepCopyVisited))
                current.visited.remove(neighbor)
            }
        }
    }

    return maxGold
}

// https://leetcode.com/problems/find-the-safest-path-in-a-grid/?envType=daily-question&envId=2024-05-15
fun maximumSafenessFactor(grid: List<List<Int>>): Int {
    val rows = grid.size
    val columns = rows

    val safenessMatrix = Array(grid.size) { IntArray(grid.size) }

    val q = ArrayDeque<Location>()

    for (i in 0 until rows) {
        for (j in 0 until columns) {
          if (grid[i][j] == 1) {
              q.add(Location(i, j))
          }

          safenessMatrix[i][j] = if (grid[i][j] == 1) 0 else -1
        }
    }

    while (q.isNotEmpty()) {
        var size = q.size

        while (size-- > 0) {
            val current = q.remove()

            for (neighbor in current.neighbors()) {
                val safeness = safenessMatrix[current.row][current.col]

                if (neighbor.isValid(rows, columns) &&
                    safenessMatrix[neighbor.row][neighbor.col] == -1) {
                    safenessMatrix[neighbor.row][neighbor.col] = safeness + 1
                    q.add(neighbor)
                }
            }
        }
    }

    var maxSafeness = -1

    var start = 0
    var end = 0

    for (i in 0 until rows) {
        for (j in  0 until columns) {
            end = maxOf(end, safenessMatrix[i][j])
        }
    }

    while (start <= end) {
        val mid = start + (end - start) / 2

        if (isValidSafeness(safenessMatrix, mid)) {
            maxSafeness = mid
            start = mid+1
        } else {
            end = mid-1
        }
    }

    return maxSafeness
}

fun isValidSafeness(safenessMatrix: Array<IntArray>, minimumSafeness: Int): Boolean {
    val n = safenessMatrix.size

    if (safenessMatrix[0][0] < minimumSafeness || safenessMatrix[n-1][n-1] < minimumSafeness) {
        return false
    }

    val q = ArrayDeque<Location>()
    q.add(Location(0, 0))

    val visited = mutableSetOf<Location>()
    visited.add(Location(0,0))

    while (q.isNotEmpty()) {
        val current = q.remove()

        if (current == Location(n-1, n-1)) {
            return true
        }

        for (neighbor in current.neighbors()) {
            if (neighbor.isValid(n, n) &&
                !visited.contains(neighbor) &&
                safenessMatrix[neighbor.row][neighbor.col] >= minimumSafeness) {
                visited.add(neighbor)
                q.add(neighbor)
            }
        }
    }

    return false
}

