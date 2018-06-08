package zhan.foundation.lesson10;

import java.io.IOException;

public class MainServer {

	public static void main(String[] args) throws IOException {
		MyNIORector reactor = new MyNIORector(9000);
		reactor.start();

	}

}
