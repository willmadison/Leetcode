package thirtydaychallenge

func bstFromPreorder(preorder []int) *TreeNode {
	root := &TreeNode{}

	if len(preorder) > 0 {
		v := preorder[0]
		root.Val = v

		for i := 1; i < len(preorder); i++ {
			n := &TreeNode{Val: preorder[i]}

			current := root
			var placed bool

			for !placed {
				if n.Val < current.Val {
					if current.Left == nil {
						current.Left = n
						placed = true
					} else {
						current = current.Left
					}
				} else {
					if current.Right == nil {
						current.Right = n
						placed = true
					} else {
						current = current.Right
					}
				}
			}
		}
	}

	return root
}
