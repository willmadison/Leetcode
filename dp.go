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
