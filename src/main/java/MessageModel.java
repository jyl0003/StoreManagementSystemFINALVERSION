public class MessageModel {

    public static final int EDIT_USER = 10;

    public static final int GET_PRODUCT = 100;
    public static final int UPDATE_PRODUCT = 101;
    public static final int SAVE_PRODUCT = 102;

    public static final int GET_CUSTOMER = 200;
    public static final int UPDATE_CUSTOMER = 201;
    public static final int SAVE_CUSTOMER = 202;

    public static final int ADD_USER = 300;
    public static final int UPDATE_USER = 301;

    public static  final int LOAD_PURCHASE = 400;
    public static  final int UPDATE_PURCHASE = 401;
    public static  final int SAVE_PURCHASE = 402;


    public static final int OPERATION_OK = 1000; // server responses!
    public static final int OPERATION_FAILED = 1001;

    public static final int LOGIN = 2000;
    public static final int LOGOUT = 2001;
    public int code;
    public String data;
    public String Username;
    public String Password;
    public String customerData;
    public String productData;
    public int ssid;
}
