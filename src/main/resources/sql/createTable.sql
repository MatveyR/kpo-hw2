create table users(
                      id text primary key,
                      username text,
                      password text,
                      role text
);

create table orders(
                      id text primary key,
                      user_id text,
                      constraint fk_order
                          foreign key(user_id)
                              references users(id),
                      status text
                      total_amount int
);
