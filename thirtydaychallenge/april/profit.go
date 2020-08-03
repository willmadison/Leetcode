package april

func maxProfit(prices []int) int {
	var buy, profit int
	sell := buy + 1

	for sell < len(prices) {
		if prices[buy] < prices[sell] {
			if sell == len(prices)-1 || prices[sell] > prices[sell+1] {
				profit += prices[sell] - prices[buy]
				buy = sell
				sell++
			} else {
				sell++
			}
		} else {
			buy++
			sell++
		}
	}

	return profit
}
