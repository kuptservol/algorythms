package main

import (
    "fmt"
)

func Modify(x int) int {
    return Modify2(x)
}

func Modify2(x int) int {
    x = x + 1
    return x
}

func main() {
    fmt.Println(Modify(2))
}
