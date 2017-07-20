use std::io;

fn main() {
    let mut num = String::new();
    io::stdin().read_line(&mut num).expect("Failed to read line");

    let num: u32 = num.trim().parse().expect("Is not numeric");

    println!("You requested: {}th Fibbonachi number", num);
    println!("Result: {}", fib(num));
}

fn fib(kth: u32) -> u32 {
    let fib = if kth==1  {
        0
    }
    else if kth==2 {
        1
    }
    else {
        fib(kth-1)+fib(kth-2)
    };

    fib
}
