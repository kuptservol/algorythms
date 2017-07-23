fn main() {
        ownership_reference();
}

fn ownership_move_on_heap() {
        let s1 = String::from("world");
        let s2 = s1;
        println!("Hello, {}!", s2);
}

fn ownership_move() {
        let s1 = String::from("world");
        let s2 = s1;
// not allowed cause s1 has been moved
 //println!("Hello, {}!", s2);
}


fn ownership_on_stack() {
        let s1 = "world";
        let s2 = s1;
        // with stack it is possible
        println!("Hello, {}!", s1);
}

fn ownership_reference() {
    let s = String::from("world");
    let len = get_len(&s);
    println!("Len of {} is {} !", s, len);
}

fn get_len(s: &String) -> usize {
     s.len()
}
