package main

import (
    "fmt"
    "math"
)

func Sqrt(x float64) float64 {
    defer fmt.Println("world")
    z := 1.0
    p := &z
    for i:=1; i<10;i++ {
        *p -= (*p**p - x) / (2**p)
        fmt.Println(z)
    }

    return z
}

func main(){
    fmt.Println(Sqrt(2))
    fmt.Println("Answer:")
    fmt.Println(math.Sqrt(2))
}
