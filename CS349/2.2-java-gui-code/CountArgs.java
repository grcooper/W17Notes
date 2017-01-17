public class CountArgs {
	public static void main(String[] args) {
		if (args.length > 0) {
			System.out.println("You provided " + args.length + " args.");
		}
		else {
			System.out.println("None supplied");
		}
	}
}