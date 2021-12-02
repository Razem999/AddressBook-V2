import java.util.Scanner;

public class BuddyInfo {

    private static String name;
    private static String address;
    private static String phoneNumber;
    private String bud;

    public static String getName() {
        return name;
    }
    public static String getAddress() { return address;}
    public static String getPhoneNumber() { return phoneNumber; }

    public BuddyInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bud = name + "#" + address + "#" + phoneNumber;
    }
    
    public BuddyInfo() {
        this("Razem", "Ottawa", "500");
    }

    public String toXML(){
        String xml = "<buddyInfo>\n";
        xml += "<name>" + name + "</name>\n";
        xml += "<address>" + address + "</address>\n";
        xml += "<phoneNumber>" + phoneNumber + "</phoneNumber>\n";
        xml += "</buddyInfo>\n";
        return xml;
    }
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        new BuddyInfo();
        System.out.println("Hello " + BuddyInfo.getName());
        new BuddyInfo("John", "Toronto", "710");
        System.out.println("Hello " + getName());
        System.out.println("Your address: " + getAddress());
        System.out.println("Your phone number: " + getPhoneNumber());
    }

    public static BuddyInfo importBuddyInfo(String buddy) {
        Scanner s = new Scanner(buddy).useDelimiter("#");
        BuddyInfo buddyInfo = new BuddyInfo(s.next(), s.next(), s.next());
        System.out.println(buddyInfo.toString());
        return buddyInfo;
    }

    @Override
    public String toString() {
        return bud;
    }
}
