package main

import (
    "fmt"
	"strings"
)

func WordCount(s string) map[string]int {
    var m = make(map[string]int)
    for _,v := range strings.Fields(s) {
       _, ok := m[v]
       if !ok {
            m[v]=1
       }else{
            m[v]+=1
       }
    }

	return m
}

func main() {
	fmt.Println(WordCount("a a c b c"))
}
