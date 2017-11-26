package main

/*
type Tree struct {
    Left  *Tree
    Value int
    Right *Tree
}
*/

import (
    "golang.org/x/tour/tree"
    "container/list"
    "fmt"
)

// Walk walks the tree t sending all values
// from the tree to the channel ch.
func Walk(t *tree.Tree, ch chan int) {
    l := list.New()
    l.PushBack(t)
    for e := l.Front(); e != nil; e = e.Next() {
        val := e.Value.(*tree.Tree)
        ch <- val.Value
        left := val.Left
        if left != nil {
            l.PushBack(left)
            }
        right := val.Right
        if right != nil {
             l.PushBack(right)
             }
    }

    close(ch)
}

// Same determines whether the trees
// t1 and t2 contain the same values.
func Same(t1, t2 *tree.Tree) bool {
    ch1 := make(chan int, 10)
    go Walk(t1, ch1)

    ch2 := make(chan int, 10)
        go Walk(t2, ch2)

    var i1m = make(map[int]bool)
    for i1 := range ch1 {
       i1m[i1] = true
    }

    for i2 := range ch2 {
          if i1m[i2]==false {
            return false
          }
       }

    return true
}

func main() {
    fmt.Println(Same(tree.New(10), tree.New(10)))
}
