fn main() {
  ownership_move();
}

fn ownership_move() {
    let s = String::from("world");
    let len = get_len(&s2);
    println!("Len of {} is {} !", s, len);
}

fn get_len(s: &String) -> u32 {
     s.len()
}
