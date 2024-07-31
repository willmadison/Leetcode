package leetcode

func numTeams(ratings []int) int {
	n := len(ratings)
	var teams int

	increasingTeams := make([][]int, n)
	decreasingTeams := make([][]int, n)

	for i := 0; i < n; i++ {
		increasingTeams[i] = make([]int, 4)
		decreasingTeams[i] = make([]int, 4)

		increasingTeams[i][1] = 1
		decreasingTeams[i][1] = 1
	}

	for count := 2; count <= 3; count++ {
		for i := 0; i < n; i++ {
			for j := i + 1; j < n; j++ {
				if ratings[j] > ratings[i] {
					increasingTeams[j][count] += increasingTeams[i][count-1]
				}
				if ratings[j] < ratings[i] {
					decreasingTeams[j][count] += decreasingTeams[i][count-1]
				}
			}
		}
	}

	for i := 0; i < n; i++ {
		teams += increasingTeams[i][3] + decreasingTeams[i][3]
	}

	return teams
}

func minHeightShelves(books [][]int, shelfWidth int) int {

	memo := make([][]int, len(books))

	for i := range memo {
		memo[i] = make([]int, shelfWidth+1)
	}

	var helper func(i, remainingShelfWidth, maxHeight int) int

	helper = func(i, remainingShelfWidth, maxHeight int) int {
		currentBook := books[i]
		updatedMaxHeight := max(maxHeight, currentBook[1])

		if i == len(books)-1 {
			if remainingShelfWidth >= currentBook[0] {
				return updatedMaxHeight
			}

			return maxHeight + currentBook[1]
		}

		if memo[i][remainingShelfWidth] != 0 {
			return memo[i][remainingShelfWidth]
		} else {
			option1Height := maxHeight + helper(i+1, shelfWidth-currentBook[0], currentBook[1])

			if remainingShelfWidth >= currentBook[0] {
				option2Height := helper(i+1, remainingShelfWidth-currentBook[0], updatedMaxHeight)

				memo[i][remainingShelfWidth] = min(option1Height, option2Height)

				return memo[i][remainingShelfWidth]
			}

			memo[i][remainingShelfWidth] = option1Height
			return memo[i][remainingShelfWidth]
		}
	}

	return helper(0, shelfWidth, 0)
}
