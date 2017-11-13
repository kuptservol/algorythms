package main

import "fmt"

func fibonacci() func() int {
    f1:=0
    f2:=1
    i:=0
    return func() int{
        if i==0 {
           i++
           return f1
        } else if i == 1{
           i ++
           return f2
        } else {
           res := f2+f1
           f1 = f2
           f2 = res

           return res
        }
    }

}

func main() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}
