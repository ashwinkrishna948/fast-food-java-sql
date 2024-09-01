
public class Order {
	
	int slNo;
	int d_id;
	String d_name;
	int quantity;
	int perprice;
	int total;
	
	public Order (int s, int did, String dn, int q, int p) {
		slNo = s;
		d_id = did;
		d_name = dn;
		quantity = q;
		perprice = p;
		total = q*p;
	}
}
