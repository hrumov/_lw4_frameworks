
public class CheckPageContains {

	public boolean findElements(String data, String str) {
		System.out.println("-------------------outCheckPageContains---------------");
		if (data.indexOf(str) == -1) {
			System.out.println("Substring is not found!!!"+str);
			
			return false;

		} else {
			System.out.println("Substring is found!!!" + str);
			return true;
		}
	}

}
