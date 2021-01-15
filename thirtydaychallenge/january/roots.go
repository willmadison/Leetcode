package january

type Node struct {
	Val      int
	Children []*Node
}

func findRoot(tree []*Node) *Node {
	parentsByChild := map[int]*Node{}

	for _, n := range tree {
		if len(n.Children) > 0 {
			for _, c := range n.Children {
				parentsByChild[c.Val] = n
			}
		}
	}

	for _, n := range tree {
		if _, present := parentsByChild[n.Val]; !present {
			return n
		}
	}

	return nil
}
