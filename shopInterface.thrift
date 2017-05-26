namespace java tutorial

typedef i32 int

service ShopInterface {
	double getPrice(1: string product),
	void Order(1: string product, 2: i32 quantity, 3: string ip, 4: i32 port)
}
