package main

import (
    "fmt"
)

type ErrNegativeSqrt float64

func (e ErrNegativeSqrt) Error() string{
    return "cannot Sqrt negative number: "+fmt.Sprint(float64(e))
}

func Sqrt(x float64) (float64, error) {
    if x < 0 {
        return 0, ErrNegativeSqrt(x)
    }
    z := 1.0
    for i:=1; i<10;i++ {
        z -= (z*z - x) / (2*z)
    }

    return z, nil
}

func main() {
	_, error := Sqrt(-2)
	if error != nil{
	   fmt.Print(error)
	}
}
