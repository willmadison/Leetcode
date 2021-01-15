package january

func canPermutePalindrome(s string) bool {
	countsByCharacter := map[rune]int{}

	for _, c := range s {
		countsByCharacter[c]++
	}

	var numOdds int

	for _, v := range countsByCharacter {
		if v%2 != 0 {
			numOdds++
		}
	}

	return numOdds <= 1
}
